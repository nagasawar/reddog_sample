package reddog_sample.action.user;

import javax.annotation.Resource;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.framework.beans.util.Beans;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.form.user.InputForm;
import reddog_sample.ignore.entity.RdUser;
import reddog_sample.service.SF;
import reddog_sample.util.annotation.AjaxCheck;
import reddog_sample.util.annotation.TokenCheck;

// ユーザ > 新規作成実行

public class AddNewCreateAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected InputForm inputForm;

    @AjaxCheck
    @TokenCheck
    @Execute(validator = true, validate = "validate", input="/user/addNew/addNew.jsp")
    public String index() throws Exception {

        // ---------------------------------------------------------------
        // 空のデータ作成
        // ---------------------------------------------------------------
        RdUser rdUser = new RdUser();

        // ---------------------------------------------------------------
        // Form値セット
        // ---------------------------------------------------------------
        Beans.copy(inputForm, rdUser)
             .execute();

        // ---------------------------------------------------------------
        // 作成実行
        // ---------------------------------------------------------------
        SF.rdUser.insert(rdUser);

        // ---------------------------------------------------------------
        // Ajaxダイアログを閉じさせる
        // ---------------------------------------------------------------
        inputForm.closeAjaxEditor = true;

        // ---------------------------------------------------------------
        // Ajaxダイアログに新規のキーをセット
        // ---------------------------------------------------------------
        inputForm.ajaxEditorKeyId = String.valueOf(rdUser.userId);

        return "/user/addNew/addNew.jsp";
    }

    public ActionMessages validate() {
        ActionMessages errors = new ActionMessages();

        // ログインID 一意チェック
        if (SF.rdUser.existByLoginId(inputForm.loginId)) {
            errors.add("loginId", new ActionMessage("このログインIDはすでに存在します", false));
        }

        return errors;
    }
}
