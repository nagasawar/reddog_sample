package reddog_sample.action.employee;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.entity.Employee;
import reddog_sample.form.employee.InputForm;
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
        Employee employee = SF.employee.getOne(inputForm.ajaxEditorKeyId);
        if (employee == null) {
            logger.warn("社員情報が存在しません");
            isDelete = false;
        }

        //TODO 依存チェック 他の画面で使用しているときは削除できないようにする。

        // ---------------------------------------------------------
        // 削除実行
        // ---------------------------------------------------------
        String viewMsg = "";

        if (isDelete) {
            SF.employee.delete(employee);
            viewMsg = "削除しました";

//        } else if (isDependent) {
//            viewMsg = "使用中のため削除できません";

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
