package camp.woowa.jspcafe.repository;

import camp.woowa.jspcafe.db.MySQLDatabaseManager;
import camp.woowa.jspcafe.model.Question;
import camp.woowa.jspcafe.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MySQLQuestionRepositoryTest {
    static MySQLDatabaseManager databaseManager;
    static QuestionRepository questionRepository;
    static UserRepository userRepository;
    static long userId;

    @BeforeAll
    static void setUpAll() {
        databaseManager = new MySQLDatabaseManager();
        questionRepository = new MySQLQuestionRepository(databaseManager);
        userRepository = new MySQLUserRepository(databaseManager);
        userId = userRepository.save(new User("userId", "password", "name", "email"));
    }

    @BeforeEach
    void setUp() {
        questionRepository.deleteAll();
    }

    @AfterAll
    static void tearDownAll() {
        userRepository.deleteAll();
    }

    @Test
    void testSave() {
        // given
        String title = "title";
        String content = "content";
        String writer = "1234";
        Long expectedId = 1L;
        Question save = new Question(title, content, writer, userId);
        // when
        Long id = questionRepository.save(save);


        // then
        assertEquals(content, questionRepository.findById(id).getContent());
    }

    @Test
    void testFindAll() {
        // given
        String title = "title";
        String content = "content";
        String writer = "1234";
        Question save = new Question(title, content, writer, userId);
        questionRepository.save(save);

        int expectedSize = 1;

        // when
        List<Question> questions = questionRepository.findAll();

        // then
        assertEquals(expectedSize, questions.size());
    }

    @Test
    void testFindById() {
        // given
        String title = "title";
        String content = "content";
        String writer = "1234";
        Question save = new Question(title, content, writer, userId);

        Long id = questionRepository.save(save);
        ;

        // when
        Question question = questionRepository.findById(id);

        // then
        assertEquals(title, question.getTitle());
        assertEquals(content, question.getContent());
        assertEquals(writer, question.getWriter());
    }

    @Test
    void testUpdate() {
        // given
        String title = "title";
        String content = "content";
        String writer = "1234";
        Question save = new Question(title, content, writer, userId);

        Long id = questionRepository.save(save);
        ;

        String updatedTitle = "updatedTitle";
        String updatedContent = "updatedContent";
        Question target = questionRepository.findById(id);

        target.update(updatedTitle, updatedContent);

        // when
        questionRepository.update(target);

        // then
        Question question = questionRepository.findById(id);
        assertEquals(updatedTitle, question.getTitle());
        assertEquals(updatedContent, question.getContent());
    }

    @Test
    void testDeleteById() {
        // given
        String title = "title";
        String content = "content";
        String writer = "1234";
        Question save = new Question(title, content, writer, userId);

        Long id = questionRepository.save(save);
        ;

        // when
        questionRepository.deleteById(id);

        // then
        assertEquals(0, questionRepository.findAll().size());
    }
}
