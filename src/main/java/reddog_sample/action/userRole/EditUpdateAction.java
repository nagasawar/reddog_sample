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
import reddog_sample.util.annotation.TokenCheck;

// ユーザ権限 > 更新実行
// ユーザ権限IDが任意で変更できるため、DELETE INSERTする

public class EditUpdateAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected InputForm inputForm;

    @AjaxCheck
    @TokenCheck
    @Execute(validator = true, input="/userRole/edit/edit.jsp")
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
        Beans.copy(inputForm, rdUserRole)
             .execute();

        // ---------------------------------------------------------------
        // 更新実行
        // ---------------------------------------------------------------
        SF.rdUserRole.update(rdUserRole);

        // ---------------------------------------------------------------
        // Ajaxダイアログを閉じさせる
        // ---------------------------------------------------------------
        inputForm.closeAjaxEditor = true;

        return "/userRole/edit/edit.jsp";
    }
}
