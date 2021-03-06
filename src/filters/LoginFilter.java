package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    /**
     * Default constructor.
     */
    public LoginFilter() {
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String context_path = ((HttpServletRequest)request).getContextPath();
        String servlet_path = ((HttpServletRequest)request).getServletPath();

        if(!servlet_path.matches("/css.*")) {       // CSSフォルダ内は認証処理から除外する
            HttpSession session = ((HttpServletRequest)request).getSession();

            // セッションスコープに保存された従業員（ログインユーザ）情報を取得
            User u = (User)session.getAttribute("login_user");


            // 投稿一覧とアカウント作成は特に制限しない
            if (!servlet_path.equals("/posts/index") && !servlet_path.matches("/homes/*") && !servlet_path.equals("/users/new") && !servlet_path.equals("/users/create")){
                if (servlet_path.equals("/login")){
                    // ログインしているのにログイン画面を表示させようとした場合は
                    // システムのトップページにリダイレクト
                    if (u != null) {
                        ((HttpServletResponse)response).sendRedirect(context_path + "/");
                        return;
                    }
                } else{
                    // ログアウトしている状態であれば
                    // トップ画面にリダイレクト
                    if(u == null) {
                        ((HttpServletResponse)response).sendRedirect(context_path + "/login");
                        return;
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    }
}