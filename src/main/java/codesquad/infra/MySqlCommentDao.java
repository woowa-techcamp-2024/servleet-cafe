package codesquad.infra;

import codesquad.common.db.connection.ConnectionManager;
import codesquad.domain.comment.Comment;
import codesquad.domain.comment.CommentDao;
import codesquad.domain.comment.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCommentDao implements CommentDao {
    private ConnectionManager connectionManager;

    public MySqlCommentDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public List<Comment> findAllByArticleId(Long articleId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionManager.getConnection();
            String sql = "select * from comments where article_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, articleId);
            resultSet = preparedStatement.executeQuery();
            List<Comment> comments = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String writer = resultSet.getString("writer");
                String content = resultSet.getString("content");
                String status = resultSet.getString("status");
                comments.add(new Comment(id, articleId, writer, content, Status.of(status)));
            }
            return comments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionManager.close(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public void updateStatus(List<Comment> comments, Status status) {
        if (comments == null || comments.isEmpty()) {
            return;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionManager.getConnection();
            StringBuilder placeholders = new StringBuilder();
            for (int i = 0; i < comments.size(); i++) {
                placeholders.append("?");
                if (i < comments.size() - 1) {
                    placeholders.append(", ");
                }
            }
            String sql = "UPDATE comments SET status = ? WHERE id IN (" + placeholders + ")";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status.name());
            for (int i = 0; i < comments.size(); i++) {
                preparedStatement.setLong(i + 2, comments.get(i).getId());
            }
            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows != comments.size()) {
                throw new SQLException("Failed to update comments, ok: " + updatedRows + ", failed: " + (comments.size() - updatedRows));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionManager.close(connection, preparedStatement);
        }
    }
}
