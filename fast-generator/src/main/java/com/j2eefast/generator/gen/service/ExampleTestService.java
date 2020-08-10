package com.j2eefast.generator.gen.service;


import com.j2eefast.generator.gen.entity.ExampleTestEntity;
import com.j2eefast.generator.gen.mapper.ExampleTestMapper;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.utils.Constant;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import java.util.Arrays;
import org.springframework.transaction.annotation.Transactional;
import com.j2eefast.framework.utils.FileUploadUtils;
/**
 *
 * 代码生成范例Service接口
 * @author: ZhouZhou
 * @date 2020-08-07 11:33
 */
@Service
public class ExampleTestService extends ServiceImpl<ExampleTestMapper,ExampleTestEntity> {
																																														
	@Resource
	private ExampleTestMapper exampleTestMapper;
		/**
	 * mybaitis-plus   单表页面分页查询
     */
	public PageUtil findPage(Map<String, Object> params) {
        QueryWrapper<ExampleTestEntity> queryWrapper = new QueryWrapper<ExampleTestEntity>();
           String code = (String) params.get("code");
        queryWrapper.like(ToolUtil.isNotEmpty(code), "code", code);
          String name = (String) params.get("name");
        queryWrapper.like(ToolUtil.isNotEmpty(name), "name", name);
          String email = (String) params.get("email");
        queryWrapper.like(ToolUtil.isNotEmpty(email), "email", email);
             String phone = (String) params.get("phone");
        queryWrapper.like(ToolUtil.isNotEmpty(phone), "phone", phone);
                                            		Page<ExampleTestEntity> page = exampleTestMapper.selectPage(new Query<ExampleTestEntity>(params).getPage(), queryWrapper);
				return new PageUtil(page);
    }

    /**
     * 自定义分页查询，含关联实体对像
     */
	public PageUtil findPage(Map<String, Object> params,ExampleTestEntity exampleTestEntity) {
		Page<ExampleTestEntity> page = exampleTestMapper.findPage(new Query<ExampleTestEntity>(params).getPage(),
																exampleTestEntity,
																(String) params.get(Constant.SQL_FILTER));
		return new PageUtil(page);
	}
	/**
     * 批量删除
     */
	@Transactional(rollbackFor = Exception.class)
	public boolean delExampleTestByIds(Long[] ids) {
		return this.removeByIds(Arrays.asList(ids));
	}

	/**
     * 单个删除
     */
	public boolean delExampleTestById(Long id) {
		return this.removeById(id);
	}

	/**
     * 保存
     */
	public boolean addExampleTest(ExampleTestEntity exampleTest){
																				
		//图片剪切数据转换
		exampleTest.setAvatar(FileUploadUtils.saveImgBase64(exampleTest.getAvatar()));		
		if(this.save(exampleTest)){			
			//更新关联附件信息						
			Long pkId =  exampleTest.getId();			
			FileUploadUtils.saveFileUpload(pkId,"example_test_file");						
			FileUploadUtils.saveFileUpload(pkId,"example_test_img");						
			return true;
		}
        return false;
    }

	/**
     * 修改根居ID
     */
	public boolean updateExampleTestById(ExampleTestEntity exampleTest) {
																				
		//图片剪切数据转换
		exampleTest.setAvatar(FileUploadUtils.saveImgBase64(exampleTest.getAvatar()));		
		if(this.updateById(exampleTest)){			
			//更新关联附件信息						
			Long pkId =  exampleTest.getId();			
			FileUploadUtils.saveFileUpload(pkId,"example_test_file");						
			FileUploadUtils.saveFileUpload(pkId,"example_test_img");						
			return true;
		}
		return false;
	}

	/**
     * 根居ID获取对象
     */
	public ExampleTestEntity findExampleTestById(Long id){
				return exampleTestMapper.findExampleTestById(id);		
	}
}
