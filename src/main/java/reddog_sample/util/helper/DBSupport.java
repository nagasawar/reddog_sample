package reddog_sample.util.helper;

import org.seasar.extension.jdbc.SqlLog;
import org.seasar.extension.jdbc.SqlLogRegistry;
import org.seasar.extension.jdbc.SqlLogRegistryLocator;

/**
 * DBSupportヘルパークラス<br>
 *   DB周りの処理をサポートする。<br>
 */
public class DBSupport {

    /**
     * 条件文字がない場合は、条件なしで処理を行わせるため「1=1」を返す、ある場合は先頭のANDを削除して返す。
     *
     * @param where 条件文字
     * @return
     */
    public static String whereReturn(String where) {

        if (where.length() == 0) {
            return "1=1";
        }
        else {
            return where.replaceFirst("^ AND ", " ");
        }
    }

    /**
     * Seasar2のjdbcManagerでSQL実行した後にこのメソッドを使用すると、
     * コンソールにSQL文を出力する。
     * <p>
     *
     * <code>
     * List<User> users = jdbcManager.from(User.class)
     *     .getResultList();
     *
     * DBSupport.debugSQL();
     * </code>
     */
    public static void debugSQL() {

        SqlLogRegistry registry = SqlLogRegistryLocator.getInstance();
        if (registry != null) {
            SqlLog sqlLog = registry.getLast();
            System.out.println(sqlLog.getCompleteSql());
        }
    }
}
