package controllers.homes;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Post;
import models.User;
import utils.DBUtil;

/**
 * Servlet implementation class HomesIndexServlet
 */
@WebServlet("/homes/*")
public class HomesIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomesIndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path_info = request.getPathInfo();
        String user_loginid = path_info.split("/", 0)[1];
        EntityManager em = DBUtil.createEntityManager();
        System.out.println("path" + path_info);
        System.out.println("user_loginid" + user_loginid);

        // ユーザidが存在するかどうかの確認
        long users_count = (long)em.createNamedQuery("checkRegisteredLogin_id", Long.class)
                .setParameter("login_id", user_loginid)
                .getSingleResult();
        em.close();
//        System.out.println("user count " + users_count);

        if (users_count == 0){
            // 404でも返す。
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Userオブジェクトを取得する
        em = DBUtil.createEntityManager();
        User u = (User)em.createNamedQuery("getUserbyLogin_id", User.class)
                .setParameter("login_id", user_loginid)
                .getSingleResult();

        List<Post> posts = em.createNamedQuery("getAllPostsbyUser", Post.class)
                .setParameter("login_id", user_loginid)
                .setMaxResults(15)
                .getResultList();

        em.close();

        request.setAttribute("user", u);
        request.setAttribute("posts", posts);
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/homes/home.jsp");
        rd.forward(request, response);
    }
}
