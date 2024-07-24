package com.woowa.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

import com.woowa.database.QuestionDatabase;
import com.woowa.database.QuestionMemoryDatabase;
import com.woowa.database.UserDatabase;
import com.woowa.database.UserMemoryDatabase;
import com.woowa.framework.web.ResponseEntity;
import com.woowa.model.Author;
import com.woowa.model.Question;
import com.woowa.model.User;
import com.woowa.support.QuestionFixture;
import com.woowa.support.UserFixture;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class QuestionHandlerTest {

    private QuestionHandler questionHandler;
    private UserDatabase userDatabase;
    private QuestionDatabase questionDatabase;

    @BeforeEach
    void setUp() {
        userDatabase = new UserMemoryDatabase();
        questionDatabase = new QuestionMemoryDatabase();
        questionHandler = new QuestionHandler(userDatabase, questionDatabase);
    }

    @Nested
    @DisplayName("createQuestion 호출 시")
    class CreateQuestionTest {

        @Test
        @DisplayName("질문을 생성한다.")
        void createQuestion() {
            //given
            User user = User.create(UUID.randomUUID().toString(), "test@test.com", "password", "nickname");
            String title = "title";
            String content = "content";

            userDatabase.save(user);

            //when
            ResponseEntity response = questionHandler.createQuestion(user.getUserId(), title, content);

            //then
            List<Question> result = questionDatabase.findAll();
            assertThat(result).hasSize(1).first().satisfies(question -> {
                assertThat(question.getTitle()).isEqualTo(title);
                assertThat(question.getContent()).isEqualTo(content);
                assertThat(question.getAuthor()).isEqualTo(Author.from(user));
            });
        }

        @Test
        @DisplayName("메인 화면으로 리다이렉트한다.")
        void redirectToMain() {
            //given
            User user = User.create(UUID.randomUUID().toString(), "test@test.com", "password", "nickname");
            String title = "title";
            String content = "content";

            userDatabase.save(user);

            //when
            ResponseEntity response = questionHandler.createQuestion(user.getUserId(), title, content);

            //then
            assertThat(response.getStatus()).isEqualTo(302);
            assertThat(response.getLocation()).isEqualTo("/");
        }

        @Test
        @DisplayName("예외(NoSuchElement): 존재하지 않는 사용자이면")
        void noSuchElement_whenUserNotFound() {
            //given
            String userId = "nope";
            String title = "title";
            String content = "content";

            //when
            Exception exception = catchException(() -> questionHandler.createQuestion(userId, title, content));

            //then
            assertThat(exception).isInstanceOf(NoSuchElementException.class);
        }
    }

    @Nested
    @DisplayName("findQuestions 호출 시")
    class FindQuestionsTest {

        @Test
        @DisplayName("질문 목록이 생성된 시간 역순으로 반환된다.")
        void questionsOrderByCreatedAt() {
            //given
            User user = User.create(UUID.randomUUID().toString(), "test@test.com", "password", "nickname");
            for(int i=0; i<10; i++) {
                Question question = Question.create(UUID.randomUUID().toString(), "title" + i, "content", Author.from(user),
                        ZonedDateTime.now().plusHours(i));
                questionDatabase.save(question);
            }

            //when
            ResponseEntity response = questionHandler.findQuestions(0, 10);

            //then
            Object questions = response.getModel().get("questions");
            assertThat(questions).isNotNull()
                    .asInstanceOf(LIST)
                    .hasSize(10)
                    .map(question -> ((Question)question).getCreatedAt())
                    .isSortedAccordingTo(Comparator.reverseOrder());
        }

        @Test
        @DisplayName("페이지네이션이 적용된다.")
        void pagination() {
            //given
            User user = User.create(UUID.randomUUID().toString(), "test@test.com", "password", "nickname");
            for(int i=0; i<20; i++) {
                Question question = Question.create(UUID.randomUUID().toString(), "title" + i, "content", Author.from(user),
                        ZonedDateTime.now().plusHours(i));
                questionDatabase.save(question);
            }

            //when
            ResponseEntity response = questionHandler.findQuestions(1, 10);

            //then
            Object questions = response.getModel().get("questions");
            assertThat(questions).isNotNull()
                    .asInstanceOf(LIST)
                    .hasSize(10)
                    .first()
                    .satisfies(question -> assertThat(((Question) question).getTitle()).isEqualTo("title" + 9));
        }
    }

    @Nested
    @DisplayName("findQuestion 호출 시")
    class FindQuestionTest {

        @Test
        @DisplayName("단일 게시글을 조회한다.")
        void findQuestion() {
            //given
            User user = UserFixture.user();
            Question question = QuestionFixture.question(user);

            questionDatabase.save(question);

            //when
            ResponseEntity response = questionHandler.findQuestion(question.getQuestionId());

            //then
            Object result = response.getModel().get("question");
            assertThat(result).isNotNull().isInstanceOf(Question.class);
            Question findQuestion = (Question) result;
            assertThat(findQuestion.getTitle()).isEqualTo(question.getTitle());
            assertThat(findQuestion.getContent()).isEqualTo(question.getContent());
            assertThat(findQuestion.getAuthor()).isEqualTo(Author.from(user));
            assertThat(findQuestion.getCreatedAt()).isEqualTo(question.getCreatedAt());
        }
    }
}
