package reddog_sample.form;

import org.seasar.struts.annotation.Arg;
import org.seasar.struts.annotation.Required;

public class LoginForm extends AbstractBaseForm {

    @Required(arg0 = @Arg(key="ログインID", resource=false))
    public String loginId;

    @Required(arg0 = @Arg(key="パスワード", resource=false))
    public String password;

    public String url;
}
