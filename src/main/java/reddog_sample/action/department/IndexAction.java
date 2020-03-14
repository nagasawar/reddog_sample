package reddog_sample.action.department;

import javax.annotation.Resource;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.form.department.IndexForm;
import reddog_sample.service.SF;

// 所属 > 所属検索

public class IndexAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected IndexForm indexForm;

    // --------------------------------------------------------
    @Execute(validator = false)
    public String index() throws Exception {

        // ---------------------------------------------------------------
        // データを取得、Formにセット
        // ---------------------------------------------------------------
        indexForm.departments = SF.department.findAll();

        return "index.jsp";
    }
}
