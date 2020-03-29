package com.fast.framework.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.common.core.base.entity.Ztree;
import com.fast.common.core.exception.RxcException;
import com.fast.common.core.page.Query;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.ToolUtil;
import com.fast.framework.sys.dao.SysDictTypeDao;
import com.fast.framework.sys.entity.SysDictTypeEntity;
import com.fast.framework.sys.service.SysDictDataService;
import com.fast.framework.sys.service.SysDictTypeSerive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: fast
 * @Package: com.fast.framework.sys.service.impl
 * @ClassName: SysDictTypeSeriveImpl
 * @Author: ZhouHuan Emall:18774995071@163.com
 * @Description:
 * @Date: 2019/12/18 14:42
 * @Version: 1.0
 */
@Service("sysDictTypeSerive")
public class SysDictTypeSeriveImpl extends ServiceImpl<SysDictTypeDao, SysDictTypeEntity> implements SysDictTypeSerive {

    @Autowired
    private SysDictDataService sysDictDataService;

    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        String dictName = (String) params.get("dictName");
        String dictType = (String) params.get("dictType");
        String status = (String) params.get("status");
        Page<SysDictTypeEntity> page = this.baseMapper.selectPage(new Query<SysDictTypeEntity>(params).getPage(),
                new QueryWrapper<SysDictTypeEntity>().like(!ToolUtil.isEmpty(dictName), "dict_name", dictName)
                        .like(!ToolUtil.isEmpty(dictType), "dict_type", dictType)
                        .like(!ToolUtil.isEmpty(status), "status", status));
        return new PageUtil(page);
    }

    @Override
    public boolean checkDictTypeUnique(SysDictTypeEntity dict) {
        Long dictId = ToolUtil.isEmpty(dict.getDictId()) ? -1L : dict.getDictId();
        SysDictTypeEntity dictType = this.baseMapper.checkDictTypeUnique(dict.getDictType());
        if (!ToolUtil.isEmpty(dictType) && dictType.getDictId().longValue() != dictId.longValue())
        {
            return  false;
        }
        return true;
    }

    @Override
    public List<Ztree> dictTypeTreeData() {
        List<SysDictTypeEntity> dictList = this.list();
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (SysDictTypeEntity dict : dictList){
            if ("0".equals(dict.getStatus()))
            {
                Ztree ztree = new Ztree();
                ztree.setId(dict.getDictId());
                ztree.setName(transDictName(dict));
                ztree.setTitle(dict.getDictType());
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

    public String transDictName(SysDictTypeEntity dictType)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("(" + dictType.getDictName() + ")");
        sb.append("&nbsp;&nbsp;&nbsp;" + dictType.getDictType());
        return sb.toString();
    }

    @Override
    public int updateDictType(SysDictTypeEntity dictType) {
        return this.baseMapper.updateDictType(dictType);
    }

    @Override
    public void deleteDictTypeByIds(Long[] ids) {
        for (Long dictId : ids)
        {
            SysDictTypeEntity dictType = this.getById(dictId);
            if (!ToolUtil.isEmpty(dictType) &&
                    sysDictDataService.countDictDataByType(dictType.getDictType()) > 0)
            {
                throw new RxcException(String.format("%1$s已分配,不能删除", dictType.getDictName()),"50001");
            }
        }
        this.baseMapper.deleteDictTypeByIds(ids);
    }

}
