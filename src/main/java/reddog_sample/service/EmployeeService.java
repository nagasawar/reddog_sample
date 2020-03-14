package reddog_sample.service;

import java.util.List;

import javax.annotation.Resource;

import org.seasar.extension.jdbc.JdbcManager;

import reddog_sample.entity.Employee;

public class EmployeeService {

    @Resource
    JdbcManager jdbcManager;

    /**
     * 主キーに紐づくデータを1件取得する
     *
     * @param id
     * @return
     */
    public Employee getOne(String id) {

        Employee employee = jdbcManager.from(Employee.class)
            .id(id)
            .getSingleResult();

        return employee;
    }

    /**
     * 主キーに紐づくデータが存在するか確認する
     *
     * @param id
     * @return
     */
    public boolean exist(String id) {

        Employee employee = jdbcManager.from(Employee.class)
            .where("employee_id = ?", id)
            .getSingleResult();

        return (employee != null);
    }

    /**
     * 所属IDに紐づくデータが存在するか確認する
     *
     * @param departmentId
     * @return
     */
    public boolean existByDepartmentId(String departmentId) {

        long count = jdbcManager.from(Employee.class)
            .where("department_id = ?", departmentId)
            .getCount();

        return (count > 0);
    }

    /**
     * 以前登録していた主キー以外で、新しく指定した主キーに紐づくデータが存在するか確認する
     *
     * @param id
     * @param beforeId
     * @return
     */
    public boolean existByNotBeforeId(String id, String beforeId) {

        Employee employee = jdbcManager.from(Employee.class)
            .where("employee_id = ? AND employee_id <> ?", id, beforeId)
            .getSingleResult();

        return (employee != null);
    }

    /**
     * 全件リストを取得する
     * @return
     */
    public List<Employee> findAll() {

        List<Employee> employees = jdbcManager.from(Employee.class)
            .innerJoin("department")
            .getResultList();

        return employees;
    }

    /**
     * 新規作成する
     * @param employee
     */
    public void insert(Employee employee) {
        jdbcManager.insert(employee).execute();
    }

    /**
     * 更新する
     * @param employee
     */
    public void update(Employee employee) {
        jdbcManager.update(employee).execute();
    }

    /**
     * 削除する
     * @param employee
     */
    public void delete(Employee employee) {
        jdbcManager.delete(employee).execute();
    }
}
