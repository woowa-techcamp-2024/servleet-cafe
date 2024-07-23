package codesquad.jspcafe.domain.article.payload.response;

import codesquad.jspcafe.common.utils.DateTimeFormatExecutor;
import codesquad.jspcafe.domain.article.domain.Article;
import java.time.LocalDateTime;

public class ArticleCommonResponse {

    private final String title;
    private final String writer;
    private final String contents;
    private final String createdAt;

    private ArticleCommonResponse(String title, String writer, String contents,
        LocalDateTime createdAt) {
        this.title = title;
        this.writer = writer;
        this.contents = contents;
        this.createdAt = DateTimeFormatExecutor.execute(createdAt);
    }

    public static ArticleCommonResponse from(Article article) {
        return new ArticleCommonResponse(article.getTitle(), article.getWriter(),
            article.getContents(), article.getCreatedAt());
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
