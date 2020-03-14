package reddog_sample.action.userRole;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.form.userRole.InputForm;
import reddog_sample.ignore.entity.RdUserRole;
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
        RdUserRole rdUserRole = SF.rdUserRole.getOne(inputForm.ajaxEditorKeyId);
        if (rdUserRole == null) {
            logger.warn("ユーザ権限が存在しません");
            isDelete = false;
        }

        // ---------------------------------------------------------------
        // 依存チェック ユーザマスタで使用しているときは削除できないようにする。
        // ---------------------------------------------------------------
        boolean isUse = SF.rdUser.existByUserRoleId(inputForm.ajaxEditorKeyId);

        // ---------------------------------------------------------
        // 削除実行
        // ---------------------------------------------------------
        String viewMsg = "";

        if (isUse) {
            viewMsg = "使用中のため削除できません";

        } else if (isDelete) {
            SF.rdUserRole.delete(rdUserRole);
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
