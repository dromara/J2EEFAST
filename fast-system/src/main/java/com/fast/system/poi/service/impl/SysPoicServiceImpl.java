package com.fast.system.poi.service.impl;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.ToolUtil;
import com.fast.common.core.page.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.fast.system.poi.dao.SysPoicDao;
import com.fast.system.poi.entity.SysPoicEntity;
import com.fast.system.poi.service.SysPoicService;

/**
 *
 * @ClassName: 报信息
 * @Package: com.fast.system.poi
 * @Description: 报信息ServiceImpl业务层处理
 * @author: ZhouHuan 自动生成
 * @time 2020-01-08
 
 *
 */
@Service("sysPoicService")
public class SysPoicServiceImpl extends ServiceImpl<SysPoicDao, SysPoicEntity> implements SysPoicService {

    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        QueryWrapper<SysPoicEntity> r = new QueryWrapper<SysPoicEntity>();

                                                                                                              String name = (String) params.get("name");
                    
                                            r.like(ToolUtil.isNotEmpty(name), "name", name);
                    
                                                                                                    String filename = (String) params.get("filename");
                    
                                            r.like(ToolUtil.isNotEmpty(filename), "filename", filename);
                    
                                                                                                    String type = (String) params.get("type");
                    
                                            r.eq(ToolUtil.isNotEmpty(type), "type", type);
                    
                    
        Page<SysPoicEntity> page = this.baseMapper.selectPage(new Query<SysPoicEntity>(params).getPage(), r);
        return new PageUtil(page);
    }

    @Override
    public int update(SysPoicEntity sysPoic) {
       return this.baseMapper.update(sysPoic);
    }

    @Override
    public int deleteSysPoicById(Long id){
        return this.baseMapper.deleteSysPoicById(id);
    }

    /**
     * 删除报信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBatch(Long[] ids)
    {
        return this.baseMapper.deleteBatch(ids);
    }

}
