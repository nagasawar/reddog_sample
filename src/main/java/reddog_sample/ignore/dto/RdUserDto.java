package reddog_sample.ignore.dto;

import java.io.Serializable;

import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;
import org.seasar.framework.util.ArrayUtil;

import reddog_sample.ignore.entity.RdPageAuth;
import reddog_sample.ignore.entity.RdUser;

/**
 * 認証情報を格納するためのDTO
 */
@Component(instance=InstanceType.SESSION)
public class RdUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer userId = null;

    /**
     * ログインチェック
     *
     * @return
     */
    public boolean isLogin() {
        return (userId != null);
    }

    /**
     * 引数のパスが許可されているかチェック
     *
     * @param path
     * @return
     */
    public boolean isAllowedRole(String path) {

        JdbcManager jdbcManager =
            SingletonS2Container.getComponent(JdbcManager.class);

        RdPageAuth pa = jdbcManager.from(RdPageAuth.class)
                .where("path = ?", path)
                .getSingleResult();

        if (pa == null) {
            return true;
        }

        RdUser user = jdbcManager.from(RdUser.class)
                .id(userId)
                .getSingleResult();

        String userRoleId = String.valueOf(user.userRoleId);
        String[] roleArray = pa.roles.split(",");

        if (!roleArray[0].equals("")
                && !ArrayUtil.contains(roleArray, userRoleId)) {
            return false;
        }

        return true;
    }

}
