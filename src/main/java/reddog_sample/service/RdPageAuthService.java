package reddog_sample.service;

import javax.annotation.Resource;

import org.seasar.extension.jdbc.JdbcManager;

import reddog_sample.ignore.entity.RdPageAuth;

/**
 * 画面権限制御テーブル「rd_page_auth」
 * @author Administrator
 */
public class RdPageAuthService {

    @Resource
    JdbcManager jdbcManager;

    /**
     * 主キーに紐づくデータを1件取得する
     *
     * @param id
     * @return
     */
    public RdPageAuth getOne(String id) {

        RdPageAuth pa = jdbcManager.from(RdPageAuth.class)
            .id(id)
            .getSingleResult();

        return pa;
    }

    /**
     * 引数のキーに紐づくデータを1件取得する
     *
     * @param path
     * @return
     */
    public RdPageAuth getByPath(String path) {

        RdPageAuth pa = jdbcManager.from(RdPageAuth.class)
            .where("path = ?", path)
            .getSingleResult();

        return pa;
    }
}
