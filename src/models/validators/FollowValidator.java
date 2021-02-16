package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Follow;

public class FollowValidator {
    public static List<String> validate(Follow f) {
        List<String> errors = new ArrayList<String>();

        String userId_error = _validateUserId(f.getFrom_userId(), f.getTo_userId());
        if(!userId_error.equals("")) {
            errors.add(userId_error);
        }

        return errors;
    }

    private static String _validateUserId(Integer from_userId, Integer to_userId) {
        if (from_userId == to_userId){
            return "自分とはフレンドになれません";
        }

        return "";
    }
}