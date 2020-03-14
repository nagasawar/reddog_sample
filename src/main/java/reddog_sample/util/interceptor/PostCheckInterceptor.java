package reddog_sample.util.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.SingletonS2Container;

import reddog_sample.util.annotation.PostCheck;
import reddog_sample.util.exception.PostCheckException;

/**
 * メソッドに@PostCheckアノテーションがあるときにPOST通信をチェックする
 */
public class PostCheckInterceptor extends AbstractInterceptor {
    private S2Container container;

    public Object invoke(MethodInvocation invocation) throws Throwable {

        PostCheck postCheck = invocation.getMethod().getAnnotation(PostCheck.class);
        if (postCheck != null) {
            HttpServletRequest req = SingletonS2Container.getComponent(HttpServletRequest.class);
            if (!req.getMethod().toLowerCase().equals("post")) {
                throw new PostCheckException();
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