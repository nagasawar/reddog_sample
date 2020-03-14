package reddog_sample.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * トークンのチェック アノテーション<br>
 * トークンが存在するかチェックし、存在しなければ「/WEB-INF/view/error/token.jsp」画面を表示する。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TokenCheck {
}
