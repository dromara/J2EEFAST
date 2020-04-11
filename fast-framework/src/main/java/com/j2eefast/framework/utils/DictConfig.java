package com.j2eefast.framework.utils;


import com.j2eefast.common.core.utils.SpringUtil;
import com.j2eefast.framework.sys.entity.SysDictDataEntity;
import com.j2eefast.framework.sys.service.SysDictDataService;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @ProjectName: fast
 * @Package: com.j2eefast.framework.utils
 * @ClassName: DictConfig
 * @Author: zhouzhou Emall:18774995071@163.com
 * @Description: 首创 html调用 Freemarker 实现字典读取
 * @Date: 2019/12/18 17:25
 * @Version: 1.0
 */
@Component
public class DictConfig {
	
    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     * @return 参数键值
     */
    public List<SysDictDataEntity> getType(String dictType)
    {
        return SpringUtil.getBean(SysDictDataService.class).selectDictDataByType(dictType);
    }

    public String getDictListJson(String dictType){
        List<SysDictDataEntity> list = SpringUtil.getBean(SysDictDataService.class).selectDictDataByType(dictType);
        JSONArray jsonArray = JSONUtil.parseArray(list,false);
        return jsonArray.toString();
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    public String getLabel(String dictType, String dictValue)
    {
        return SpringUtil.getBean(SysDictDataService.class).selectDictLabel(dictType, dictValue);
    }

}
