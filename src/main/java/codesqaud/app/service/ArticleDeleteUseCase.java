package codesqaud.app.service;

import codesqaud.app.AuthenticationManager;
import codesqaud.app.dao.article.ArticleDao;
import codesqaud.app.dao.reply.ReplyDao;
import codesqaud.app.db.TransactionManager;
import codesqaud.app.exception.HttpException;
import codesqaud.app.model.Article;
import codesqaud.app.model.Reply;
import codesqaud.app.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class ArticleDeleteUseCase extends ArticleUseCase {
    private final DataSource dataSource;

    public ArticleDeleteUseCase(ArticleDao articleDao, ReplyDao replyDao, DataSource dataSource) {
        super(articleDao, replyDao);
        this.dataSource = dataSource;
    }

    public void handle(HttpServletRequest req, HttpServletResponse resp, Long id) throws IOException {
        TransactionManager.executeTransaction(dataSource, () -> deleteArticle(req, id));
        resp.sendRedirect("/");
    }

    private void deleteArticle(HttpServletRequest req, Long id) {
        Article article = articleDao.findByIdForUpdate(id)
                .orElseThrow(() -> new HttpException(SC_NOT_FOUND));

        List<Reply> replies = replyDao.findByArticleId(article.getId());

        authorizeArticle(req, article.getAuthorId());
        validateDelete(req, replies);

        articleDao.delete(article);
        replies.forEach(replyDao::delete);
    }

    private void validateDelete(HttpServletRequest req, List<Reply> replies) {
        boolean hasOthersReplies = false;
        for (Reply reply : replies) {
            if(!AuthenticationManager.isMe(req, reply.getAuthorId())) {
                hasOthersReplies = true;
                break;
            }
        }

        if (hasOthersReplies) {
            throw new HttpException(SC_FORBIDDEN, "다른 사람이 작성한 댓글이 있습니다.");
        }
    }
}
