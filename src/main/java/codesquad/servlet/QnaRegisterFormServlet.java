package codesquad.servlet;

import codesquad.servlet.annotation.authentication.Authorized;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/qna/register-form")
public class QnaRegisterFormServlet extends HttpServlet {
    /**
     * 질문 등록 폼 요청
     */
    @Authorized
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/qna/form.jsp").forward(req, resp);
    }
}
