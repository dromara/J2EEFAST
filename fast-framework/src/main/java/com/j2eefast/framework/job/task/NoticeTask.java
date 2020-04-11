package com.j2eefast.framework.job.task;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.j2eefast.common.core.utils.SpringUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.redis.SysConfigRedis;
import com.j2eefast.framework.sys.entity.SysNoticeEntity;
import com.j2eefast.framework.sys.service.SysNoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * <p>公告通知过时性检查</p>
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2019-03-28 00:06
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
@Component("jobNoticeTask")
public class NoticeTask {

    private Logger                  logger                  = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysConfigRedis sysConfigRedis;

    public void fastNotice(){
        List<SysNoticeEntity> listRedisNotice = sysConfigRedis.getRedisNotice();
        long longTime;
        if(ToolUtil.isNotEmpty(listRedisNotice)){
            JSONArray array= JSONArray.parseArray(JSON.toJSONString(listRedisNotice));
            listRedisNotice = JSONObject.parseArray(array.toJSONString(), SysNoticeEntity.class);
        }else{
           listRedisNotice = new ArrayList<>();
        }
        List<SysNoticeEntity> listNotice =  SpringUtil.getBean(SysNoticeService.class).
               list(new QueryWrapper<SysNoticeEntity>().ne("status","2").orderByDesc("start_time"));
        for(SysNoticeEntity notice: listNotice){
            if(DateUtil.compare(notice.getEndTime(),new Date()) < 0){
                notice.setStatus("2");
                SpringUtil.getBean(SysNoticeService.class).updateById(notice);
                if(ToolUtil.isNotEmpty(listRedisNotice)){
                    Iterator<SysNoticeEntity> it = listRedisNotice.iterator();
                    while(it.hasNext()){
                        SysNoticeEntity notices = it.next();
                        if(notice.getId().equals(notices.getId())){
                            it.remove();
                        }
                    }
                }
            }

            if(DateUtil.compare(notice.getStartTime(),new Date()) < 0 && DateUtil.compare(notice.getEndTime(),new Date()) > 0 && !notice.getStatus().equals("0")){
                notice.setStatus("0");
                SpringUtil.getBean(SysNoticeService.class).updateById(notice);
                if(ToolUtil.isNotEmpty(listRedisNotice)){
                    Iterator<SysNoticeEntity> it = listRedisNotice.iterator();
                    boolean flag = false;
                    while(it.hasNext()){
                        SysNoticeEntity notices = it.next();
                        if(notice.getId().equals(notices.getId())){
                            flag = true;
                        }
                    }
                    if(!flag){
                       listRedisNotice.add(notice);
                    }
                }else {
                       listRedisNotice.add(notice);
                }
            }
        }
        if(ToolUtil.isNotEmpty(listNotice)){
            if(ToolUtil.isNotEmpty(listRedisNotice)){
                Collections.sort(listRedisNotice, Comparator.comparing(SysNoticeEntity::getEndTime).reversed());
                longTime = DateUtil.between(new Date(),listRedisNotice.get(0).getEndTime(), DateUnit.SECOND);
                sysConfigRedis.saveOrUpdateNotice(listRedisNotice,longTime);
            }
        }else{
            sysConfigRedis.delNotice();
        }
    }
}
