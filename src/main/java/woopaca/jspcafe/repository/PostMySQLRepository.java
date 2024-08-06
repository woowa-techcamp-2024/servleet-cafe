package woopaca.jspcafe.repository;

import woopaca.jspcafe.database.JdbcTemplate;
import woopaca.jspcafe.model.Count;
import woopaca.jspcafe.model.Page;
import woopaca.jspcafe.model.Post;

import java.util.List;
import java.util.Optional;

public class PostMySQLRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    public PostMySQLRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Post post) {
        if (post.getId() != null) {
            update(post);
            return;
        }

        String sql = "INSERT INTO post (title, content, written_at, writer_id, status) VALUES (?, ?, ?, ?, ?)";
        long autoGeneratedKey = jdbcTemplate.updateAndReturnKey(sql,
                post.getTitle(), post.getContent(), post.getWrittenAt(), post.getWriterId(), post.getStatus().name());
        post.setId(autoGeneratedKey);
    }

    private void update(Post post) {
        String sql = "UPDATE post SET title = ?, content = ?, view_count = ?, status = ? WHERE id = ?";
        jdbcTemplate.update(sql, post.getTitle(), post.getContent(), post.getViewCount(), post.getStatus().name(), post.getId());
    }

    @Override
    public List<Post> findAll() {
        String sql = "SELECT * FROM post";
        return jdbcTemplate.queryForList(sql, Post.class);
    }

    @Override
    public Optional<Post> findById(Long id) {
        String sql = "SELECT * FROM post WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Post.class, id));
    }

    @Override
    public Page<Post> findToPage(int page, int limit) {
        String sql = """
                SELECT * 
                FROM post JOIN (
                        SELECT id 
                        FROM post 
                        WHERE status = 'PUBLISHED' 
                        ORDER BY written_at DESC 
                        LIMIT ?, ?
                ) AS p ON post.id = p.id 
                ORDER BY written_at DESC;
                """;
        List<Post> posts = jdbcTemplate.queryForList(sql, Post.class, (page - 1) * limit, limit);

        String countSql = "SELECT COUNT(*) AS count FROM post";
        Count totalCount = jdbcTemplate.queryForObject(countSql, Count.class);
        int totalPage = (int) Math.ceil((double) totalCount.count() / limit);
        return new Page<>(posts, totalPage, page, totalCount.count());
    }
}
