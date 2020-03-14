package reddog_sample.action.user;

import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.form.user.IndexForm;
import reddog_sample.ignore.dto.RdUserDto;
import reddog_sample.service.RdUserService;
import reddog_sample.service.SF;

// ユーザ > ユーザ検索

public class IndexAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected IndexForm indexForm;

    @Resource
    protected RdUserDto rdUserDto;

    // --------------------------------------------------------
    @Execute(validator = false)
    public String index() throws Exception {

        // ---------------------------------------------------------------
        // 条件セット
        // ---------------------------------------------------------------
        Map<String, String> conditions = new TreeMap<String, String>();
        conditions.put(RdUserService.CK_LOGINID,  indexForm.cndLoginId);
        conditions.put(RdUserService.CK_USERNAME, indexForm.cndUserName);

        // ログイン者本人は除く
        conditions.put(RdUserService.CK_NOT_ID, String.valueOf(rdUserDto.userId));

        // ---------------------------------------------------------------
        // データを取得してFormへセット
        // ---------------------------------------------------------------
        indexForm.rdUsers = SF.rdUser.search(conditions);

        return "index.jsp";
    }
}
