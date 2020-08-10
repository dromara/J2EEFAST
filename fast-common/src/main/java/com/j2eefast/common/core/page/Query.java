package com.j2eefast.common.core.page;

import java.util.LinkedHashMap;
import java.util.Map;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.common.core.xss.SQLFilter;
import cn.hutool.core.util.StrUtil;

/**
 * 页面分页查询参数
 * @author zhouzhou
 * @date 2020-03-12 22:05
 */
public class Query <T> extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	/**
	 * mybatis-plus分页参数
	 */
	private Page<T> page;
	/**
	 * 当前页码
	 */
	private int currPage = 1;
	/**
	 * 每页条数
	 */
	private int limit = 10;
	
	public Query(Map<String, Object> params) {
		this.putAll(params);

		// 分页参数
		if (params.get("__page") != null) {
			currPage = Integer.parseInt((String) params.get("__page"));
		}
		if (params.get("__limit") != null) {
			limit = Integer.parseInt((String) params.get("__limit"));
		}
		// 对多级联动支持
		if(params.get("__seletPage") != null && params.get("__seletPage").equals("true")){
			if (params.get("pageNumber") != null) {
				currPage = Integer.parseInt((String) params.get("pageNumber"));
			}
			if (params.get("pageSize") != null) {
				limit = Integer.parseInt((String) params.get("pageSize"));
			}
		}


		this.put("offset", (currPage - 1) * limit);
		this.put("page", currPage);
		this.put("limit", limit);

		// 防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
		String sidx = SQLFilter.sqlInject((String) params.get("__sidx"));
		String order = SQLFilter.sqlInject((String) params.get("__order"));
		this.put("sidx", sidx);
		this.put("order", order);

		// mybatis-plus分页
		this.page = new Page<>(currPage, limit);

		// 排序
		if (ToolUtil.isNotEmpty(sidx) && ToolUtil.isNotEmpty(order)) {
			sidx = StrUtil.toUnderlineCase(sidx);
			if("ASC".equalsIgnoreCase(order)) {
				this.page.addOrder(OrderItem.asc(sidx));
			}else {
				this.page.addOrder(OrderItem.desc(sidx));
			}
		}

	}

	public Page<T> getPage() {
		return page;
	}

	public int getCurrPage() {
		return currPage;
	}

	public int getLimit() {
		return limit;
	}

//	public String getSidx(){
//		return  (String)this.get("sidx");
//	}
//
//	public String getOrder(){
//		return  (String)this.get("order");
//	}
}
