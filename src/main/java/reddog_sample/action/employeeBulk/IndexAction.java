package reddog_sample.action.employeeBulk;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.action.employeeBulk.handsonTable.HtEmployee;
import reddog_sample.entity.Employee;
import reddog_sample.form.employeeBulk.EmployeeBulkForm;
import reddog_sample.service.SF;
import reddog_sample.util.annotation.TokenSave;
import reddog_sample.util.helper.handsonTable.HandsonTableBuilder;

// 社員一括編集 > 初回表示

public class IndexAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected EmployeeBulkForm employeeBulkForm;

    @Resource
    protected HttpServletRequest request;

    // --------------------------------------------------------
    @TokenSave
    @Execute(validator = false)
    public String index() throws Exception {

        HandsonTableBuilder htBuilder = new HandsonTableBuilder(request, "dataTable-hidden");
        htBuilder.setHtColumns(HtEmployee.class);
        employeeBulkForm.htBuilder = htBuilder;

        List<Employee> employees = SF.employee.findAll();
        htBuilder.setHtDatas(HtEmployee.class, employees);

        return "index.jsp";
    }
}
