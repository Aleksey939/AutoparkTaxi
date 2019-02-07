<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<%--<jsp:include page="/WEB-INF/views/includes/add.css"/>--%>
<html>
<head>
    <title>Добавление оплаты</title>
    <style>
        .field {
            clear: both;
            text-align: right;
            line-height: 25px;
        }
    </style>
</head>
<body>
<%@include file="/WEB-INF/views/includes/header.jsp" %>


    <section>
        <h2>Добавление оплаты</h2>
        <div class="row">
            <div class="col-md-4 col-lg-4 content-container">
                <div class="field">
                    <form method="post">
                        <label>Начало периода: <input type="date" name="startDate"/>
                        </label>
                        <label>
                            Сумма: <input input="number" name="sum"/>
                        </label>
                        <br/>
                        <label>
                            Инвестор: <select size="1" name="personId">
                            <option value=""> </option>
                            <c:forEach var="person" items="${persons}">
                                <option value="${person.id}"> ${person.lastName}</option>
                            </c:forEach>
                        </select>
                        </label>

                        <br/>
                        <input type="submit"/>

                    </form>
                </div>
            </div>
        </div>
    </section>
<%@include file="/WEB-INF/views/includes/footer.jsp" %>