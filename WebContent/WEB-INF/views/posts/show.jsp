<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${post != null}">
                <h2>id : ${post.id} の投稿　詳細ページ</h2>

                <table>
                    <tbody>
                        <tr>
                            <th>user name</th>
                            <td><c:out value="${post.user.username}" /></td>
                        </tr>

                        <tr>
                            <th>内容</th>
                            <td>
                                <pre><c:out value="${post.content}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td>
                                <fmt:formatDate value="${post.created_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <p><a href="#" onclick="confirmDestroy();">この投稿を削除する</a></p>
                <form name="delete_form" method="POST" action="<c:url value='/posts/destroy' />">
                    <input type="hidden" name="_token" value="${_token}" />
                </form>
                <script>
                    function confirmDestroy() {
                        if(confirm("本当に削除してよろしいですか？")) {
                            document.delete_form.submit();
                        }
                    }
                </script>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value='/posts/index' />">一覧に戻る</a></p>
    </c:param>
</c:import>