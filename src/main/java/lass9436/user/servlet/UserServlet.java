package lass9436.user.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lass9436.user.model.User;
import lass9436.user.model.UserRepository;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

	private UserRepository userRepository;

	@Override
	public void init() throws ServletException {
		// 서블릿 초기화 시 컨텍스트에서 UserRepository 인스턴스를 가져옴
		userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("users", userRepository.findAll());
		req.getRequestDispatcher("/WEB-INF/user/list.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 요청 파라미터 읽기
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");
		String name = req.getParameter("name");
		String email = req.getParameter("email");

		// User 객체 생성 및 데이터 설정
		User user = new User(userId, password, name, email);
		User save = userRepository.save(user);

		// 유저목록으로 리다이렉트
		resp.sendRedirect("/users");
	}
}

