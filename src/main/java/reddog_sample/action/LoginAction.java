package reddog_sample.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.util.StringUtil;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.exception.ActionMessagesException;

import reddog_sample.form.LoginForm;
import reddog_sample.ignore.dto.RdUserDto;
import reddog_sample.ignore.entity.RdUser;
import reddog_sample.service.SF;
import reddog_sample.util.annotation.TokenCheck;
import reddog_sample.util.annotation.TokenSave;


public class LoginAction extends AbstractBaseAction {

    /** アクションフォーム */
    @Resource
    @ActionForm
    protected LoginForm loginForm;

    /** 認証情報を格納するセッションスコープ */
    @Resource
    protected RdUserDto rdUserDto;

    /** ログイン画面を表示する実行メソッド */
    @TokenSave
    @Execute(validator=false)
    public String index() {
        return "index.jsp";
    }

    /** ログインボタンがクリックされた際に呼び出される実行メソッド */
    @TokenCheck
    @Execute(validator=true, input="index.jsp")
    public String login() {
        HttpServletRequest req = SingletonS2Container.getComponent(HttpServletRequest.class);

        // ログイン画面で入力されたユーザ名とパスワードでユーザを検索
        RdUser rdUser = SF.rdUser.getUser(loginForm.loginId, loginForm.password);

        if (rdUser != null) {
            // ログイン成功

            // ログインに成功したユーザIDをセッションDTOへ保存
            rdUserDto.userId = rdUser.userId;

            // 既存セッション破棄
            HttpSession session = req.getSession(true);
            session.invalidate();

            // 新規セッションにDTOを格納
            HttpSession newSession = req.getSession(true);
            newSession.setAttribute("rdUserDto", rdUserDto);

            if (!StringUtil.isEmpty(loginForm.url)) {
                // 遷移後のURLが指定されていた場合、該当URLに遷移する
                return loginForm.url+ "?redirect=true";
            }

            // メニュー画面に遷移する
            return "/?redirect=true";

        } else {
            throw new ActionMessagesException("ログインできませんでした", false);
        }
    }
}
