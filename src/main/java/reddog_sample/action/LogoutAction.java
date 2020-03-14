package reddog_sample.action;

import javax.annotation.Resource;

import org.seasar.framework.aop.annotation.InvalidateSession;
import org.seasar.struts.annotation.Execute;

import reddog_sample.ignore.dto.RdUserDto;

// ログアウト処理
public class LogoutAction extends AbstractBaseAction {

    /** 認証情報を格納するセッションスコープ */
    @Resource
    protected RdUserDto rdUserDto;

    @InvalidateSession
    @Execute(validator=false)
    public String logout() {
        // 認証情報をクリア
        rdUserDto.userId = null;

        // ログイン画面に遷移
        return "/login/?redirect=true";
    }
}
