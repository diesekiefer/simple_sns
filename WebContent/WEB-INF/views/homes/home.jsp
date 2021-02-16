<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>

        <c:if test="${errors != null}">
            <div id="flush_error">
            入力内容にエラーがあります。<br />
            <c:forEach var="error" items="${errors}">
                ・<c:out value="${error}" /><br />
            </c:forEach>
            </div>
        </c:if>

        <h2><c:out value="${user.username}" />さんのページ</h2>

        <c:if test="${sessionScope.login_user != null}">
            <c:if test="${is_followed > 0}">
                <h2><c:out value="${user.username}" />さんはあなたをフォローしています</h2>
            </c:if>
            <c:choose>
                <c:when test="${is_follow == 0}">
                    <form method="POST" action="<c:url value='/follows/add?user_id=${user.id}' />">
                        <input type="hidden" name="_token" value="${_token}" />
                        <button type="submit">フレンド追加</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form method="POST" action="<c:url value='/follows/remove?user_id=${user.id}' />">
                        <input type="hidden" name="_token" value="${_token}" />
                        <button type="submit">フレンド解除</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </c:if>
        <div>
            <span>フォロー中: <c:out value="${followee_count}" /></span>
            <span>フォロワー: <c:out value="${follower_count}" /></span>
        </div>
        <table id="post_list">
            <tbody>
                <tr>
                    <th>投稿内容</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="post" items="${posts}" varStatus="status">
                    <tr class="row${status.count % 2}">
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