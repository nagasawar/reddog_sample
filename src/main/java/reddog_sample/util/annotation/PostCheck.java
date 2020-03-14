package reddog_sample.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * POST通信のチェック アノテーション<br>
 * POST通信でなければ「/WEB-INF/view/error/post.jsp」画面を表示する。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PostCheck {
}
