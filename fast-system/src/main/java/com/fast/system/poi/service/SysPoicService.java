package com.fast.system.poi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.system.poi.entity.SysPoicEntity;
import com.fast.common.core.utils.PageUtil;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName: 报信息
 * @Package: com.fast.system.poi
 * @Description: 报信息Service接口
 * @author: ZhouHuan
 * @time 2020-01-08
 
 *
 */
public interface SysPoicService extends IService<SysPoicEntity> {

    /**
     * @Description: 查询表list分页
     * @param: 页面参数
     * @auther: ZhouHuan 自动生成
     * @date: 2020/1/7 10:31
     */
    PageUtil queryPage(Map<String, Object> params);

    /**
     * @Description: 批量删除
     * @auther: ZhouHuan 自动生成
     * @date: 2020-01-08
     */
    int deleteBatch(Long[] ids);


    /**
     * 单个删除报信息
     *
     * @param id 报信息ID
     * @return 结果
     */
    int deleteSysPoicById(Long id);

    /**
     * 修改报信息
     *
     * @param sysPoicEntity 报信息
     * @return 结果
     */
    int update(SysPoicEntity sysPoic);

}
