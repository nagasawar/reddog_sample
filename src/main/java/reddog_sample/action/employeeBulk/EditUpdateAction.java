package reddog_sample.action.employeeBulk;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.action.employeeBulk.handsonTable.HtEmployee;
import reddog_sample.form.employeeBulk.EmployeeBulkForm;
import reddog_sample.util.annotation.TokenCheck;
import reddog_sample.util.helper.handsonTable.HandsonTableBuilder;

// 社員一括編集 > 更新実行

public class EditUpdateAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected EmployeeBulkForm employeeBulkForm;

    @Resource
    protected HttpServletRequest request;

    @TokenCheck
    @Execute(validator = true, validate = "validate", input="/employeeBulk/index.jsp")
    public String index() throws Exception {

        HandsonTableBuilder htBuilder = this.setHtBuilder();

        htBuilder.setHtDatas(HtEmployee.class, employeeBulkForm.jsonStr);
        htBuilder.saveHtDatas();

        super.setSuccessMsg("更新しました");

        return "/employeeBulk/index.jsp";
    }

    public ActionMessages validate() throws Exception {
        ActionMessages errors = new ActionMessages();

        HandsonTableBuilder htBuilder = this.setHtBuilder();

        htBuilder.setHtDatas(HtEmployee.class, employeeBulkForm.jsonStr);
        htBuilder.validHtDatas();

        if (htBuilder.isInValid()) {
            errors.add("htError", new ActionMessage("表内にエラーがあります。詳細は表の最下部を参照してください", false));
        }

        return errors;
    }

    /**
     * handsonTableBuilderクラスのインスタンスを作成しFormへセットする。
     */
    private HandsonTableBuilder setHtBuilder() throws Exception {

        HandsonTableBuilder htBuilder = new HandsonTableBuilder(request, "dataTable-hidden");
        htBuilder.setHtColumns(HtEmployee.class);

        employeeBulkForm.htBuilder = htBuilder;

        return htBuilder;
    }
}
