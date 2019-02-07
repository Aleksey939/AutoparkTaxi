<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Home</title>


    <%@include file="/WEB-INF/views/includes/header.jsp" %>

        <section>
            <title>Spring Security</title>
            <!-- Bootstrap core CSS -->
            <%--<link href="<c:url value="/pages/css/bootstrap.css" />" rel="stylesheet">--%>
            <!-- Custom styles for this template -->
            <%--<link href="<c:url value="/pages/css/signin.css" />" rel="stylesheet">--%>
            <div class="container" style="width: 300px;">
                <c:url value="/j_spring_security_check" var="loginUrl"/>
                <form action="${loginUrl}" method="post">
                    <h2 class="form-signin-heading">Please sign in</h2>
                    <input type="text" class="form-control" name="j_username" placeholder="Email address" required
                           autofocus value="admin">
                    <input type="password" class="form-control" name="j_password" placeholder="Password" required
                           value="admin">
                    <button class="btn btn-lg btn-primary btn-block" type="submit">Войти</button>
                </form>
            </div>

        </section>

        <%@include file="/WEB-INF/views/includes/footer.jsp" %>



