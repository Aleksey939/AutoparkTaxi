<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Редактирование персоны</title>
    <style>
        .field {
            clear: both;
            text-align: right;
            line-height: 25px;
        }
    </style>
    <%@include file="/WEB-INF/views/includes/header.jsp" %>

        <section>
            <div class="row">
                <div class="col-md-4 col-lg-4 content-container">
                    <div class="field">
                        <form method="post">
                            <label>
                                <input type="hidden" value="${person.id}" name="id"/>
                            </label>
                            <br/>
                            <label>
                                firstName: <input value="${person.firstName}" name="firstName"/>
                            </label>
                            <br/>
                            <label>
                                lastName: <input value="${person.lastName}" name="lastName"/>
                            </label>
                            <br/>
                            <label>
                                passport: <input value="${person.passport}" name="passport"/>
                            </label>
                            <br/>
                            <label>
                                identificationNumber: <input value="${person.identificationNumber}"
                                                             name="identificationNumber"/>
                            </label>
                            <br/>
                            <label>
                                phone: <input value="${person.phone}" name="phone"/>
                            </label>
                            <br/>
                            <label>
                                email: <input input="email" value="${person.email}" name="email"/>
                            </label>
                            <br/>
                            <label>
                                password: <input name="password"/>
                            </label> <br/>


                            <label>
                                status: <select size="1" name="status">
                                <c:forEach var="personStatusValue"
                                           items="<%=org.ivanov.Domain.entity.status.values() %>">
                                    <option> ${personStatusValue}</option>
                                </c:forEach>
                            </select>
                            </label> <br/>


                            <br/>
                            <input type="submit"/>
                        </form>
                    </div>
                </div>
            </div>
        </section>


<%@include file="/WEB-INF/views/includes/footer.jsp" %>