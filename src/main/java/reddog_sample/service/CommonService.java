package reddog_sample.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.seasar.extension.jdbc.JdbcManager;

import reddog_sample.entity.Common;
import reddog_sample.enums.CommonCategory;

public class CommonService {

    @Resource
    JdbcManager jdbcManager;

    // ---------------------------------------------------------

    /**
     * 性別リストを取得する
     */
    public List<Common> getGenderList() {
        return this.getList(CommonCategory.GENDER);
    }

    /**
     * 指定値の性別を1件取得する
     */
    public Common getGender(String value) {
        return this.getOne(CommonCategory.GENDER, value);
    }

    /**
     * 性別のID,名称mapを取得する
     * @return
     */
    public Map<String, String> getGenderIdNameMap() {
        return this.getIdNameMap( this.getGenderList() );
    }

    // ---------------------------------------------------------

    private List<Common> getList(CommonCategory category) {

        List<Common> list = jdbcManager.from(Common.class)
                .where("category = ?", category)
                .getResultList();

        return list;
    }

    private Common getOne(CommonCategory category, String value) {

        Common o = jdbcManager.from(Common.class)
            .where("category = ? AND value = ?", category, value)
            .getSingleResult();

        return o;
    }

    /**
     * ID,名称mapを取得する
     * @return
     */
    private Map<String, String> getIdNameMap(List<Common> list) {
        Map<String, String> map = new TreeMap<String, String>();

        for (Common s : list) {
            map.put(s.value, s.cname);
        }

        return map;
    }
}
