package controllers.follows;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Follow;
import models.Post;
import models.User;
import models.validators.FollowValidator;
import utils.DBUtil;

/**
 * Servlet implementation class FollowAddServlet
 */
@WebServlet("/follows/add")
public class FollowsAddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsAddServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Follow f = new Follow();

            // ログイン情報をセッションスコープから取得
            HttpSession session = ((HttpServletRequest)request).getSession();
            User from_u = (User)session.getAttribute("login_user");
            f.setFrom_userId(from_u.getId());
            f.setTo_userId(Integer.parseInt(request.getParameter("user_id")));

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            f.setCreated_at(currentTime);

            // リダイレクトに使うのでTo_user のLoginIdを取得したい
            User to_u = em.find(User.class, Integer.parseInt(request.getParameter("user_id")));

            List<String> errors = FollowValidator.validate(f);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("user", from_u);
                em = DBUtil.createEntityManager();
                List<Post> posts = em.createNamedQuery("getAllPostsbyUser", Post.class)
                        .setParameter("login_id", to_u.getLogin_id())
                        .setMaxResults(15)
                        .getResultList();

                em.close();
                request.setAttribute("posts", posts);
                request.setAttribute("errors", errors);
                // response.sendRedirect(request.getContextPath() + "/homes/" + from_u.getLogin_id());
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/homes/home.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.persist(f);
                em.getTransaction().commit();
                request.getSession().setAttribute("flush", "登録が完了しました。");
                em.close();

                response.sendRedirect(request.getContextPath() + "/homes/" + to_u.getLogin_id());
            }
        }
    }
}
