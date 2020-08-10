package com.j2eefast.generator.gen.mapper;

import com.j2eefast.generator.gen.entity.ExampleTestEntity;
import java.io.Serializable;
import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
/**
 *
 * example_testDAO接口
 * @author: ZhouZhou
 * @date 2020-08-07 11:33
 */
public interface ExampleTestMapper extends BaseMapper<ExampleTestEntity> {

	/**
     * 自定义分页查询
     * @param  page 
     * @param  exampleTestEntity 实体类
     */
     Page<ExampleTestEntity> findPage(IPage<ExampleTestEntity> page,
                                       @Param("exampleTest") ExampleTestEntity exampleTestEntity,
                                       @Param("sql_filter") String sql_filter);

     ExampleTestEntity findExampleTestById(Serializable id);


     List<ExampleTestEntity> findList(ExampleTestEntity exampleTestEntity);

     /**删除相关方法  使用mybatis-plus集成的 **/
}
