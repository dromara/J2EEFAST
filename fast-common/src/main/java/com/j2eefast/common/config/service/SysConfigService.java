package com.j2eefast.common.config.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.config.entity.SysConfigEntity;
import com.j2eefast.common.config.mapper.SysConfigMapper;
import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.RedisUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.Map;

/**
 * <p>系统参数服务实现类</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-01 16:17
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
@Service
public class SysConfigService extends ServiceImpl<SysConfigMapper, SysConfigEntity> {

    private static final String                     CONFIG_KEY                  = "sys:config:";

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 页面翻页查询
     * @param params
     * @return
     */
    public PageUtil findPage(Map<String, Object> params) {
        String paramKey = (String) params.get("paramKey");
        String paramName = (String) params.get("paramName");
        Page<SysConfigEntity> page = this.baseMapper.selectPage(new Query<SysConfigEntity>(params).getPage(),
                new QueryWrapper<SysConfigEntity>()
                        .like(ToolUtil.isNotEmpty(paramKey), "param_key", paramKey)
                        .like(ToolUtil.isNotEmpty(paramName), "param_name", paramName));
        return new PageUtil(page);
    }

    /**
     * 保存
     */
    public boolean add(SysConfigEntity sysConfig){
        if(this.save(sysConfig)){
            redisUtil.set(CONFIG_KEY+sysConfig.getParamKey(),sysConfig);
            return true;
        }
        return false;
    }

    /**
     * 更新
     * @param sysConfig
     * @return
     */
    public boolean update(SysConfigEntity sysConfig){
        if(this.updateById(sysConfig)){
            redisUtil.set(CONFIG_KEY+sysConfig.getParamKey(),sysConfig);
            return true;
        }
        return false;
    }


    public boolean updateValueByKey(String key, String value) {
       if(this.baseMapper.updateValueByKey(key, value) > 0){
           redisUtil.delete(CONFIG_KEY+key);
           return true;
       }
       return false;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    public boolean deleteBatchByIds(Long[] ids) {
        for (Long id : ids) {
            SysConfigEntity config = this.getById(id);
            redisUtil.delete(CONFIG_KEY+config.getParamKey());
        }
        return this.removeByIds(Arrays.asList(ids));
    }

    /**
     * 通过key 获取值
     * @param key
     * @return
     */
    public String getParamValue(String key) {
        SysConfigEntity config = redisUtil.get(CONFIG_KEY+key, SysConfigEntity.class);
        if (config == null) {
            config = this.baseMapper.queryByKey(key);
            redisUtil.set(CONFIG_KEY+key,config);
        }

        return config == null ? null : config.getParamValue();
    }

    public <T> T getConfigObject(String key, Class<T> clazz) {
        String value = getParamValue(key);
        if (!StrUtil.isBlankOrUndefined(value) && JSONUtil.isJson(value)
                && !ClassUtil.equals(clazz, "String", false)) { //判断获取值,是否转换Bean对象
            return JSONUtil.toBean(value, clazz);
            //return new Gson().fromJson(value, clazz);
        }

        if(ClassUtil.equals(clazz, "String", false)) { //String 转换
            return Convert.convert(clazz, value);
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RxcException("获取参数失败");
        }
    }

    /**
     * 检测Key 是否存在
     * @param config
     * @return
     */
    public boolean checkConfigKeyUnique(SysConfigEntity config) {
        Long configId = ToolUtil.isEmpty(config.getId()) ? -1L : config.getId();
        SysConfigEntity info = this.getOne(new QueryWrapper<SysConfigEntity>().eq("param_key",config.getParamKey()));
        if (ToolUtil.isNotEmpty(info) && info.getId().longValue() != configId.longValue()){
            return  false;
        }
        return true;
    }
}
