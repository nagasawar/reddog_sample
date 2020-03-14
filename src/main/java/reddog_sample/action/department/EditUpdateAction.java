package reddog_sample.action.department;

import javax.annotation.Resource;

import org.seasar.framework.beans.util.Beans;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.entity.Department;
import reddog_sample.form.department.InputForm;
import reddog_sample.service.SF;
import reddog_sample.util.annotation.AjaxCheck;
import reddog_sample.util.annotation.TokenCheck;

// 所属 > 更新実行
// 所属IDが任意で変更できるため、DELETE INSERTする

public class EditUpdateAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected InputForm inputForm;

    @AjaxCheck
    @TokenCheck
    @Execute(validator = true, input="/department/edit/edit.jsp")
    public String index() throws Exception {

        // ---------------------------------------------------------------
        // データを取得
        // ---------------------------------------------------------------
        Department department = SF.department.getOne(inputForm.ajaxEditorKeyId);
        if (department == null) {
            return forwardBerrorAjax(inputForm, "所属情報が存在しません");
        }

        // ---------------------------------------------------------------
        // Form値セット
        // ---------------------------------------------------------------
        Beans.copy(inputForm, department)
             .execute();

        // ---------------------------------------------------------------
        // 更新実行
        // ---------------------------------------------------------------
        SF.department.update(department);

        // ---------------------------------------------------------------
        // Ajaxダイアログを閉じさせる
        // ---------------------------------------------------------------
        inputForm.closeAjaxEditor = true;

        return "/department/edit/edit.jsp";
    }
}
