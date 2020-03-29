package com.fast.common.core.enums;

/**
 * 业务操作类型 
 * @author zhouzhou
 * @date 2020-03-12 14:12
 */
public enum BusinessType {
	/**
     * 其它
     */
    OTHER,

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 授权
     */
    GRANT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 生成代码
     */
    GENCODE,

    /**
     * 清空
     */
    CLEAN,

    /**
     * 执行
     */
    RUN,
}
