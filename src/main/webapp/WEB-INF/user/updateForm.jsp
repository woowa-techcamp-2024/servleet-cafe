<%@ page import="org.example.demo.domain.User" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="kr" xmlns:jsp="http://java.sun.com/JSP/Page">


<jsp:include page="/components/header.jsp"/>
<body>
<jsp:include page="/components/nav.jsp"/>

<%
    User user = (User) request.getAttribute("user");
%>
<div class="container" id="main">
    <div class="col-md-6 col-md-offset-3">
        <div class="panel panel-default content-main">
            <form name="question" method="post" action="/users/<%=user.getId()%>">
                <div class="form-group">
                    <label for="userId">사용자 아이디</label>
                    <input class="form-control" id="userId" name="userId" value="<%=user.getUserId()%>"
                           placeholder="<%=user.getUserId()%>" readonly>
                </div>
                <div class="form-group">
                    <label for="password">기존 비밀번호</label>
                    <input type="password" class="form-control" id="passwordCheck" name="passwordCheck"
                           placeholder="기존 Password">
                </div>
                <div class="form-group">
                    <label for="password">새로운 비밀번호</label>
                    <input type="password" class="form-control" id="password" name="password"
                           placeholder="새로운 Password">
                </div>
                <div class="form-group">
                    <label for="name">이름</label>
                    <input class="form-control" id="name" name="name" placeholder="Name">
                </div>
                <div class="form-group">
                    <label for="email">이메일</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="Email">
                </div>
                <button type="submit" class="btn btn-success clearfix pull-right">수정 완료</button>
                <div class="clearfix"/>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/components/footer.jsp"/>
</body>
</html>