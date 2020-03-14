package reddog_sample.form.employeeOutput;

import java.util.List;

import org.seasar.struts.annotation.Required;

import reddog_sample.entity.Employee;
import reddog_sample.form.AbstractBaseForm;
import reddog_sample.service.SF;

// 社員出力Formクラス
public class EmployeeOutputForm extends AbstractBaseForm {

    public EmployeeOutputForm() {

        this.employees = SF.employee.findAll();
    }

    public List<Employee> employees;

    @Required
    public String employeeId;

    public void reset() {
        employeeId   = "";
    }
}
