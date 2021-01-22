package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.User;
import utils.DBUtil;

public class UserValidator {
    public static List<String> validate(User u, Boolean login_idDuplicateCheckFlag, Boolean passwordCheckFlag) {
        List<String> errors = new ArrayList<String>();

        String code_error = validateLogin_id(u.getLogin_id(), login_idDuplicateCheckFlag);
        if(!code_error.equals("")) {
            errors.add(code_error);
        }

        String name_error = validateUsername(u.getUsername());
        if(!name_error.equals("")) {
            errors.add(name_error);
        }

        String password_error = validatePassword(u.getPassword(), passwordCheckFlag);
        if(!password_error.equals("")) {
            errors.add(password_error);
        }

        return errors;
    }

    // 社員番号
    private static String validateLogin_id(String login_id, Boolean login_idDuplicateCheckFlag) {
        // 必須入力チェック
        if(login_id == null || login_id.equals("")) {
            return "ログインIDを入力してください。";
        }

        // すでに登録されている社員番号との重複チェック
        if(login_idDuplicateCheckFlag) {
            EntityManager em = DBUtil.createEntityManager();
            long users_count = (long)em.createNamedQuery("checkRegisteredLogin_id", Long.class)
                                           .setParameter("login_id", login_id)
                                             .getSingleResult();
            em.close();
            if(users_count > 0) {
                return "入力されたログインIDはすでに存在しています。";
            }
        }

        return "";
    }

    // 社員名の必須入力チェック
    private static String validateUsername(String username) {
        if(username == null || username.equals("")) {
            return "ユーザネームを入力してください。";
        }

        return "";
    }

    // パスワードの必須入力チェック
    private static String validatePassword(String password, Boolean passwordCheckFlag) {
        // パスワードを変更する場合のみ実行
        if(passwordCheckFlag && (password == null || password.equals(""))) {
            return "パスワードを入力してください。";
        }
        return "";
    }
}
