package reddog_sample.util.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.struts.util.TokenProcessor;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.container.S2Container;

import reddog_sample.util.annotation.TokenCheck;
import reddog_sample.util.exception.TokenCheckException;

/**
 * メソッドに@TokenCheckアノテーションがあるときにトークンをチェックする
 */
public class TokenCheckInterceptor extends AbstractInterceptor {
    private S2Container container;

    public Object invoke(MethodInvocation invocation) throws Throwable {

        TokenCheck tokenCheck = invocation.getMethod().getAnnotation(TokenCheck.class);
        if (tokenCheck != null) {
            HttpServletRequest request =
                (HttpServletRequest) container.getExternalContext().getRequest();
            if (!TokenProcessor.getInstance().isTokenValid(request, false)) {
                throw new TokenCheckException();
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