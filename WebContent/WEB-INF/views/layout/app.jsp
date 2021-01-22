<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <title>Simple SNS</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <div id="header_menu">
                    <h1><a href="<c:url value='/' />">Simple SNS</a></h1>&nbsp;&nbsp;&nbsp;
                    <a href="<c:url value='/posts/index' />">投稿一覧</a>&nbsp;
                </div>
                <c:choose>
                <c:when test="${sessionScope.login_user != null}">
                    <div id="user_name">
                        <c:out value="${sessionScope.login_user.username}" />&nbsp;さん&nbsp;&nbsp;&nbsp;
                        <a href="<c:url value='/logout' />">ログアウト</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div id="sign_in">
                        <a href="<c:url value='/users/new' />">アカウント作成</a>
                    </div>
                </c:otherwise>
                </c:choose>
            </div>
            <div id="content">
                ${param.content}
            </div>
            <div id="footer">
                by diesekiefer.
            </div>
        </div>
    </body>
</html>