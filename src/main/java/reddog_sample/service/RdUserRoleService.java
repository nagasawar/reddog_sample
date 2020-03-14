package reddog_sample.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.seasar.extension.jdbc.JdbcManager;

import reddog_sample.ignore.entity.RdUserRole;

public class RdUserRoleService {

    @Resource
    JdbcManager jdbcManager;

    /**
     * 主キーに紐づくデータを1件取得する
     *
     * @param id
     * @return
     */
    public RdUserRole getOne(String id) {

        RdUserRole role = jdbcManager.from(RdUserRole.class)
            .id(id)
            .getSingleResult();

        return role;
    }

    /**
     * 全件リストを取得する
     * @return
     */
    public List<RdUserRole> findAll() {

        List<RdUserRole> roles = jdbcManager.from(RdUserRole.class)
            .getResultList();

        return roles;
    }

    /**
     * ID,名称のmapを取得する
     * @return
     */
    public Map<String, String> getIdNameMap() {

        List<RdUserRole> roles = jdbcManager.from(RdUserRole.class)
            .getResultList();

        Map<String, String> map = new TreeMap<String, String>();

        for (RdUserRole r : roles) {
            map.put(r.userRoleId, r.userRoleName);
        }

        return map;
    }

    /**
     * 主キーに紐づくデータが存在するか確認する
     *
     * @param id
     * @return
     */
    public boolean exist(String id) {

        RdUserRole role = jdbcManager.from(RdUserRole.class)
            .id(id)
            .getSingleResult();

        return (role != null);
    }

    /**
     * 新規作成する
     * @param rdUserRole
     */
    public void insert(RdUserRole rdUserRole) {
        jdbcManager.insert(rdUserRole).execute();
    }

    /**
     * 更新する
     * @param rdUserRole
     */
    public void update(RdUserRole rdUserRole) {
        jdbcManager.update(rdUserRole).execute();
    }

    /**
     * 削除する
     * @param rdUserRole
     */
    public void delete(RdUserRole rdUserRole) {
        jdbcManager.delete(rdUserRole).execute();
    }
}
