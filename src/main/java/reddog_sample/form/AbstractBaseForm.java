package reddog_sample.form;

import javax.servlet.http.HttpServletRequest;

import org.seasar.framework.container.SingletonS2Container;

public class AbstractBaseForm {

    /**
     * エラーメッセージ
     */
    public String errorMsg;

    /**
     * dtAjaxEditorで使用するキー
     */
    public String ajaxEditorKeyId;

    /**
     * dtAjaxEditorを閉じさせるフラグ
     */
    public boolean closeAjaxEditor = false;

    /**
     * リクエスト
     */
    public HttpServletRequest request =
            SingletonS2Container.getComponent(HttpServletRequest.class);
}
