<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.entity.User" %>
<%@ page import="com.example.db.UserDatabase" %>
<!DOCTYPE html>
<html lang="kr">
<head>
    <meta charset="UTF-8">
    <title>유저 리스트</title>
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/styles.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-fixed-top header">
    <div class="col-md-12">
        <div class="navbar-header">
            <a href="../index.jsp" class="navbar-brand">SLiPP</a>
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse1">
                <i class="glyphicon glyphicon-search"></i>
            </button>
        </div>
        <div class="collapse navbar-collapse" id="navbar-collapse1">
            <form class="navbar-form pull-left">
                <div class="input-group" style="max-width:470px;">
                    <input type="text" class="form-control" placeholder="Search" name="srch-term" id="srch-term">
                    <div class="input-group-btn">
                        <button class="btn btn-default btn-primary" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                    </div>
                </div>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-bell"></i></a>
                    <ul class="dropdown-menu">
                        <li><a href="https://slipp.net" target="_blank">SLiPP</a></li>
                        <li><a href="https://facebook.com" target="_blank">Facebook</a></li>
                    </ul>
                </li>
                <li><a href="/list.html"><i class="glyphicon glyphicon-user"></i></a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="navbar navbar-default" id="subnav">
    <div class="col-md-12">
        <div class="navbar-header">
            <a href="#" style="margin-left:15px;" class="navbar-btn btn btn-default btn-plus dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-home" style="color:#dd1111;"></i> Home <small><i class="glyphicon glyphicon-chevron-down"></i></small></a>
            <ul class="nav dropdown-menu">
                <li><a href="/profile.html"><i class="glyphicon glyphicon-user" style="color:#1111dd;"></i> Profile</a></li>
                <li class="nav-divider"></li>
                <li><a href="#"><i class="glyphicon glyphicon-cog" style="color:#dd1111;"></i> Settings</a></li>
            </ul>

            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse2">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div class="collapse navbar-collapse" id="navbar-collapse2">
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="../index.jsp">Posts</a></li>
                <li><a href="/login.html" role="button">로그인</a></li>
                <li><a href="/form.html" role="button">회원가입</a></li>
                <li><a href="#" role="button">로그아웃</a></li>
                <li><a href="#" role="button">개인정보수정</a></li>
            </ul>
        </div>
    </div>
</div>

<div class="container" id="main">
    <div class="col-md-10 col-md-offset-1">
        <div class="panel panel-default">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>#</th> <th>사용자 아이디</th> <th>이름</th> <th>이메일</th><th></th>
                </tr>
                </thead>
                <tbody>
                <%
                    UserDatabase userDatabase = (UserDatabase) config.getServletContext().getAttribute("userDatabase");
                    List<User> userList = userDatabase.findAll();
                    for(User user : userList) {
                %>
                <tr>
                    <th scope="row"><%= user.id() %></th>
                    <td><a href="/users/<%= user.id() %>"><%= user.id() %></a></td>
                    <td><%= user.name() %></td>
                    <td><%= user.email() %></td>
                    <td><a href="#" class="btn btn-success" role="button">수정</a></td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- script references -->
<script src="../js/jquery-2.2.0.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/scripts.js"></script>
</body>
</html>
