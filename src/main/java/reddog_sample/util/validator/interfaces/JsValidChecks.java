package reddog_sample.util.validator.interfaces;

import java.lang.annotation.Annotation;

/**
 * Jsチェックに必要なインターフェース
 *
 * @author Administrator
 *
 */
public interface JsValidChecks {

    /**
     * Javascript入力チェックエラー時のメッセージを返す
     *
     * @param annotation
     * @param fieldName
     * @return
     */
    public String getJsErrMsg(Annotation annotation, String fieldName);

    /**
     * <pre>
     * Javascript入力チェックの判定を返す
     *
     * 実際のJavascript内で、「if (eval(【@arg】)) { ...」と記述されており、
     * 【@arg】部分が返す値に置換される。
     *
     * ※正規表現のエスケープ文字「\」を使用するとき注意が必要。
     *   javaと、javascriptの2重で「\」エスケープをする必要がある。
     *   例） \d → \\\\d
     * </pre>
     *
     * @return
     */
    public String getJsJudgement();
}
