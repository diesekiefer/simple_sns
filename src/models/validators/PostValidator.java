package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Post;
import utils.DBUtil;

public class PostValidator {
    public static List<String> validate(Post p, Boolean user_idDuplicateCheckFlag) {
        List<String> errors = new ArrayList<String>();

        String user_id_error = _validateUser_id(p.getUser_id(), user_idDuplicateCheckFlag);
        if(!user_id_error.equals("")) {
            errors.add(user_id_error);
        }

        String content_error = _validateContent(p.getContent());
        if(!content_error.equals("")) {
            errors.add(content_error);
        }

        return errors;
    }

    // 社員番号
    private static String _validateUser_id(String user_id, Boolean user_idDuplicateCheckFlag) {
        // 必須入力チェック
        if(user_id == null || user_id.equals("")) {
            return "ユーザIDを入力してください。";
        }

        // すでに登録されている社員番号との重複チェック
        if(user_idDuplicateCheckFlag) {
            EntityManager em = DBUtil.createEntityManager();
            long post_count = (long)em.createNamedQuery("checkRegisteredUser_id", Long.class)
                                           .setParameter("user_id", user_id)
                                             .getSingleResult();
            em.close();
            if(post_count > 0) {
                return "入力されたユーザIDの情報はすでに存在しています。";
            }
        }

        return "";
    }

    private static String _validateContent(String content) {
        if(content == null || content.equals("")) {
            return "内容を入力してください。";
            }

        return "";
    }
}