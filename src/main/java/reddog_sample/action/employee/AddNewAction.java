package reddog_sample.action.employee;

import javax.annotation.Resource;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.form.employee.InputForm;
import reddog_sample.util.annotation.AjaxCheck;
import reddog_sample.util.annotation.TokenSave;

// 社員 > 新規作成表示

public class AddNewAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected InputForm inputForm;

    @AjaxCheck
    @TokenSave
    @Execute(validator = false)
    public String index() throws Exception {

        return "addNew.jsp";
    }
}
