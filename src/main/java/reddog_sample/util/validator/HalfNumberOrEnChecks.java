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
import reddog_sample.util.validator.annotation.HalfNumberOrEn;
import reddog_sample.util.validator.interfaces.JsValidChecks;

// 半角英数字チェック
public class HalfNumberOrEnChecks extends S2FieldChecks implements JsValidChecks {

    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger(HalfNumberOrEnChecks.class);

    public static boolean validateHalfNumberOrEn(Object bean,
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

        // 半角英数字でない場合はエラー
        if (CheckError.halfNumberOrEn(strValue)) {
            addError(errors, field, validator, validatorAction, request);
            return false;
        }

        return true;
    }

    @Override
    public String getJsErrMsg(Annotation annotation, String fieldName) {
        HalfNumberOrEn v = (HalfNumberOrEn)annotation;

        String label = MessageResourcesUtil.getMessage("labels."+ fieldName);
        String errMsg = MessageResourcesUtil.getMessage(v.msg().key(), label);

        return errMsg;
    }

    @Override
    public String getJsJudgement() {
        return "value.length != 0 && !value.match(/^[0-9a-zA-Z]*$/)";
    }
}
