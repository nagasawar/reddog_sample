package reddog_sample.util.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.SingletonS2Container;

import reddog_sample.util.annotation.AjaxCheck;
import reddog_sample.util.exception.AjaxCheckException;

/**
 * メソッドに@AjaxCheckアノテーションがあるときにAjax通信をチェックする
 */
public class AjaxCheckInterceptor extends AbstractInterceptor {
    private S2Container container;

    public Object invoke(MethodInvocation invocation) throws Throwable {

        AjaxCheck ajaxCheck = invocation.getMethod().getAnnotation(AjaxCheck.class);
        if (ajaxCheck != null) {
            HttpServletRequest  req = SingletonS2Container.getComponent(HttpServletRequest.class);
            if (req.getHeader("X-Requested-With") == null) {
                throw new AjaxCheckException();
            }
        }

        return invocation.proceed();
    }

    public S2Container getContainer() {
        return this.container;
    }

    public void setContainer(S2Container container) {
        this.container = container.getRoot();
    }
}