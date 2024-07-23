<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/template/component/htmlhead.jsp"></jsp:include>
<body>

<div>
    <jsp:include page="/template/component/header.jsp"></jsp:include>
</div>

<div class="container" id="main">
    <div class="col-md-10 col-md-offset-1">
        <div class="panel panel-default">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>사용자 아이디</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${users}" varStatus="status">
                    <tr>
                        <th scope="row">${status.count}</th>
                        <td>${user.userId}</td>
                        <td><a href="/users/${user.userId}" class="btn btn-success" role="button">${user.name}</a></td>
                        <td>${user.email}</td>
                        <td><a href="#" class="btn btn-success" role="button">수정</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div>
    <jsp:include page="/template/component/footer.jsp"></jsp:include>
</div>
</body>
</html>