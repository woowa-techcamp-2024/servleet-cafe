package codesquad.javacafe.post.controller;

import codesquad.javacafe.common.SubController;
import codesquad.javacafe.post.dto.request.PostCreateRequestDto;
import codesquad.javacafe.post.repository.PostRepository;
import codesquad.javacafe.post.service.PostService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PostController implements SubController {
    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    @Override
    public void doProcess(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        log.info("[PostController doProcess]");
        var method = req.getMethod();
        log.info("[PostController doProcess] method: {}", method);
        switch (method) {
            case "POST" :{
                createPost(req);
                res.sendRedirect("/");
            }
        }
    }

    private void createPost(HttpServletRequest req) {
        var body = req.getParameterMap();
        var postDto = new PostCreateRequestDto(body);
        log.info("[PostController createPost] postRequestDto: {}", postDto);
        PostService.getInstance().createPost(postDto);
    }
}
