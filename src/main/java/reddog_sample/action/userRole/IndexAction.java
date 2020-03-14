package reddog_sample.action.userRole;

import javax.annotation.Resource;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.form.userRole.IndexForm;
import reddog_sample.service.SF;

// ユーザ権限 > ユーザ権限検索

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
        indexForm.rdUserRoles = SF.rdUserRole.findAll();

        return "index.jsp";
    }
}
