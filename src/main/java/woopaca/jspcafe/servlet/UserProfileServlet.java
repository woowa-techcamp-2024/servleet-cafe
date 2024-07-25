package woopaca.jspcafe.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import woopaca.jspcafe.service.UserService;
import woopaca.jspcafe.servlet.dto.UserProfile;

import java.io.IOException;

@WebServlet("/users/profile/*")
public class UserProfileServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        this.userService = (UserService) servletContext.getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String userId = pathInfo.substring(1);
        UserProfile profile = userService.getUserProfile(userId);
        request.setAttribute("profile", profile);
        request.getRequestDispatcher("/user/profile.jsp")
                .forward(request, response);
    }
}
