package reddog_sample.action.user;

import javax.annotation.Resource;

import org.seasar.framework.beans.util.Beans;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.form.user.InputForm;
import reddog_sample.ignore.entity.RdUser;
import reddog_sample.service.SF;
import reddog_sample.util.annotation.AjaxCheck;
import reddog_sample.util.annotation.TokenSave;

// ユーザ > 編集表示

public class EditAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected InputForm inputForm;

    @AjaxCheck
    @TokenSave
    @Execute(validator = false)
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
        Beans.copy(rdUser, inputForm)
             .execute();

        inputForm.beforeLoginId = rdUser.loginId;

        return "edit.jsp";
    }
}
