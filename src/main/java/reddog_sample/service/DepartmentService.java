package reddog_sample.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.seasar.extension.jdbc.JdbcManager;

import reddog_sample.entity.Department;

public class DepartmentService {

    @Resource
    JdbcManager jdbcManager;

    /**
     * 主キーに紐づくデータを1件取得する
     *
     * @param id
     * @return
     */
    public Department getOne(String id) {

        Department department = jdbcManager.from(Department.class)
            .id(id)
            .getSingleResult();

        return department;
    }

    /**
     * 主キーに紐づく所属が存在するか確認する
     *
     * @param id
     * @return
     */
    public boolean exist(String id) {

        Department department = jdbcManager.from(Department.class)
            .id(id)
            .getSingleResult();

        return (department != null);
    }

    /**
     * 全件リストを取得する
     * @return
     */
    public List<Department> findAll() {

        List<Department> departments = jdbcManager.from(Department.class)
            .getResultList();

        return departments;
    }

    /**
     * 所属ID,所属名のmapを取得する
     * @return
     */
    public Map<String, String> getIdNameMap() {

        List<Department> departments = jdbcManager.from(Department.class)
            .getResultList();

        Map<String, String> map = new TreeMap<String, String>();

        for (Department dep : departments) {
            map.put(dep.departmentId, dep.departmentName);
        }

        return map;
    }

    /**
     * 新規作成する
     * @param department
     */
    public void insert(Department department) {
        jdbcManager.insert(department).execute();
    }

    /**
     * 更新する
     * @param department
     */
    public void update(Department department) {
        jdbcManager.update(department).execute();
    }

    /**
     * 削除する
     * @param department
     */
    public void delete(Department department) {
        jdbcManager.delete(department).execute();
    }
}
