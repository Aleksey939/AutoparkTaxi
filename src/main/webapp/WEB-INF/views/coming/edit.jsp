<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Add Coming</title>
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
        <h3>Добавление Прихода</h3>
        <div class="row">
            <div class="col-md-6 col-lg-6 content-container">
                <div class="field">
                    <form method="post">

                        <label>
                            car: <select size="1" name="carId">
                            <option value=""> </option>
                            <c:forEach var="car" items="${cars}">
                                <option value="${car.id}"> ${car.number}</option>
                            </c:forEach>
                        </select>
                        </label>
                        <br/>
                        <input type="hidden" value="${coming.id}" name="id"/>
                        <label>startDate: <input type="date" name="startDateDto"/>
                        </label>
                        <br/>
                        <%--<label>EndDate: <input input="date" name="EndDate"/>--%>
                        <%--</label>--%>
                        <%--<br/>--%>
                        <label>income: <input value="${coming.income}" input="number" step=".01" name="income"/>
                        </label>
                        <br/>
                        <label>Бонус: <input value="${coming.bonus}" step=".01" name="bonus"/>
                        </label>
                        <br/>
                        <%--<label>commissionPartner: <input input="number" name="commissionPartner"/>--%>
                        <%--</label>--%>
                        <%--<br/>--%>
                        <%--<label>driverSalary: <input name="driverSalary"/>--%>
                        <%--</label>--%>
                        <%--<br/>--%>
                        <label>mileage: <input value="${coming.mileage}" input="number" name="mileage"/>
                        </label>
                        <br/>
                        <%--<label>consumptionOneKm:  <input input="number" name="consumptionOneKm"/>--%>
                        <%--</label>--%>
                        <%--<br/>--%>
                        <label>fuelCosts: <input value="${coming.fuelCosts}" input="number" step=".01" name="fuelCosts"/>
                        </label>
                        <br/>
                        <%--<label>profitOneKm: <input input="number" name="profitOneKm"/>--%>
                        <%--</label>--%>
                        <%--<br/>--%>
                        <%--<label>fundMaintenance: <input name="fundMaintenance"/>--%>
                        <%--</label>--%>
                        <%--<br/>--%>
                        <label>costsOfMaintenance: <input value="${coming.costsOfMaintenance}" input="number" step=".01" name="costsOfMaintenance"/>
                        </label>
                        <br/>
                        <%--<label>capitalizationMaintenance:  <input input="number"--%>
                        <%--name="capitalizationMaintenance"/>--%>
                        <%--</label>--%>
                        <%--<br/>--%>
                        <%--<label>fundRepairs: <input input="number" name="fundRepairs"/>--%>
                        <%--</label>--%>
                        <%--<br/>--%>
                        <label>costsOfRepairs: <input value="${coming.costsOfRepairs}" input="number" step=".01" name="costsOfRepairs"/>
                        </label>
                        <br/>
                        <%--<label>capitalizationRepairs: <input name="capitalizationRepairs"/>--%>
                        <%--</label>--%>
                        <%--<br/>--%>
                        <label>currentExpenses: <input value="${coming.currentExpenses}" input="number" step=".01" name="currentExpenses"/>
                        </label>
                        <br/>
                        <label>carWash: <input value="${coming.carWash}" input="number" step=".01" name="carWash"/>
                        </label>
                        <br/>
                        <%--<label>profit: <input input="number" name="profit"/>--%>
                        <%--</label>--%>
                        <%--<br/>--%>
                        <%--<label>commissionControl: <input input="number" name="commissionControl"/>--%>
                        <%--</label>--%>
                        <%--<br/>--%>
                        <%--<label>investorIncomecommissionControl: <input input="number"--%>
                        <%--name="investorIncome"/>--%>
                        <%--</label>--%>
                        <%--<br/>--%>
                        <br/>
                        <input type="submit"/>
                    </form>
                </div>
            </div>
        </div>

    </section>
<%@include file="/WEB-INF/views/includes/footer.jsp" %>