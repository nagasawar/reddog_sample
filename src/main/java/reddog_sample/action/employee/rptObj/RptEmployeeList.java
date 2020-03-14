package reddog_sample.action.employee.rptObj;

import java.util.List;

import lombok.Data;

/**
 * 社員一覧PDF値クラス
 */
@Data
public class RptEmployeeList {

    private List<RptEmployee> employees;
}
