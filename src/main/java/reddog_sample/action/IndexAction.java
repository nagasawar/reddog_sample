package reddog_sample.action;

import java.util.List;

import javax.annotation.Resource;

import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.config.S2ActionMapping;
import org.seasar.struts.util.S2ActionMappingUtil;

import reddog_sample.entity.Department;
import reddog_sample.entity.Employee;
import reddog_sample.form.MainForm;
import reddog_sample.ignore.dto.RdUserDto;

/**
 * Topページ
 */
public class IndexAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected MainForm mainForm;

    @Resource
    protected RdUserDto rdUserDto;

    @Execute(validator = false)
    public String index() throws Exception {

        //FIXME 色々ここで試している

        S2ActionMapping mapping = S2ActionMappingUtil.getActionMapping();
        String m = mapping.getPath();

        JdbcManager jdbcManager =
            SingletonS2Container.getComponent(JdbcManager.class);

        // 職員を取得
        List<Employee> results = jdbcManager.from(Employee.class)
            .innerJoin("department")
            .where("employee_id = ?", 1)
            .getResultList();

        mainForm.employees = results;

        // 所属を取得
        List<Department> depList = jdbcManager.from(Department.class)
            .leftOuterJoin("employees")
            .getResultList();

        mainForm.test = "テスト・テスト";

        String t = "";
        String[] tl = t.split(",");

        //mainForm.userName = SF.rdUser.getUserName(rdUserDto.userId);

        return "index.jsp";
    }
}
