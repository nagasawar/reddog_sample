package reddog_sample.form.user;

import java.util.List;

import reddog_sample.form.AbstractBaseForm;
import reddog_sample.ignore.entity.RdUser;

// ユーザ > Formクラス

public class IndexForm extends AbstractBaseForm {

    // 検索条件
    public String cndLoginId;  // ログインID
    public String cndUserName; // ユーザ名

    // 検索結果 ユーザリスト
    public List<RdUser> rdUsers;
}
