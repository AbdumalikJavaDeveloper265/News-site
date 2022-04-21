<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Новости</title>
</head>
<body>
<div>
    <h2>Новости <br> Только для залогинившихся пользователей.</h2>
    
    <div>
  <h3>${pageContext.request.userPrincipal.name}</h3>
  <sec:authorize access="!isAuthenticated()">
    <h4><a href="/login">Войти</a></h4>
    <h4><a href="/registration">Зарегистрироваться</a></h4>
  </sec:authorize>
  <sec:authorize access="isAuthenticated()">
    <h4><a href="/logout">Выйти</a></h4>
  </sec:authorize>
  <h4><a href="/news">Новости (только пользователь)</a></h4>
  <h4><a href="/admin">Пользователи (только админ)</a></h4>
</div>
</div>
</body>
</html>


