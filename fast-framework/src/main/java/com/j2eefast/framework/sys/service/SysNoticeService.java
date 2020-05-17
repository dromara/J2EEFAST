package com.j2eefast.framework.sys.service;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.redis.SysConfigRedis;
import com.j2eefast.framework.sys.entity.SysNoticeEntity;
import com.j2eefast.framework.sys.mapper.SysNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>公告服务实现类</p>
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2019-03-26 10:58
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Service
public class SysNoticeService  extends ServiceImpl<SysNoticeMapper, SysNoticeEntity> {

    @Autowired
    private SysConfigRedis sysConfigRedis;

    /**
     * 页面展示查询翻页
     */
    public PageUtil findPage(Map<String, Object> params) {
        QueryWrapper<SysNoticeEntity> r = new QueryWrapper<SysNoticeEntity>();
        String noticeTitle = (String) params.get("noticeTitle");
        r.like(ToolUtil.isNotEmpty(noticeTitle), "notice_title", noticeTitle);
        String status = (String) params.get("status");
        r.eq(ToolUtil.isNotEmpty(status), "status", status);
        String noticeType = (String) params.get("noticeType");
        r.eq(ToolUtil.isNotEmpty(noticeType), "notice_type", noticeType);
        Page<SysNoticeEntity> page = this.baseMapper.selectPage(new Query<SysNoticeEntity>(params).getPage(), r);
        return new PageUtil(page);
    }

    /**
     * 新增
     * @param sysNotice
     * @return
     */
    public boolean add(SysNoticeEntity sysNotice){
        if(this.save(sysNotice)){

            if("0".equals(sysNotice.getStatus())){
                sysNotice.setNoticeContent("");
                List<SysNoticeEntity> listNotice = sysConfigRedis.getRedisNotice();
                long longTime;
                if(ToolUtil.isNotEmpty(listNotice)){
                    JSONArray array= JSONArray.parseArray(JSON.toJSONString(listNotice));
                    listNotice = JSONObject.parseArray(array.toJSONString(), SysNoticeEntity.class);
                    Date temp = sysNotice.getEndTime();
                    for(SysNoticeEntity notice: listNotice){
                        if(DateUtil.compare(notice.getEndTime(),temp) >= 0){
                            temp = notice.getEndTime();
                        }
                    }
                    listNotice.add(sysNotice);
                    longTime = DateUtil.between(new Date(),temp, DateUnit.SECOND);

                }else{
                    listNotice = new ArrayList<>(1);
                    listNotice.add(sysNotice);
                    longTime = DateUtil.between(new Date(),sysNotice.getEndTime(), DateUnit.SECOND);
                }
                sysConfigRedis.saveOrUpdateNotice(listNotice,longTime);
            }
            return true;
        }
        return false;
    }

    /**
     * 更新
     * @param sysNotice
     * @return
     */
    public boolean update(SysNoticeEntity sysNotice){
        if(this.updateById(sysNotice)){
            sysNotice.setNoticeContent("");
            List<SysNoticeEntity> listNotice = sysConfigRedis.getRedisNotice();
            long longTime;
            if (ToolUtil.isNotEmpty(listNotice)) {
                JSONArray array = JSONArray.parseArray(JSON.toJSONString(listNotice));
                listNotice = JSONObject.parseArray(array.toJSONString(), SysNoticeEntity.class);
                Date temp = sysNotice.getEndTime();
                Iterator<SysNoticeEntity> it = listNotice.iterator();
                while (it.hasNext()) {
                    SysNoticeEntity notice = it.next();
                    if (!notice.getId().equals(sysNotice.getId()) && DateUtil.compare(notice.getEndTime(), temp) >= 0) {
                        temp = notice.getEndTime();
                    }
                    if (notice.getId().equals(sysNotice.getId())) {
                        it.remove();
                    }
                }
                if("0".equals(sysNotice.getStatus())) {
                    listNotice.add(sysNotice);
                }
                longTime = DateUtil.between(new Date(), temp, DateUnit.SECOND);
                sysConfigRedis.saveOrUpdateNotice(listNotice, longTime);
            } else {
                if("0".equals(sysNotice.getStatus())) {
                    listNotice = new ArrayList<>(1);
                    listNotice.add(sysNotice);
                    longTime = DateUtil.between(new Date(), sysNotice.getEndTime(), DateUnit.SECOND);
                    sysConfigRedis.saveOrUpdateNotice(listNotice, longTime);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 批量删除
     * @author zhouzhou
     * @date 2020-03-08 21:30
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatchByIds(Long[] ids) {

        //
        if(this.removeByIds(Arrays.asList(ids))){
            List<SysNoticeEntity> listNotice = sysConfigRedis.getRedisNotice();
            long longTime;
            if (ToolUtil.isNotEmpty(listNotice)) {
                JSONArray array = JSONArray.parseArray(JSON.toJSONString(listNotice));
                listNotice = JSONObject.parseArray(array.toJSONString(), SysNoticeEntity.class);
                Iterator<SysNoticeEntity> it = listNotice.iterator();
                while (it.hasNext()) {
                    SysNoticeEntity notice = it.next();
                    for(Long l: ids){
                        if (notice.getId().equals(l)) {
                            it.remove();
                        }
                    }
                }

                if(ToolUtil.isNotEmpty(listNotice)){
                    Collections.sort(listNotice, Comparator.comparing(SysNoticeEntity::getEndTime).reversed());
                    longTime = DateUtil.between(new Date(), listNotice.get(0).getEndTime(), DateUnit.SECOND);
                    sysConfigRedis.saveOrUpdateNotice(listNotice, longTime);
                }else{
                    sysConfigRedis.delNotice();
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 获取一个公告根居开始时间排序
     * @return
     */
    public SysNoticeEntity getNotice(){
        SysNoticeEntity sysNotice;
        List<SysNoticeEntity> listNotice = sysConfigRedis.getRedisNotice();
        if(ToolUtil.isNotEmpty(listNotice)){
            JSONArray array= JSONArray.parseArray(JSON.toJSONString(listNotice));
            listNotice = JSONObject.parseArray(array.toJSONString(), SysNoticeEntity.class);
            Date temp;
            Iterator<SysNoticeEntity> it = listNotice.iterator();
            while(it.hasNext()){
                SysNoticeEntity notice = it.next();
                if(notice.getNoticeLevel().equals("0") || !notice.getStatus().equals("0") || !notice.getNoticeType().equals("1")){
                    it.remove();
                }
            }
            if(ToolUtil.isNotEmpty(listNotice)){
                Collections.sort(listNotice, Comparator.comparing(SysNoticeEntity::getStartTime));
                return listNotice.get(0);
            }
            return new SysNoticeEntity();
        }
        return new SysNoticeEntity();
    }
}
