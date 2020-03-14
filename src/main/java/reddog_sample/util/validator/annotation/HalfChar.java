package reddog_sample.util.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.seasar.struts.annotation.Arg;
import org.seasar.struts.annotation.Msg;
import org.seasar.struts.annotation.Validator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Validator("halfChar")
public @interface HalfChar {

    Msg msg() default @Msg(key = "errors.halfChar");

    Arg arg0() default @Arg(key = "");

    String target() default "";
}
