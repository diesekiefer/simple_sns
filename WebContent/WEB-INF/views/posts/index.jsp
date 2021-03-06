<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>投稿一覧</h2>
        <table id="post_list">
            <tbody>
                <tr>
                    <th>投稿ユーザ</th>
                    <th>投稿内容</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="post" items="${posts}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td>
                            <a href="<c:url value='/homes/${post.user.login_id}' />"><c:out value="${post.user.username}" /></a>
                        </td>
                        <td><c:out value="${post.content}" /></td>
                        <td>
                            <a href="<c:url value='/posts/show?id=${post.id}' />">詳細を表示</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${posts_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((posts_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/posts/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <c:choose>
            <c:when test="${sessionScope.login_user != null}">
                <p><a href="<c:url value='/posts/new' />">新規投稿</a></p>
            </c:when>
            <c:otherwise>
                <p><a href="<c:url value='/login' />">ログインして投稿する</a></p>
            </c:otherwise>
        </c:choose>
    </c:param>
</c:import>