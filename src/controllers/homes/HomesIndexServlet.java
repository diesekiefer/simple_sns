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
import javax.servlet.http.HttpSession;

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

        if (users_count == 0){
            // 404でも返す。
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // ログイン情報をセッションスコープから取得
        HttpSession session = ((HttpServletRequest)request).getSession();
        User login_user = (User)session.getAttribute("login_user");

        // Userオブジェクトを取得する
        em = DBUtil.createEntityManager();
        User u = (User)em.createNamedQuery("getUserbyLogin_id", User.class)
                .setParameter("login_id", user_loginid)
                .getSingleResult();
        int page = 1;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e) { }

        // Postオブジェクトのリストを取得する
        List<Post> posts = em.createNamedQuery("getAllPostsbyUser", Post.class)
                .setParameter("login_id", user_loginid)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        long posts_count = (long)em.createNamedQuery("getPostsCountbyUser", Long.class)
                .setParameter("login_id", user_loginid)
                .getSingleResult();

        // 表示するページのユーザが from に含まれるFollowオブジェクトの数を取得する
        long followee_count = em.createNamedQuery("getFollowsCountbyFrom_userId", Long.class)
                .setParameter("from_userId", u.getId())
                .getSingleResult();

        // 表示するページのユーザが to に含まれるFollowオブジェクトの数を取得する
        long follower_count = em.createNamedQuery("getFollowsCountbyTo_userId", Long.class)
                .setParameter("to_userId", u.getId())
                .getSingleResult();

        em.close();


        if (login_user != null){
            em = DBUtil.createEntityManager();

            // 表示するページのユーザを、ログインユーザがフォローしているかどうか
            long is_follow = em.createNamedQuery("getFollowsCountbyUsers", Long.class)
                    .setParameter("from_userId", login_user.getId())
                    .setParameter("to_userId", u.getId())
                    .getSingleResult();

            // ログインユーザが、表示するページのユーザをふぉろーしているかどうか
            long is_followed = em.createNamedQuery("getFollowsCountbyUsers", Long.class)
                    .setParameter("from_userId", u.getId())
                    .setParameter("to_userId", login_user.getId())
                    .getSingleResult();

            em.close();
            request.setAttribute("is_follow", is_follow);
            request.setAttribute("is_followed", is_followed);
        }

        request.setAttribute("_token", request.getSession().getId());
        request.setAttribute("user", u);
        request.setAttribute("posts", posts);
        request.setAttribute("posts_count", posts_count);
        request.setAttribute("page", page);
        request.setAttribute("followee_count", followee_count);
        request.setAttribute("follower_count", follower_count);

        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }
//        if(request.getSession().getAttribute("errors") != null) {
//            request.setAttribute("errors", request.getSession().getAttribute("errors"));
//            request.getSession().removeAttribute("errors");
//        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/homes/home.jsp");
        rd.forward(request, response);
    }
}
