package reddog_sample.action.user;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.form.user.InputForm;
import reddog_sample.ignore.entity.RdUser;
import reddog_sample.service.SF;
import reddog_sample.util.annotation.AjaxCheck;
import reddog_sample.util.helper.Logic;

// ユーザ > 削除実行

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
        RdUser rdUser = SF.rdUser.getOne(inputForm.ajaxEditorKeyId);
        if (rdUser == null) {
            //return forwardBerror(inputForm, "ユーザ情報が存在しません");
            logger.warn("ユーザ情報が存在しません");
            isDelete = false;
        }

        //TODO 依存チェック

        // ---------------------------------------------------------
        // 削除実行
        // ---------------------------------------------------------
        String viewMsg = "";

        if (isDelete) {
            SF.rdUser.delete(rdUser);
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
