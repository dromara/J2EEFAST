package com.j2eefast.common.core.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import com.j2eefast.common.core.exception.RxcException;


/**
 * hibernate-validator校验工具类
 * </p>
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 * @author zhouzhou
 * @date 2017-04-12 21:21
 */
public class ValidatorUtil {
	
	private static Validator 					validator;
	
	static {
			validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	
	/**
	 * 校验对象
	 * @param object 待校验对象
	 * @param groups 待校验的组
	 * @throws RRException 校验不通过，则报RxcException异常
	 */
	public static void validateEntity(Object object, Class<?>... groups) throws RxcException {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
		if (!constraintViolations.isEmpty()) {
			ConstraintViolation<Object> constraint = constraintViolations.iterator().next();
			throw new RxcException(constraint.getMessage());
		}
	}
}
