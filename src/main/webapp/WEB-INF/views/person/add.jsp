<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Добавление персоны</title>
    <style>
        .field {
            clear: both;
            text-align: right;
            line-height: 25px;
        }
    </style>
    <%--<jsp:include page="/WEB-INF/views/includes/add.css"/>--%>
    <%@include file="/WEB-INF/views/includes/header.jsp" %>

        <section>
            <div class="row">
                <div class="col-md-4 col-lg-4 content-container">
                    <div class="field">
                        <form method="post">
                            <label>
                                firstName: <input name="firstName"/>
                            </label>
                            <br/>
                            <label>
                                lastName: <input name="lastName"/>
                            </label>
                            <br/>
                            <label>
                                passport: <input name="passport"/>
                            </label>
                            <br/>
                            <label>
                                identificationNumber: <input name="identificationNumber"/>
                            </label>
                            <br/>
                            <label>
                                phone: <input name="phone"/>
                            </label>
                            <br/>
                            <label>
                                email: <input input="email" name="email"/>
                            </label>
                            <br/>
                            <label>
                                password: <input type="password" name="password"/>
                            </label> <br/>
                            <label>
                                status: <select size="1" name="status">
                                <c:forEach var="personStatusValue"
                                           items="<%=org.ivanov.Domain.entity.status.values() %>">
                                    <option> ${personStatusValue}</option>
                                </c:forEach>
                            </select>
                            </label>
                            <br/>
                            <br/>
                            <input type="submit"/>
                        </form>
                    </div>
                </div>
            </div>
        </section>
        <%@include file="/WEB-INF/views/includes/footer.jsp" %>

