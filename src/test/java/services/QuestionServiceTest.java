package services;

import camp.woowa.jspcafe.repository.InMemQuestionRepository;
import camp.woowa.jspcafe.repository.QuestionRepository;
import camp.woowa.jspcafe.services.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionServiceTest {
    QuestionRepository questionRepository;
    QuestionService questionService;

    @BeforeEach
    void setUp() {
        questionRepository = new InMemQuestionRepository();
        questionService = new QuestionService(questionRepository);
    }

    @Test
    void TestSave() {
        // given
        String title = "title";
        String content = "content";
        String writer = "1234";
        Long expectedId = 1L;

        // when
        Long id = questionService.save(title, content, writer);

        // then
        assertEquals(expectedId, id);
    }

    @Test
    void TestFindAll() {
        // given
        String title = "title";
        String content = "content";
        String writer = "1234";
        questionService.save(title, content, writer);

        int expectedSize = 1;

        // when
        int size = questionService.findAll().size();

        // then
        assertEquals(expectedSize, size);
    }
}
