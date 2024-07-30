package codesquad.jspcafe.servlet;

import codesquad.jspcafe.common.ApplicationProperties;
import codesquad.jspcafe.common.database.JDBCConnectionManager;
import codesquad.jspcafe.domain.article.repository.ArticleJdbcRepository;
import codesquad.jspcafe.domain.article.repository.ArticleRepository;
import codesquad.jspcafe.domain.article.service.ArticleService;
import codesquad.jspcafe.domain.reply.repository.ReplyJdbcRepository;
import codesquad.jspcafe.domain.reply.repository.ReplyRepository;
import codesquad.jspcafe.domain.reply.service.ReplyService;
import codesquad.jspcafe.domain.user.repository.UserJdbcRepository;
import codesquad.jspcafe.domain.user.repository.UserRepository;
import codesquad.jspcafe.domain.user.service.UserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class DefaultServletContextListener implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(DefaultServletContextListener.class);

    private final ApplicationProperties applicationProperties = new ApplicationProperties();
    private final JDBCConnectionManager connectionManager = new JDBCConnectionManager(
        applicationProperties);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserRepository userRepository = new UserJdbcRepository(connectionManager);
        ArticleRepository articleRepository = new ArticleJdbcRepository(connectionManager);
        ReplyRepository replyRepository = new ReplyJdbcRepository(connectionManager);
        ServletContext sc = sce.getServletContext();
        sc.setAttribute("userService", new UserService(userRepository));
        sc.setAttribute("articleService",
            new ArticleService(articleRepository, replyRepository, userRepository));
        sc.setAttribute("replyService", new ReplyService(userRepository, replyRepository));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
        }
    }

}
