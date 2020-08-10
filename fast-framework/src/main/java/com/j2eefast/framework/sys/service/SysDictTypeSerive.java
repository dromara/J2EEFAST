package com.j2eefast.framework.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.base.entity.Ztree;
import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.RedisUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.sys.entity.SysDictTypeEntity;
import com.j2eefast.framework.sys.mapper.SysDictTypeMapper;
import com.j2eefast.framework.utils.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 数据字典Key
 */
@Service
public class SysDictTypeSerive extends ServiceImpl<SysDictTypeMapper,SysDictTypeEntity> {

    @Autowired
    private SysDictDataService sysDictDataService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 页面展示查询翻页
     */
    public PageUtil findPage(Map<String, Object> params) {
        String dictName = (String) params.get("dictName");
        String dictType = (String) params.get("dictType");
        String status = (String) params.get("status");
        Page<SysDictTypeEntity> page = this.baseMapper.selectPage(new Query<SysDictTypeEntity>(params).getPage(),
                new QueryWrapper<SysDictTypeEntity>().like(!ToolUtil.isEmpty(dictName), "dict_name", dictName)
                        .like(!ToolUtil.isEmpty(dictType), "dict_type", dictType)
                        .like(!ToolUtil.isEmpty(status), "status", status));
        return new PageUtil(page);
    }


    public boolean checkDictTypeUnique(SysDictTypeEntity dict) {
        Long dictId = ToolUtil.isEmpty(dict.getId()) ? -1L : dict.getId();
        SysDictTypeEntity dictType = this.getOne(new QueryWrapper<SysDictTypeEntity>()
                .eq("dict_type",dict.getDictType()));
        if (!ToolUtil.isEmpty(dictType) && dictType.getId().longValue() != dictId.longValue()){
            return  false;
        }
        return true;
    }


    public List<Ztree> dictTypeTreeData() {
        List<SysDictTypeEntity> dictList = this.list();
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (SysDictTypeEntity dict : dictList){
            if ("0".equals(dict.getStatus())){
                Ztree ztree = new Ztree();
                ztree.setId(dict.getId());
                ztree.setTitle(transDictName(dict));
                ztree.setName(dict.getDictType());
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

    public String transDictName(SysDictTypeEntity dictType){
        StringBuffer sb = new StringBuffer();
        sb.append("(" + dictType.getDictName() + ")");
        sb.append("&nbsp;&nbsp;&nbsp;" + dictType.getDictType());
        return sb.toString();
    }

    public boolean deleteBatchByIds(Long[] ids) {
        for (Long dictId : ids){
            SysDictTypeEntity dictType = this.getById(dictId);
            if (!ToolUtil.isEmpty(dictType) &&
                    sysDictDataService.countDictDataByType(dictType.getDictType()) > 0){
                throw new RxcException(String.format("%1$s已分配,不能删除", dictType.getDictName()),"50001");
            }
        }
       return  this.removeByIds(Arrays.asList(ids));
    }


    public boolean clearDictRedis(){
        return redisUtil.deletes(RedisKeys.getDictConfigKey("*"));
    }

}
