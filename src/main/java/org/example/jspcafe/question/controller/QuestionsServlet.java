package org.example.jspcafe.question.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.jspcafe.question.repository.QuestionRepository;
import org.example.jspcafe.user.User;

import java.io.IOException;

import static org.example.jspcafe.common.RequestUtil.extractLongPathVariable;
import static org.example.jspcafe.common.RequestUtil.getUserFromSession;

@WebServlet(name = "QuestionServlet", value = "/questions/*")
public class QuestionsServlet extends HttpServlet {

    private QuestionRepository questionRepository;

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        this.questionRepository = (QuestionRepository) context.getAttribute("QuestionRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        User user = getUserFromSession(req);
        if(user == null) {
            res.sendRedirect("/login.jsp"); // 로그인 페이지로 리다이렉트
        }
        Long id = extractLongPathVariable(req);
        req.setAttribute("question", questionRepository.findById(id));
        req.getRequestDispatcher("/qna/show.jsp").forward(req, res);
    }


}
