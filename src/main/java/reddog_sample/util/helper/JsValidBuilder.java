package reddog_sample.util.helper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.seasar.struts.annotation.ByteType;
import org.seasar.struts.annotation.CreditCardType;
import org.seasar.struts.annotation.DateType;
import org.seasar.struts.annotation.DoubleRange;
import org.seasar.struts.annotation.DoubleType;
import org.seasar.struts.annotation.EmailType;
import org.seasar.struts.annotation.FloatRange;
import org.seasar.struts.annotation.FloatType;
import org.seasar.struts.annotation.IntRange;
import org.seasar.struts.annotation.IntegerType;
import org.seasar.struts.annotation.LongRange;
import org.seasar.struts.annotation.LongType;
import org.seasar.struts.annotation.Mask;
import org.seasar.struts.annotation.Maxbytelength;
import org.seasar.struts.annotation.Maxlength;
import org.seasar.struts.annotation.Minbytelength;
import org.seasar.struts.annotation.Minlength;
import org.seasar.struts.annotation.Required;
import org.seasar.struts.annotation.ShortType;
import org.seasar.struts.annotation.UrlType;
import org.seasar.struts.util.MessageResourcesUtil;

import reddog_sample.util.validator.interfaces.JsValidChecks;


/**
 * JsValidBuilderヘルパークラス
 *   FormクラスのアノテーションからJavaScriptの入力チェックを作成する
 */
public class JsValidBuilder {

    public static String RD_FORM_NAME = "rdForm";

    private List<Map<String, String>> jsDefList = new LinkedList<Map<String, String>>();
    private String fieldName;
    private String jsSelector;
    private String jsEvent;
    private String jsValue;
    private String jsSetTimeout;

    /**
     * コンストラクタ
     * アノテーション振り分け処理を行っている
     *
     * @param request
     * @param property      プロパティ名(フィールド名として使用する)
     * @param jsSelector    入力チェック対象要素
     * @param jsEvent       入力チェック起動イベント
     * @param jsValue       入力チェック対象
     * @param jsSetTimeout  入力チェックスクリプト読み込みを遅らせる場合に使用する。(msで指定)、、基本的には0指定でOK
     * @throws Exception
     */
    public JsValidBuilder(
            HttpServletRequest request,
            String property,
            String jsSelector,
            String jsEvent,
            String jsValue,
            String jsSetTimeout) throws Exception {

        this.fieldName    = property;
        this.jsSelector   = jsSelector;
        this.jsEvent      = jsEvent;
        this.jsValue      = jsValue;
        this.jsSetTimeout = jsSetTimeout;

        Class<?> c = this.getRdForm(request);
        Field f = c.getField(this.fieldName);

        for (Annotation a : f.getDeclaredAnnotations()) {

            // ----------------------------------
            // @Mask
            // ----------------------------------
            if (a instanceof Mask) {
                Mask v = (Mask) a;

                String mask = v.mask();
                mask = mask.replace("\\", "\\\\");

                String label = MessageResourcesUtil.getMessage("labels."+ this.fieldName);
                String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label);

                Map<String, String> m = new TreeMap<String, String>();
                m.put("fieldName", this.fieldName);
                m.put("errMsg", errMsg);
                m.put("judgement",
                        "value.length != 0"
                            + " && !value.match(/"+ mask+ "/)"
                );

                this.jsDefList.add(m);
            }
            // ----------------------------------
            // @Required
            // ----------------------------------
            else if (a instanceof Required) {
                Required v = (Required) a;

                String label = MessageResourcesUtil.getMessage("labels."+ this.fieldName);
                String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label);

                Map<String, String> m = new TreeMap<String, String>();
                m.put("fieldName", this.fieldName);
                m.put("errMsg", errMsg);
                m.put("judgement", "value.length == 0");

                this.jsDefList.add(m);
            }
            // ----------------------------------
            // @Maxlength
            // ----------------------------------
            else if (a instanceof Maxlength) {
                Maxlength v = (Maxlength) a;

                int max = v.maxlength();

                String label = MessageResourcesUtil.getMessage("labels."+ this.fieldName);
                String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label, max);

