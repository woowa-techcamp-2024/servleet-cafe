package codesquad.javacafe.comment.service;

import codesquad.javacafe.comment.dto.request.CommentRequestDto;
import codesquad.javacafe.comment.dto.response.CommentResponseDto;
import codesquad.javacafe.comment.entity.Comment;
import codesquad.javacafe.comment.repository.CommentRepository;
import codesquad.javacafe.common.exception.ClientErrorCode;

import java.util.List;
import java.util.stream.Collectors;

public class CommentService {
    private static final CommentService instance = new CommentService();
    private CommentService() {}
    public static CommentService getInstance() {
        return instance;
    }

    public CommentResponseDto save(CommentRequestDto commentDto) {
        var comment = commentDto.toEntity();
        var savedComment = CommentRepository.getInstance().save(comment);
        return new CommentResponseDto(savedComment);
    }

    public List<CommentResponseDto> getCommentList(long postId) {
        var commentList = CommentRepository.getInstance().findAll(postId);
        return commentList.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public void delete(long commentId) {
        var result = CommentRepository.getInstance().delete(commentId);
        if (result == 0) {
            throw ClientErrorCode.COMMENT_IS_NULL.customException("comment id = " + commentId);
        }
    }
}
