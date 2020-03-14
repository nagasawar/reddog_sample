package reddog_sample.action.userRole;

import javax.annotation.Resource;

import org.seasar.framework.beans.util.Beans;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.form.userRole.InputForm;
import reddog_sample.ignore.entity.RdUserRole;
import reddog_sample.service.SF;
import reddog_sample.util.annotation.AjaxCheck;
import reddog_sample.util.annotation.TokenSave;

// ユーザ権限 > 編集表示

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
        RdUserRole rdUserRole = SF.rdUserRole.getOne(inputForm.ajaxEditorKeyId);
        if (rdUserRole == null) {
            return forwardBerrorAjax(inputForm, "ユーザ権限が存在しません");
        }

        // ---------------------------------------------------------------
        // Form値セット
        // ---------------------------------------------------------------
        Beans.copy(rdUserRole, inputForm)
             .execute();

        return "edit.jsp";
    }
}
