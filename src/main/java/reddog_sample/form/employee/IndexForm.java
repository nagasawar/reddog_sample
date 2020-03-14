package reddog_sample.form.employee;

import java.util.List;

import reddog_sample.entity.Employee;
import reddog_sample.form.AbstractBaseForm;

// 社員 > Formクラス

public class IndexForm extends AbstractBaseForm {

    // 検索結果 社員リスト
    public List<Employee> employees;
}
