package reddog_sample.action.userRole;

import javax.annotation.Resource;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.framework.beans.util.Beans;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.form.userRole.InputForm;
import reddog_sample.ignore.entity.RdUserRole;
import reddog_sample.service.SF;
import reddog_sample.util.annotation.AjaxCheck;
import reddog_sample.util.annotation.TokenCheck;

// ユーザ権限 > 新規作成実行

public class AddNewCreateAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected InputForm inputForm;

    @AjaxCheck
    @TokenCheck
    @Execute(validator = true, validate = "validate", input="/userRole/addNew/addNew.jsp")
    public String index() throws Exception {

        // ---------------------------------------------------------------
        // 空データ作成
        // ---------------------------------------------------------------
        RdUserRole rdUserRole = new RdUserRole();

        // ---------------------------------------------------------------
        // Form値セット
        // ---------------------------------------------------------------
        Beans.copy(inputForm, rdUserRole)
             .execute();

        // ---------------------------------------------------------------
        // 作成実行
        // ---------------------------------------------------------------
        SF.rdUserRole.insert(rdUserRole);

        // ---------------------------------------------------------------
        // Ajaxダイアログを閉じさせる
        // ---------------------------------------------------------------
        inputForm.closeAjaxEditor = true;

        // ---------------------------------------------------------------
        // Ajaxダイアログに新規のキーをセット
        // ---------------------------------------------------------------
        inputForm.ajaxEditorKeyId = rdUserRole.userRoleId;

        return "/userRole/addNew/addNew.jsp";
    }

    public ActionMessages validate() {
        ActionMessages errors = new ActionMessages();

        // ユーザ権限ID 一意チェック
        if (SF.rdUserRole.exist(inputForm.userRoleId)) {
            errors.add("userRoleId", new ActionMessage("このユーザ権限IDはすでに存在します", false));
        }

        return errors;
    }
}
