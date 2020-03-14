package reddog_sample.util.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.struts.util.TokenProcessor;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.container.S2Container;

import reddog_sample.util.annotation.TokenSave;

/**
 * メソッドに@Tokenアノテーションがあるときにトークンを発行する
 */
public class TokenSaveInterceptor extends AbstractInterceptor {
    private S2Container container;

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object ret = invocation.proceed();

        TokenSave tokenSave = invocation.getMethod().getAnnotation(TokenSave.class);
        if (tokenSave != null) {
            HttpServletRequest request =
                (HttpServletRequest) container.getExternalContext().getRequest();
            TokenProcessor.getInstance().saveToken(request);
        }

        return ret;
    }

    public S2Container getContainer() {
        return this.container;
    }

    public void setContainer(S2Container container) {
        this.container = container.getRoot();
    }
}