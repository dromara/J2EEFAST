package com.j2eefast.framework.bussiness.aop;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.j2eefast.common.core.base.entity.LoginUserEntity;
import com.j2eefast.framework.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.annotation.DataFilter;
import com.j2eefast.framework.sys.service.SysCompService;
import com.j2eefast.framework.sys.service.SysDeptService;
import com.j2eefast.framework.sys.service.SysUserDeptService;
import com.j2eefast.framework.utils.Constant;
import cn.hutool.core.bean.BeanUtil;

/**
 * 
 * @Description:数据过滤，切面处理类
 * @author zhouzhou 18774995071@163.com
 * @time 2018-12-03 17:25
 *
 */
@Order(5)
@Aspect
@Component
public class DataFilterAspect {
	@Autowired
	private SysDeptService sysDeptService;
	
	@Autowired
	private SysUserDeptService sysUserDeptService;
	
	@Autowired
	private SysCompService sysCompService;

	@Pointcut("@annotation(com.j2eefast.framework.annotation.DataFilter)")
	public void dataFilterCut() {

	}

	@SuppressWarnings("unchecked")
	@Before("dataFilterCut()")
	public void dataFilter(JoinPoint point) throws Throwable {
		
		System.out.println("目标方法名为:" + point.getSignature().getName());
        System.out.println("目标方法所属类的简单类名:" +        point.getSignature().getDeclaringType().getSimpleName());
        System.out.println("目标方法所属类的类名:" + point.getSignature().getDeclaringTypeName());
        System.out.println("目标方法声明类型:" + Modifier.toString(point.getSignature().getModifiers()));

        //Object result =  point.proceed()
		Object params = point.getArgs()[0];
		if (params != null && params instanceof Map) {

			LoginUserEntity user = UserUtils.getUserInfo();

			// 如果不是超级管理员，则进行数据过滤
			if (!user.getId().equals(Constant.SUPER_ADMIN)) {
				//若用户为最大管理员切公司为总公司
				if(!(user.getCompId() == 0 &&
						UserUtils.hasRole(Constant.SU_ADMIN))) {
					Map map = (Map) params;
					map.put(Constant.SQL_FILTER, getSQLFilter(user, point));
				}
			}
			return;
		}

		throw new RxcException("数据权限接口，只能是Map类型参数，且不能为NULL");
	}

	/**
	 * 获取数据过滤的SQL
	 */
	private String getSQLFilter(LoginUserEntity user, JoinPoint point) {

		MethodSignature signature = (MethodSignature) point.getSignature();
		DataFilter dataFilter = signature.getMethod().getAnnotation(DataFilter.class);
		// 获取表的别名
		String tableAlias = dataFilter.tableAlias();
		
		StringBuilder sqlFilter = new StringBuilder();
		
		if (ToolUtil.isNotEmpty(tableAlias)) {
			tableAlias += ".";
		}
		
		
		
		// 获取 sys_dept 表关联 subDept ture
		if(dataFilter.subDept()) {
			
			// 用户对应sys_dept 
			Set<Long> deptIdList = new HashSet<>();
			
			// 用户对应sys_dept ID列表
			List<Long> roleIdList = sysUserDeptService.findDeptIdList(user.getId());
			if(roleIdList!=null && roleIdList.size() > 0) {
				deptIdList.addAll(roleIdList);
				//获取子列表
				for(Long l : roleIdList) {
					List<Long> subDeptIdList = sysDeptService.getSubDeptIdList(l);
					if(subDeptIdList.size() > 0) {
						deptIdList.addAll(subDeptIdList);
					}
				}
			}
			if(deptIdList != null && deptIdList.size() > 0) {
				sqlFilter.append(" (");
				sqlFilter.append(tableAlias).append(dataFilter.deptId()).append(" in(")
				.append(StringUtils.join(deptIdList, ",")).append(")");
			}
			
			if(ToolUtil.isNotEmpty(sqlFilter.toString())) {
				sqlFilter.append(")");
			}
			
		}
		
		
		//用户对应sys_comp 表关联
		if(dataFilter.subComp()) {
			if("sys_poi.".equals(tableAlias)) {
				sqlFilter.append(" and");
			}
			
			// 用户对应sys_comp 
			Set<Long> deptIdList = new HashSet<>();
			
			List<Long> compChildId = sysCompService.getSubDeptIdList(user.getCompId());
			
			compChildId.add(user.getCompId());
			
			deptIdList.addAll(compChildId);
			
			if(deptIdList != null && deptIdList.size() > 0) {
				sqlFilter.append(" (");
				sqlFilter.append(tableAlias).append(dataFilter.compId()).append(" in(")
				.append(StringUtils.join(deptIdList, ",")).append(")");
			}
			
			if(ToolUtil.isNotEmpty(sqlFilter.toString())) {
				sqlFilter.append(")");
			}
		}

		//用户对
		if(dataFilter.subData()){
			sqlFilter.append(" (");
			sqlFilter.append(tableAlias).append(dataFilter.compId()).append(" = ")
					.append(user.getCompId()+"").append(")");
		}

//		// 用户角色对应的部门ID列表
//		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(user.getUserId());
//		if (roleIdList.size() > 0) {
//			List<Long> userDeptIdList = sysRoleDeptService
//					.queryDeptIdList(roleIdList.toArray(new Long[roleIdList.size()]));
//			deptIdList.addAll(userDeptIdList);
//		}
//
//		// 用户子部门ID列表
//		if (dataFilter.subDept()) {
//			List<Long> subDeptIdList = sysDeptService.getSubDeptIdList(user.getDeptId());
//			deptIdList.addAll(subDeptIdList);
//		}
//
//		StringBuilder sqlFilter = new StringBuilder();
//		sqlFilter.append(" (");
//
//		if (deptIdList.size() > 0) {
//			sqlFilter.append(tableAlias).append(dataFilter.deptId()).append(" in(")
//					.append(StringUtils.join(deptIdList, ",")).append(")");
//		}
//
//		// 只针对用户ID
		if (dataFilter.user()) {
			sqlFilter.append(ToolUtil.isNotEmpty(sqlFilter.toString())?" and ":"")
					.append(dataFilter.setField()+"='"+ BeanUtil.getFieldValue(user,dataFilter.setField()) +"'");
		}
		System.out.println(sqlFilter.toString());
		return sqlFilter.toString();
	}
}
