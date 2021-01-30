<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>新規アカウント登録ページ</h2>
        <form method="POST" action="<c:url value='/createuser' />">
            <c:if test="${errors != null}">
                <div id="flush_error">
                    入力内容にエラーがあります。<br />
                    <c:forEach var="error" items="${errors}">
                        ・<c:out value="${error}" /><br />
                    </c:forEach>

                </div>
            </c:if>

            <label for="code">ログインID</label><br />
            <input type="text" name="login_id" value="${user.login_id}" />
            <br /><br />

            <label for="name">ユーザ名</label><br />
            <input type="text" name="username" value="${user.username}" />
            <br /><br />

            <label for="password">パスワード</label><br />
            <input type="password" name="password" />
            <br /><br />

            <input type="hidden" name="_token" value="${_token}" />
            <button type="submit">アカウント作成</button>
        </form>

        <p><a href="<c:url value='/' />">トップに戻る</a></p>
    </c:param>
</c:import>