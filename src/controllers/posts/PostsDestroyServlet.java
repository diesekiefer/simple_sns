package controllers.posts;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Post;
import utils.DBUtil;

/**
 * Servlet implementation class PostsDestroyServlet
 */
@WebServlet("/posts/destroy")
public class PostsDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostsDestroyServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        System.out.println(request.getSession().getAttribute("post_id"));
        Post p = em.find(Post.class, (Integer)(request.getSession().getAttribute("post_id")));

        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", "削除が完了しました。");

        response.sendRedirect(request.getContextPath() + "/posts/index");
    }

}
