package reddog_sample.action.department;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.entity.Department;
import reddog_sample.form.department.InputForm;
import reddog_sample.service.SF;
import reddog_sample.util.annotation.AjaxCheck;
import reddog_sample.util.helper.Logic;

// 所属 > 削除実行

public class DeleteAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected InputForm inputForm;

    private static Logger logger = Logger.getLogger(DeleteAction.class);

    @AjaxCheck
    @Execute(validator = false)
    public String index() throws Exception {

        boolean isDelete = true;

        // ---------------------------------------------------------------
        // データを取得
        // ---------------------------------------------------------------
        Department department = SF.department.getOne(inputForm.ajaxEditorKeyId);
        if (department == null) {
            logger.warn("所属情報が存在しません");
            isDelete = false;
        }

        // ---------------------------------------------------------------
        // 依存チェック 社員マスタで使用しているときは削除できないようにする。
        // ---------------------------------------------------------------
        boolean isUse = SF.employee.existByDepartmentId(inputForm.ajaxEditorKeyId);

        // ---------------------------------------------------------
        // 削除実行
        // ---------------------------------------------------------
        String viewMsg = "";

        if (isUse) {
            viewMsg = "使用中のため削除できません";

        } else if (isDelete) {
            SF.department.delete(department);
            viewMsg = "削除しました";

        } else {
            viewMsg = "削除できませんでした";
        }

        // ---------------------------------------------------------
        // JSONセット
        // ---------------------------------------------------------
        JSONObject json = new JSONObject();
        json.put("success", Boolean.TRUE);
        json.put("view_msg", viewMsg);

        // ---------------------------------------------------------
        // JSON出力
        // ---------------------------------------------------------
        Logic.writeResponseJson(json);

        return null;
    }

}
