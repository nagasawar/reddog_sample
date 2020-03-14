package reddog_sample.action.employee;

import javax.annotation.Resource;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.form.employee.IndexForm;
import reddog_sample.service.SF;

// 社員 > 社員検索

public class IndexAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected IndexForm indexForm;

    // --------------------------------------------------------
    @Execute(validator = false)
    public String index() throws Exception {

        // ---------------------------------------------------------------
        // データを取得してFormへセット
        // ---------------------------------------------------------------
        indexForm.employees = SF.employee.findAll();

        return "index.jsp";
    }
}
