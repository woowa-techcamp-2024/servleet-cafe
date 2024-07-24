<%@ page import="org.example.demo.Post" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="kr" xmlns:jsp="http://java.sun.com/JSP/Page">


<jsp:include page="/components/header.jsp" />
<body>
<jsp:include page="/components/nav.jsp" />
<div class="container" id="main">
    <div class="col-md-12 col-sm-12 col-lg-12">
        <%
            Post post = (Post) request.getAttribute("post");
        %>
        <div class="panel panel-default">
          <header class="qna-header">
              <h2 class="qna-title"><%=post.getTitle()%></h2>
          </header>
          <div class="content-main">
              <article class="article">
                  <div class="article-header">
                      <div class="article-header-thumb">
                          <img src="https://graph.facebook.com/v2.3/100000059371774/picture" class="article-author-thumb" alt="">
                      </div>
                      <div class="article-header-text">
                          <a href="/users/<%=post.getWriter().getId()%>" class="article-author-name"><%=post.getWriter().getName()%></a>
                          <a href="/questions/413" class="article-header-time" title="퍼머링크">
                              <%=post.getCreatedAt()%>
                              <i class="icon-link"></i>
                          </a>
                      </div>
                  </div>
                  <div class="article-doc">
                      <%=post.getContents()%>
                  </div>
                  <div class="article-util">
                      <ul class="article-util-list">
                          <li>
                              <a class="link-modify-article" href="/questions/423/form">수정</a>
                          </li>
                          <li>
                              <form class="form-delete" action="/questions/423" method="POST">
                                  <input type="hidden" name="_method" value="DELETE">
                                  <button class="link-delete-article" type="submit">삭제</button>
                              </form>
                          </li>
                          <li>
                              <a class="link-modify-article" href="/">목록</a>
                          </li>
                      </ul>
                  </div>
              </article>

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
                                  <p>이 글만으로는 원인 파악하기 힘들겠다. 소스 코드와 설정을 단순화해서 공유해 주면 같이 디버깅해줄 수도 있겠다.</p>
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
                          <article class="article" id="answer-1406">
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
                                  <p>이 글만으로는 원인 파악하기 힘들겠다. 소스 코드와 설정을 단순화해서 공유해 주면 같이 디버깅해줄 수도 있겠다.</p>
                              </div>
                              <div class="article-util">
                                  <ul class="article-util-list">
                                      <li>
                                          <a class="link-modify-article" href="/questions/413/answers/1405/form">수정</a>
                                      </li>
                                      <li>
                                          <form class="form-delete" action="/questions/413/answers/1405" method="POST">
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
				<form class="delete-answer-form" action="/api/questions/{3}/answers/{4}" method="POST">
					<input type="hidden" name="_method" value="DELETE">
                     <button type="submit" class="delete-answer-button">삭제</button>
				</form>
			</li>
		</ul>
		</div>
	</article>
</script>

<jsp:include page="/components/footer.jsp" />
	</body>
</html>