package chya.zhyy.entity.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImportCol {

	/**
	 * 支持导入，默认为ture
	 */
	boolean support() default true;
	
	/**
	 * 是否非空，默认可为空
	 */
	boolean nullable() default true;

	/**
	 * 关联表实体类 
	 */
	Class<?> foreginEntity() default Object.class;

	/**
	 * 关联表实体属性名称
	 */
	String refName() default "no_ref_name";
	
	/**
	 * 标题
	 */
	String title() default "";
}
