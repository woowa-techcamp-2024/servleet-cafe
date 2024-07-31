<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/template/component/head.jsp"></jsp:include>
<body>
<div>
    <jsp:include page="/template/component/nav.jsp"></jsp:include>
</div>

<div class="container" id="main">
    <div class="col-md-12 col-sm-12 col-lg-12">
        <div class="panel panel-default">
            <header class="qna-header">
                <h2 class="qna-title">${article.title}</h2>
            </header>
            <div class="content-main">
                <article class="article">
                    <div class="article-header">
                        <div class="article-header-thumb">
                            <img src="https://graph.facebook.com/v2.3/100000059371774/picture" class="article-author-thumb" alt="">
                        </div>
                        <div class="article-header-text">
                            <a href="${pageContext.request.contextPath}/users/${article.writer}" class="article-author-name">${article.writer}</a>
                            <a href="/questions/413" class="article-header-time" title="퍼머링크">
                                ${article.createdAt}
                                <i class="icon-link"></i>
                            </a>
                        </div>
                        <div class="article-util">
                            <ul class="article-util-list">
                                <li>
                                    <a class="link-modify-article" href="${pageContext.request.contextPath}/questions/${article.id}/form">수정</a>
                                </li>
                                <li>
                                    <button type="button" onclick="sendDelete()" class="delete-answer-button">삭제</button>
                                </li>
                                <li>
                                    <a class="link-modify-article" href="${pageContext.request.contextPath}">목록</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="article-doc">
                        ${article.contents}
                    </div>
                </article>

                <%--댓글 부분--%>
                <div class="qna-comment">
                    <div class="qna-comment-slipp">
                        <p class="qna-comment-count"><strong>2</strong>개의 의견</p>
                        <div class="qna-comment-slipp-articles">

                            <article class="article" id="answer-1405">
                                <div class="article-header">
                                    <div class="article-header-thumb">
                                        <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                                    </div>
                                    <div class="article-header-text">
                                        <a href="/users/1/자바지기" class="article-author-name">자바지기</a>
                                        <a href="#answer-1434" class="article-header-time" title="퍼머링크">
                                            2016-01-12 14:06
                                        </a>
                                    </div>
                                </div>
                                <div class="article-doc comment-doc">
                                    <p>댓글 예시1</p>
                                </div>
                                <div class="article-util">
                                    <ul class="article-util-list">
                                        <li>
                                            <a class="link-modify-article" href="/questions/413/answers/1405/form">수정</a>
                                        </li>
                                        <li>
                                            <form class="delete-answer-form" action="/questions/413/answers/1405" method="POST">
                                                <input type="hidden" name="_method" value="DELETE">
                                                <button type="submit" class="delete-answer-button">삭제</button>
                                            </form>
                                        </li>
                                    </ul>
                                </div>
                            </article>

                            <form class="submit-write">
                                <div class="form-group" style="padding:14px;">
                                    <textarea class="form-control" placeholder="Update your status"></textarea>
                                </div>
                                <button class="btn btn-success pull-right" type="button">답변하기</button>
                                <div class="clearfix" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/template" id="answerTemplate">
    <article class="article">
        <div class="article-header">
            <div class="article-header-thumb">
                <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
            </div>
            <div class="article-header-text">
                <a href="#" class="article-author-name">{0}</a>
                <div class="article-header-time">{1}</div>
            </div>
        </div>
        <div class="article-doc comment-doc">
            {2}
        </div>
        <div class="article-util">
            <ul class="article-util-list">
                <li>
                    <a class="link-modify-article" href="/api/qna/updateAnswer/{3}">수정</a>
                </li>
                <li>
                    <button type="button" onclick="sendDelete()" class="delete-answer-button">삭제</button>
                </li>
            </ul>
        </div>
    </article>
</script>

<script>
    function sendDelete() {
        var data = {
            title: $("#title").val(),
            contents: $("#contents").val()
        };
        $.ajax({
            url: '${pageContext.request.contextPath}/questions/${article.id}',
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(response) {
                console.log('Success:', response);
                alert("게시글 삭제 성공!");
                window.location.href = '${pageContext.request.contextPath}';
            },
            error: function(xhr, status, error) {
                console.log('Error:', error);
                alert("게시글 삭제에 실패했습니다.");
            }
        });
    }
</script>

<div>
    <jsp:include page="/template/component/footer.jsp"></jsp:include>
</div>
</body>
</html>
