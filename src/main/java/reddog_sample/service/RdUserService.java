package reddog_sample.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.framework.util.StringUtil;

import reddog_sample.ignore.entity.RdUser;
import reddog_sample.util.helper.DBSupport;

public class RdUserService {

    @Resource
    JdbcManager jdbcManager;

    /**
     * 主キーに紐づくデータを1件取得する
     *
     * @param id
     * @return
     */
    public RdUser getOne(String id) {

        RdUser rdUser = jdbcManager.from(RdUser.class)
            .id(id)
            .innerJoin("rdUserRole")
            .getSingleResult();

        return rdUser;
    }

    /**
     * ログインIDに紐づくユーザが存在するか確認する
     *
     * @param loginId
     * @return
     */
    public boolean existByLoginId(String loginId) {

        RdUser rdUser = jdbcManager.from(RdUser.class)
            .where("login_id = ?", loginId)
            .getSingleResult();

        return (rdUser != null);
    }

    /**
     * 以前登録していたログインID以外で、新しく指定したログインIDに紐づくユーザが存在するか確認する
     *
     * @param loginId
     * @param beforeLoginId
     * @return
     */
    public boolean existByNotBeforeLoginId(String loginId, String beforeLoginId) {

        RdUser rdUser = jdbcManager.from(RdUser.class)
            .where("login_id = ? AND login_id <> ?", loginId, beforeLoginId)
            .getSingleResult();

        return (rdUser != null);
    }

    /**
     * ユーザ権限IDに紐づくデータが存在するか確認する
     *
     * @param userRoleId
     * @return
     */
    public boolean existByUserRoleId(String userRoleId) {

        long count = jdbcManager.from(RdUser.class)
            .where("user_role_id = ?", userRoleId)
            .getCount();

        return (count > 0);
    }

    /**
     * ログインID、パスワードに紐づくユーザを1件取得する
     *
     * @param loginId
     * @param password
     * @return
     */
    public RdUser getUser(String loginId, String password) {

        RdUser rdUser = jdbcManager.from(RdUser.class)
            .where("login_id = ? AND password = ?",
                    loginId, password)
            .getSingleResult();

        return rdUser;
    }

    /**
     * ユーザIDに紐づくユーザ名を取得する
     *
     * @param userId
     * @return
     */
    public String getUserName(int userId) {

        RdUser rdUser = jdbcManager.from(RdUser.class)
            .where("user_id = ?", userId)
            .getSingleResult();

        return (rdUser == null)? "": rdUser.userName;
    }

    /**
     * ユーザIDに紐づくロールIDを取得する
     *
     * @param userId
     * @return
     */
    public int getUserRoleId(int userId) {

        RdUser rdUser = jdbcManager.from(RdUser.class)
            .where("user_id = ?", userId)
            .getSingleResult();

        return rdUser.userRoleId;
    }

    /**
     * 検索してユーザリストを取得する
     * @param conditions
     * @return
     */
    public List<RdUser> search(Map<String, String> conditions) {

        List<Object> params = new ArrayList<Object>();

        String where = _getWhere(conditions, params);

        List<RdUser> rdUsers = jdbcManager.from(RdUser.class)
            .where(where, params.toArray())
            .innerJoin("rdUserRole")
            .getResultList();

        return rdUsers;
    }

    /**
     * 新規作成する
     * @param rdUser
     */
    public void insert(RdUser rdUser) {
        jdbcManager.insert(rdUser).execute();
    }

    /**
     * 更新する
     * @param rdUser
     */
    public void update(RdUser rdUser) {
        jdbcManager.update(rdUser).execute();
    }

    /**
     * 削除する
     * @param rdUser
     */
    public void delete(RdUser rdUser) {
        jdbcManager.delete(rdUser).execute();
    }

    // 検索条件キー
    public static final String CK_LOGINID  = "loginId";  // ログインID
    public static final String CK_USERNAME = "userName"; // ユーザ名
    public static final String CK_NOT_ID   = "not_id";   // 指定ユーザID以外

    /**
     * 検索条件を取得する
     *
     * @param conditions
     * @param params
     * @return
     */
    private String _getWhere(Map<String, String> conditions, List<Object> params) {
        String where = "";
        String target;

        // ---------------------------------------------------------------
        // ログインID (前方一致)
        // ---------------------------------------------------------------
        target = conditions.get(CK_LOGINID);

        if (!StringUtil.isEmpty(target)) {
            where += " AND login_id LIKE ?";
            params.add(target + "%");
        }

        // ---------------------------------------------------------------
        // ユーザ名 (部分一致)
        // ---------------------------------------------------------------
        target = conditions.get(CK_USERNAME);

        if (!StringUtil.isEmpty(target)) {
            where += " AND user_name LIKE ?";
            params.add("%" + target + "%");
        }

        // ---------------------------------------------------------------
        // 指定ユーザID以外 (完全一致)
        // ---------------------------------------------------------------
        target = conditions.get(CK_NOT_ID);

        if (!StringUtil.isEmpty(target)) {
            where += " AND user_id <> ?";
            params.add(target);
        }

        return DBSupport.whereReturn(where);
    }
}
