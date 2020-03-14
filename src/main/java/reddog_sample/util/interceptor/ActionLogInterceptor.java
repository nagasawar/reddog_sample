package reddog_sample.util.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.struts.annotation.Execute;

/**
 * Actionの@Executeアノテーションが付いたメソッドが呼ばれたときに開始・終了ログを出力する
 */
public class ActionLogInterceptor extends AbstractInterceptor {

    Logger logger = Logger.getLogger(getClass());

    public Object invoke(MethodInvocation invocation) throws Throwable {

        // -----------------------------------
        // [前処理] 開始ログ出力
        // -----------------------------------
        if (isExecuteMethod(invocation)) {
            before(invocation);
        }

        // -----------------------------------
        // 主処理
        // -----------------------------------
        Object object = invocation.proceed();

        // -----------------------------------
        // [後処理] 終了ログ出力
        // -----------------------------------
        if (isExecuteMethod(invocation)) {
            after();
        }

        return object;
    }

    /**
     * Executeアノテーションであるかチェックする
     */
    private Boolean isExecuteMethod(MethodInvocation invocation) {
        return invocation.getMethod().isAnnotationPresent(Execute.class);
    }

    private void before(MethodInvocation invocation) {
        HttpServletRequest req = SingletonS2Container.getComponent(HttpServletRequest.class);

        String className = getTargetClass(invocation).getName();
        String methodName = invocation.getMethod().getName();

        logger.info("[START] ------------------------------------------------------");
        logger.info("URI: " + req.getRequestURI());
        logger.info("queryString: " + req.getQueryString());
        logger.info("action: " + className);
        logger.info("method: " + methodName);
        logger.info("");
    }

    private void after() {

        logger.info("[END] --------------------------------------------------------");
    }
}