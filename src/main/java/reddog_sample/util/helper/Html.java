package reddog_sample.util.helper;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.IteratorUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.framework.container.SingletonS2Container;


/**
 * Htmlヘルパークラス
 *   jspの中で使用する便利関数。
 */
public class Html {

    /**
     * scriptタグを作成する
     *
     * @param request
     * @param filePath (コンテキストパス)/assett 配下のパスを指定する
     * @return
     */
    public static String scriptTagWriter(HttpServletRequest request, String filePath) {
        filePath = filePath.replaceAll("^/", "");
        return String.format("<script type=\"text/javascript\" src=\"%s/asset/%s\"></script>", request.getContextPath(), filePath);
    }

    /**
     * linkタグを作成する
     *
     * @param request
     * @param filePath (コンテキストパス)/asset 配下のパスを指定する
     * @return
     */
    public static String linkTagWriter(HttpServletRequest request, String filePath) {
        filePath = filePath.replaceAll("^/", "");
        return String.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"%s/asset/%s\" />", request.getContextPath(), filePath);
    }

    /**
     * エラーサマリを取得する
     *
     * @param key 指定項目キー
     * @return エラーあり / なし → "○件エラーがあります" / ""
     */
    public static String errorSummary() {

        HttpServletRequest req = SingletonS2Container.getComponent(HttpServletRequest.class);

        ActionMessages am = (ActionMessages) req.getAttribute("org.apache.struts.action.ERROR");
        if (am == null) {
            return "";
        }

        if (am.size() > 0) {
            return String.valueOf(am.size()) + "件エラーがあります";
        }

        return "";
    }

    /**
     * Twitter Bootstrapのエラークラス名を取得する
     * 指定keyがエラーなら、CSSクラス名「has-error」を返す。
     *
     * @param key 指定項目キー
     * @return エラーあり / なし → "has-error" / ""
     */
    @SuppressWarnings("unchecked")
    public static String bsErrCls(String key) {

        HttpServletRequest req = SingletonS2Container.getComponent(HttpServletRequest.class);

        ActionMessages am = (ActionMessages) req.getAttribute("org.apache.struts.action.ERROR");
        if (am == null) {
            return "";
        }

        Iterator<ActionMessage> iterator = am.get(key);
        int size = IteratorUtils.toList(iterator).size();

        if (size > 0) {
            return "has-error";
        }

        return "";
    }

    /**
     * Twitter Bootstrapのメニュー選択中のクラス名を取得する
     * 指定urlが現在のURLと同じなら、CSSクラス名「active」を返す。
     *
     * @param request
     * @param url 「ドメイン/コンテキスト/WEB-INF/view」を抜いて、jspまで指定したURLを設定する。
     *            例)
     *               http://localhost:8080/reddog_sample/user/
     *                  ↓
     *               /user/index.jsp
     *        また、複数の引数urlが指定されていた場合は、いずれかのurlにマッチしていた時にactiveを返す。
     *
     * @return 同じURLである / ではない → "active" / ""
     */
    public static String bsMenuActiveCls(String... url) {

        HttpServletRequest  req = SingletonS2Container.getComponent(HttpServletRequest.class);

        for (String u : url) {
            if (req.getServletPath().equals("/WEB-INF/view" + u)) {
                return "active";
            }
        }

        return "";
    }
}