                Map<String, String> m = new TreeMap<String, String>();
                m.put("fieldName", this.fieldName);
                m.put("errMsg", errMsg);
                m.put("judgement", "value.length > "+ max);

                this.jsDefList.add(m);
            }
            // ----------------------------------
            // @Minlength
            // ----------------------------------
            else if (a instanceof Minlength) {
                Minlength v = (Minlength) a;

                int min = v.minlength();

                String label = MessageResourcesUtil.getMessage("labels."+ this.fieldName);
                String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label, min);

                Map<String, String> m = new TreeMap<String, String>();
                m.put("fieldName", this.fieldName);
                m.put("errMsg", errMsg);
                m.put("judgement", "value.length != 0 && value.length < "+ min);

                this.jsDefList.add(m);
            }
            // ----------------------------------
            // @Maxbytelength
            // ----------------------------------
            else if (a instanceof Maxbytelength) {
                Maxbytelength v = (Maxbytelength) a;

                int max = v.maxbytelength();

                String label = MessageResourcesUtil.getMessage("labels."+ this.fieldName);
                String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label, max);

                Map<String, String> m = new TreeMap<String, String>();
                m.put("fieldName", this.fieldName);
                m.put("errMsg", errMsg);
                // byte2()は、jquery.reddog.init.jsで定義している
                m.put("judgement", "value.bytes2() > "+ max);

                this.jsDefList.add(m);
            }
            // ----------------------------------
            // @Minbytelength
            // ----------------------------------
            else if (a instanceof Minbytelength) {
                Minbytelength v = (Minbytelength) a;

                int min = v.minbytelength();

                String label = MessageResourcesUtil.getMessage("labels."+ this.fieldName);
                String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label, min);

                Map<String, String> m = new TreeMap<String, String>();
                m.put("fieldName", this.fieldName);
                m.put("errMsg", errMsg);
                // byte2()は、jquery.reddog.init.jsで定義している
                m.put("judgement", "value.length != 0 && value.bytes2() < "+ min);

                this.jsDefList.add(m);
            }
            // ----------------------------------
            // @IntRange
            // ----------------------------------
            else if (a instanceof IntRange) {
                IntRange v = (IntRange) a;

                int min = v.min();
                int max = v.max();

                String label = MessageResourcesUtil.getMessage("labels."+ this.fieldName);
                String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label, min, max);

                Map<String, String> m = new TreeMap<String, String>();
                m.put("fieldName", this.fieldName);
                m.put("errMsg", errMsg);
                m.put("judgement",
                        "value.length != 0"
                            + " && (!value.match(/^-?[0-9]*$/) "
                                + " || parseInt(value, 10) < parseInt("+ min+ ", 10)"
                                + " || parseInt(value, 10) > parseInt("+ max+ ", 10)"
                            + ")"
                );

                this.jsDefList.add(m);
            }
            // ----------------------------------
            // @LongRange
            // ----------------------------------
            else if (a instanceof LongRange) {
                LongRange v = (LongRange) a;

                long min = v.min();
                long max = v.max();

                String label = MessageResourcesUtil.getMessage("labels."+ this.fieldName);
                String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label, min, max);

                Map<String, String> m = new TreeMap<String, String>();
                m.put("fieldName", this.fieldName);
                m.put("errMsg", errMsg);
                m.put("judgement",
                        "value.length != 0"
                            + " && (!value.match(/^-?[0-9]*$/) "
                                + " || parseInt(value, 10) < parseInt("+ min+ ", 10)"
                                + " || parseInt(value, 10) > parseInt("+ max+ ", 10)"
                            + ")"
                );

                this.jsDefList.add(m);
            }
            // ----------------------------------
            // @FloatRange
            // ----------------------------------
            else if (a instanceof FloatRange) {
                FloatRange v = (FloatRange) a;

                String min = v.min();
                String max = v.max();

                String label = MessageResourcesUtil.getMessage("labels."+ this.fieldName);
                String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label, min, max);

                Map<String, String> m = new TreeMap<String, String>();
                m.put("fieldName", this.fieldName);
                m.put("errMsg", errMsg);
                m.put("judgement",
                        "value.length != 0"
                            + " && (!value.match(/^-?[0-9]+(\\\\.[0-9]+)?$/) "
                                + " || parseFloat(value, 10) < parseFloat("+ min+ ", 10)"
                                + " || parseFloat(value, 10) > parseFloat("+ max+ ", 10)"
                            + ")"
                );

                this.jsDefList.add(m);
            }
            // ----------------------------------
            // @DoubleRange
            // ----------------------------------
            else if (a instanceof DoubleRange) {
                DoubleRange v = (DoubleRange) a;

                String min = v.min();
                String max = v.max();

                String label = MessageResourcesUtil.getMessage("labels."+ this.fieldName);
                String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label, min, max);

                Map<String, String> m = new TreeMap<String, String>();
                m.put("fieldName", this.fieldName);
                m.put("errMsg", errMsg);
                m.put("judgement",
                        "value.length != 0"
                            + " && (!value.match(/^-?[0-9]+(\\\\.[0-9]+)?$/) "
                                + " || parseFloat(value, 10) < parseFloat("+ min+ ", 10)"
                                + " || parseFloat(value, 10) > parseFloat("+ max+ ", 10)"
                            + ")"
                );

                this.jsDefList.add(m);
            }
            // ----------------------------------
            // @ByteType
            // ----------------------------------
            else if (a instanceof ByteType) {
                ByteType v = (ByteType) a;

                String label = MessageResourcesUtil.getMessage("labels."+ this.fieldName);
                String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label);

                Map<String, String> m = new TreeMap<String, String>();
                m.put("fieldName", this.fieldName);
                m.put("errMsg", errMsg);
                // 全角数字のサイズはチェックしない(サーバ側で行う)
                m.put("judgement",
                        "value.length != 0"
                            + " && (!value.match(/^-?[0-9０-９]*$/) "
                                + " || parseInt(value, 10) < parseInt("+ Byte.MIN_VALUE+ ", 10)"
                                + " || parseInt(value, 10) > parseInt("+ Byte.MAX_VALUE+ ", 10)"
                            + ")"
                );

                this.jsDefList.add(m);
            }
            // ----------------------------------
            // @ShortType
            // ----------------------------------
            else if (a instanceof ShortType) {
                ShortType v = (ShortType) a;

                String label = MessageResourcesUtil.getMessage("labels."+ this.fieldName);
                String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label);

                Map<String, String> m = new TreeMap<String, String>();
                m.put("fieldName", this.fieldName);
                m.put("errMsg", errMsg);
                // 全角数字のサイズはチェックしない(サーバ側で行う)
                m.put("judgement",
                        "value.length != 0"
                            + " && (!value.match(/^-?[0-9０-９]*$/) "
                                + " || parseInt(value, 10) < parseInt("+ Byte.MIN_VALUE+ ", 10)"
                                + " || parseInt(value, 10) > parseInt("+ Byte.MAX_VALUE+ ", 10)"
                            + ")"
                );

                this.jsDefList.add(m);
            }
            // ----------------------------------
            // @IntegerType
            // ----------------------------------
            else if (a instanceof IntegerType) {
                IntegerType v = (IntegerType) a;

                String label = MessageResourcesUtil.getMessage("labels."+ this.fieldName);
                String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label);

                Map<String, String> m = new TreeMap<String, String>();
                m.put("fieldName", this.fieldName);
                m.put("errMsg", errMsg);
                // 全角数字のサイズはチェックしない(サーバ側で行う)
                m.put("judgement",
                        "value.length != 0"
                            + " && (!value.match(/^-?[0-9０-９]*$/) "
                                + " || parseInt(value, 10) < parseInt("+ Byte.MIN_VALUE+ ", 10)"
                                + " || parseInt(value, 10) > parseInt("+ Byte.MAX_VALUE+ ", 10)"
                            + ")"
                );

                this.jsDefList.add(m);
            }
            // ----------------------------------
            // @LongType
            // ----------------------------------
            else if (a instanceof LongType) {
                LongType v = (LongType) a;

                String label = MessageResourcesUtil.getMessage("labels."+ this.fieldName);
                String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label);

                Map<String, String> m = new TreeMap<String, String>();
                m.put("fieldName", this.fieldName);
                m.put("errMsg", errMsg);
                // サイズはチェックしない(サーバ側で行う)
                //  本来Long型は「-9223372036854775808 ～ 9223372036854775807」であるが、
                //  javascriptでは「-9007199254740992 ～ 9007199254740992」の間のチェックしかできないため
                m.put("judgement",
                        "value.length != 0"
                            + " && !value.match(/^-?[0-9０-９]*$/)");

                this.jsDefList.add(m);
            }
            // ----------------------------------
            // @FloatType
            // ----------------------------------
            else if (a instanceof FloatType) {
                FloatType v = (FloatType) a;

                String label = MessageResourcesUtil.getMessage("labels."+ this.fieldName);
                String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label);

                Map<String, String> m = new TreeMap<String, String>();
                m.put("fieldName", this.fieldName);
                m.put("errMsg", errMsg);
                // サイズはチェックしない(サーバ側で行う)
                m.put("judgement",
                        "value.length != 0"
                            + " && !value.match(/^-?[0-9]+(\\\\.[0-9]+)?$/)");

                this.jsDefList.add(m);
            }
            // ----------------------------------
            // @DoubleType
            // ----------------------------------
            else if (a instanceof DoubleType) {
                DoubleType v = (DoubleType) a;

                String label = MessageResourcesUtil.getMessage("labels."+ this.fieldName);
                String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label);

                Map<String, String> m = new TreeMap<String, String>();
                m.put("fieldName", this.fieldName);
                m.put("errMsg", errMsg);
                // サイズはチェックしない(サーバ側で行う)
                m.put("judgement",
                        "value.length != 0"
                            + " && !value.match(/^-?[0-9]+(\\\\.[0-9]+)?$/)");

                this.jsDefList.add(m);
            }
            // ----------------------------------
            // @EmailType
            // ----------------------------------
            else if (a instanceof EmailType) {
                EmailType v = (EmailType) a;

                String label = MessageResourcesUtil.getMessage("labels."+ this.fieldName);
                String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label);

                Map<String, String> m = new TreeMap<String, String>();
                m.put("fieldName", this.fieldName);
                m.put("errMsg", errMsg);
                // 正規表現は次のサイトから引用
                // http://teeda.seasar.org/ja/extension/reverse/validation.html#文字列がメールアドレスかチェックするには
                m.put("judgement",
                        "value.length != 0"
                            + " && !value.match(/^[\\\\x00-\\\\x7F]+@(([-a-z0-9]+)\\\\.([-a-z0-9]+\\\\.)*[a-z]+|\\\\[\\\\d{1,3}\\\\.\\\\d{1,3}\\\\.\\\\d{1,3}\\\\.\\\\d{1,3}\\\\])$/)");

                this.jsDefList.add(m);
            }
            // ----------------------------------
            // @UrlType
            // ----------------------------------
            else if (a instanceof UrlType) {
                UrlType v = (UrlType) a;

                String label = MessageResourcesUtil.getMessage("labels."+ this.fieldName);
                String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label);

                Map<String, String> m = new TreeMap<String, String>();
                m.put("fieldName", this.fieldName);
                m.put("errMsg", errMsg);
                // 正規表現は次のサイトから引用
                // http://teeda.seasar.org/ja/extension/reverse/validation.html#文字列がURLかチェックするには
                m.put("judgement",
                        "value.length != 0"
                            + " && !value.match(/^https?:\\\\/\\\\/[-_.!~*\\'()a-zA-Z0-9;/?:@&=+$,%#]+\\\\.[a-z]+[-_.!~*\\'()a-zA-Z0-9;/?:@&=+$,%#]+$/)");

                this.jsDefList.add(m);
            }
            // ----------------------------------
            // 未実装の標準チェックは無視する
            //   @CreditCardType → 使用する場面が来ないと思う
            //   @DateType → yyyy/MM/ddを指定しても「2000/01/01, 20000/1/01, 200000/1/1」が通ってしまい微妙なので
            // ----------------------------------
            else if ((a instanceof CreditCardType) ||
                     (a instanceof DateType)) {
                // 何もしない
            }
            // ----------------------------------
            // 標準チェック以外の独自チェックアノテーション
            //   動的に生成する
            // ----------------------------------
            else {
                // アノテーション名称を取得
                String anoName = a.annotationType().getSimpleName();

                //FIXME validator-rules.xmlからCheckクラスを取得したい
                // Checkクラスをロード(「アノテーション名 + Check」のクラスを探す)
                Class<?> clazz = Class.forName("reddog_sample.util.validator."+ anoName+ "Checks");
                Object obj = clazz.newInstance();

                if (obj instanceof JsValidChecks) {
                    JsValidChecks checks = (JsValidChecks) obj;

                    Map<String, String> m = new TreeMap<String, String>();
                    m.put("fieldName", this.fieldName);
                    m.put("errMsg", checks.getJsErrMsg(a, this.fieldName));
                    m.put("judgement", checks.getJsJudgement());
                    this.jsDefList.add(m);
                }
            }
        }
    }

    /**
     * javascript処理を出力する
     */
    @Override
    public String toString() {

        StringBuilder b = new StringBuilder();

        b.append("<script type=\"text/javascript\">\n");
        b.append("$(function() { \n");
        b.append("    setTimeout(function() { \n");

        b.append("        $('"+ this.jsSelector+ "')."+ this.jsEvent+ "(function() { \n");
        b.append("            var valid = true; \n");

        for (Map<String, String> m : this.jsDefList) {
            b.append( map2js(m) );
        }

        b.append("        }); \n");

        b.append("    }, "+ this.jsSetTimeout+ "); \n");
        b.append("}); \n");
        b.append("</script>\n");

        return b.toString();
    }

    /**
     * アノテーション毎のmap情報からjs処理を作成する
     *
     * @param map
     * @return
     */
    private String map2js(Map<String, String> map) {

        if (map.isEmpty()) {
            return "";
        }

        // 「reddogValid」のjavascript関数はwebapp/asset/js/plugin/jquery.reddog.init.jsにある
        String jsTemp =
             "            if (valid) { \n"
            +"                valid = $(this).reddogValid({ \n"
            +"                    value: "+ this.jsValue+ ", \n"
            +"                    fieldName: '%s', \n"
            +"                    errMsg: '%s', \n"
            +"                    judgement: '%s' \n"
            +"                }); \n"
            +"            } \n"
        ;

        return String.format(
                    jsTemp,
                    map.get("fieldName"),
                    map.get("errMsg"),
                    map.get("judgement"));
    }

    /**
     * requestに「rdForm」の名称でObjectをセットする
     *
     * @param request
     * @param formName フォームの名称
     * @throws Exception
     */
    public static void setRdForm(HttpServletRequest request, String formName) throws Exception {
        request.setAttribute(JsValidBuilder.RD_FORM_NAME, request.getAttribute(formName));
    }

    /**
     * requestから「rdForm」の名称のFormクラスを取得する
     *
     * @param request
     * @param formName フォームの名称
     * @throws Exception
     */
    private Class<?> getRdForm(HttpServletRequest request) throws Exception {
        Object obj = request.getAttribute(RD_FORM_NAME);

        if (obj == null) {
            throw new Exception(RD_FORM_NAME+ "の値がありません");
        }

        return obj.getClass();
    }
}
