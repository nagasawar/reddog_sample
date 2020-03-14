package reddog_sample.util.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.util.ArrayUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.util.RequestUtil;

import reddog_sample.ignore.dto.RdUserDto;
import reddog_sample.ignore.entity.RdPageAuth;
import reddog_sample.service.RdPageAuthService;
import reddog_sample.service.RdUserService;
import reddog_sample.util.exception.PageAuthCheckException;

/**
 * rd_path_authテーブルを元に画面認証処理を行う
 * アノテーションなしで常に起動する
 */
public class PageAuthCheckInterceptor extends AbstractInterceptor {

    Logger logger = Logger.getLogger(getClass());

    public Object invoke(MethodInvocation invocation) throws Throwable {

        // @Executeアノテーションがなければ終了
        if (!invocation.getMethod().isAnnotationPresent(Execute.class)) {
            return invocation.proceed();
        }

        RdUserDto rdUserDto = SingletonS2Container.getComponent(RdUserDto.class);
        String path = this.removePreffix( RequestUtil.getPath() );

        RdPageAuthService paService = SingletonS2Container.getComponent(RdPageAuthService.class);
        RdPageAuth pa = paService.getByPath(path);

        // -----------------------------------
        // rd_path_authで制限されていない場合は認証なしで終了
        // -----------------------------------
        if (pa == null) {
            return invocation.proceed();
        }

        // -----------------------------------
        // ログインチェックが入っていない場合は認証なしで終了
        // -----------------------------------
        if (!pa.loginCheck){
            return invocation.proceed();
        }

        // -----------------------------------
        // ログイン認証
        //   ログイン以外の画面で、ログイン認証し、認証に失敗した場合はログイン画面へ遷移する
        // -----------------------------------
        if (!path.equals("/login") && !rdUserDto.isLogin()) {

            HttpServletRequest req = RequestUtil.getRequest();

            // ログイン画面URL
            String loginPath = "/login";

            // ログイン後の遷移先URLパラメータを作成
            String query = req.getQueryString();
            query = (StringUtil.isEmpty(query))? "": "&"+ query;

            // 遷移前のurlクエリパラメータを取得
            String motoUrl = req.getParameter("url");

            // 遷移前のurlクエリパラメータがないときは、遷移前のURL自体をurlクエリパラメータの値とする
            if (motoUrl == null) {
                String url = this.removePreffix(req.getServletPath()) + query;
                loginPath += "?redirect=true&url="+ url;

            } else {
                loginPath += "?redirect=true"+ query;
            }

            return loginPath;
        }

        // -----------------------------------
        // ユーザ権限認証
        // -----------------------------------
        RdUserService userService = SingletonS2Container.getComponent(RdUserService.class);

        String userRoleId = String.valueOf(userService.getUserRoleId(rdUserDto.userId));
        String[] roleArray = pa.roles.split(",");

        if (!roleArray[0].equals("")
                && !ArrayUtil.contains(roleArray, userRoleId)) {
            throw new PageAuthCheckException();
        }

        return invocation.proceed();
    }

    /**
     * 「index.do」「.do」を取り除く
     *
     * @param path
     * @return
     */
    private String removePreffix(String path) {

        if (path == null) {
            return null;
        }

        path = path.replace("/index.do", "");
        path = path.replace(".do", "");

        // トップページが空になってしまうので対応
        if (path.equals("")) {
            path = "/";
        }

        return path;
    }}