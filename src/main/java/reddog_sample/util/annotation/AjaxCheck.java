package reddog_sample.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ajax通信のチェック アノテーション<br>
 * Ajax通信でなければ「/WEB-INF/view/error/ajax.jsp」画面を表示する。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AjaxCheck {
}
