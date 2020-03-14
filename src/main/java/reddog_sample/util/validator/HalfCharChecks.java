package reddog_sample.util.validator;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;
import org.seasar.struts.util.MessageResourcesUtil;
import org.seasar.struts.validator.S2FieldChecks;

import reddog_sample.util.helper.CheckError;
import reddog_sample.util.validator.annotation.HalfChar;
import reddog_sample.util.validator.interfaces.JsValidChecks;

// 半角文字チェック
public class HalfCharChecks extends S2FieldChecks implements JsValidChecks {

    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger(HalfCharChecks.class);

    public static boolean validateHalfChar(Object bean,
        ValidatorAction validatorAction, Field field,
        ActionMessages errors, Validator validator,
        HttpServletRequest request) {

        Object value = null;

        try {
            // アクションフォームから値を取得
            value = PropertyUtils.getProperty(bean, field.getProperty());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        // nullだったらエラー
        if (value == null) {
            addError(errors, field, validator, validatorAction, request);
            return false;
        }

        // 文字列変換
        String strValue = (String) value;

        // 半角文字でない場合はエラー
        if (CheckError.halfChar(strValue)) {
            addError(errors, field, validator, validatorAction, request);
            return false;
        }

        return true;
    }

    @Override
    public String getJsErrMsg(Annotation annotation, String fieldName) {
        HalfChar v = (HalfChar)annotation;

        String label = MessageResourcesUtil.getMessage("labels."+ fieldName);
        String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label);

        return errMsg;
    }

    @Override
    public String getJsJudgement() {
        return "value.length != 0 && !value.match(/^[\uFF65-\uFF9F0-9a-zA-Z \\\\(\\\\)\\\\.\\\\/\\\\-]+$/)";
    }
}
