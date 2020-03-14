package reddog_sample.action.employeeOutput;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.form.employeeOutput.EmployeeOutputForm;

// 社員出力 > 初回表示

public class IndexAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected EmployeeOutputForm employeeOutputForm;

    @Resource
    protected HttpServletRequest request;

    // --------------------------------------------------------
    @Execute(validator = false)
    public String index() throws Exception {

        return "index.jsp";
    }
}
