package controllers.follows;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Follow;
import models.User;
import utils.DBUtil;

/**
 * Servlet implementation class FollowsRemoveServlet
 */
@WebServlet("/follows/remove")
public class FollowsRemoveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsRemoveServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            // ログイン情報をセッションスコープから取得
            HttpSession session = ((HttpServletRequest)request).getSession();
            User from_u = (User)session.getAttribute("login_user");

            // To_user のLoginIdを取得したい
            User to_u = em.find(User.class, Integer.parseInt(request.getParameter("user_id")));

            Follow f = (Follow)em.createNamedQuery("getFollowbyUsers")
                    .setParameter("from_userId", from_u.getId())
                    .setParameter("to_userId", Integer.parseInt(request.getParameter("user_id")))
                    .getSingleResult();

            em.getTransaction().begin();
            em.remove(f);
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "削除が完了しました。");

            response.sendRedirect(request.getContextPath() + "/homes/" + to_u.getLogin_id());
        }
    }
}
