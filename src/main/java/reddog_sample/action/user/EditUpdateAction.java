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

// ユーザ > 更新実行

public class EditUpdateAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected InputForm inputForm;

    @AjaxCheck
    @TokenCheck
    @Execute(validator = true, validate = "validate", input="/user/edit/edit.jsp")
    public String index() throws Exception {

        // ---------------------------------------------------------------
        // データを取得
        // ---------------------------------------------------------------
        RdUser rdUser = SF.rdUser.getOne(inputForm.ajaxEditorKeyId);
        if (rdUser == null) {
            return forwardBerrorAjax(inputForm, "ユーザ情報が存在しません");
        }

        // ---------------------------------------------------------------
        // Form値セット
        // ---------------------------------------------------------------
        Beans.copy(inputForm, rdUser)
             .execute();

        // ---------------------------------------------------------------
        // 更新実行
        // ---------------------------------------------------------------
        SF.rdUser.update(rdUser);

        // ---------------------------------------------------------------
        // Ajaxダイアログを閉じさせる
        // ---------------------------------------------------------------
        inputForm.closeAjaxEditor = true;

        return "/user/edit/edit.jsp";
    }

    public ActionMessages validate() {
        ActionMessages errors = new ActionMessages();

        // ログインID 前回ログインID以外での一意チェック
        if (SF.rdUser.existByNotBeforeLoginId(inputForm.loginId, inputForm.beforeLoginId)) {
            errors.add("loginId", new ActionMessage("このログインIDはすでに存在します", false));
        }

        return errors;
    }
}
