package reddog_sample.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.struts.util.ActionMessagesUtil;

import reddog_sample.form.AbstractBaseForm;

/**
 * Action用のスーパークラス<br />
 */
abstract public class AbstractBaseAction {

    /**
     * @deprecated @AjaxCheckを使用すること
     */
    @Deprecated
    protected final String ERR_MSG_AJAX = "Ajax通信ではありません";

    /**
     * @deprecated @PostCheckを使用すること
     */
    @Deprecated
    protected final String ERR_MSG_POST = "POST通信ではありません";

    public HttpSession session;

    /**
     * 成功メッセージをセットする。
     * layout.jsp にてメッセージを表示している
     *
     * @param msg
     */
    public void setSuccessMsg(String msg) {
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(msg, false));
        ActionMessagesUtil.addMessages(session, messages);
    }

    /**
     * エラーメッセージを追加する。
     *
     * @param msg
     */
    public void addErrorMsg(String msg) {
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(msg, false));
        ActionMessagesUtil.addErrors(session, messages);
    }

    /**
     * 業務系共通エラーへ遷移させる
     *
     * @param form
     * @param msg
     * @return
     */
    public String forwardBerror(AbstractBaseForm form, String msg) {
        form.errorMsg = msg;
        return "/error/berror.jsp";
    }

    /**
     * 業務系共通エラーへ遷移させる Ajaxバージョン
     *
     * @param form
     * @param msg
     * @return
     */
    public String forwardBerrorAjax(AbstractBaseForm form, String msg) {
        form.errorMsg = msg;
        return "/error/berrorAjax.jsp";
    }

    /**
     * Ajax通信であるかチェックする
     * @return Ajax通信の場合true
     * @deprecated @AjaxCheckを使用すること
     */
    @Deprecated
    public boolean isAjax() {
        HttpServletRequest  req = SingletonS2Container.getComponent(HttpServletRequest.class);
        return (req.getHeader("X-Requested-With") != null);
    }

    /**
     * POST通信であるかチェックする
     * @return POST通信の場合true
     * @deprecated @PostCheckを使用すること
     */
    @Deprecated
    public boolean isPost() {
        HttpServletRequest  req = SingletonS2Container.getComponent(HttpServletRequest.class);
        return req.getMethod().toLowerCase().equals("post");
    }
}
