<%@ page import="ru.javawebinar.topjava.util.DateTimeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h5><a href="meals?action=create">Add Meal</a></h5>
<table>

    <tr>
        <th>Описание</th>
        <th>Калорийность</th>
        <th>Дата</th>
        <th colspan="2">Действие</th>
    </tr>

    <c:forEach items="${meals}" var="meal">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>

    <c:choose>
    <c:when test="${meal.excess==true}">
    <tr bgcolor="red">
        </c:when>
        <c:otherwise>
    <tr bgcolor="green">
        </c:otherwise>
        </c:choose>
        <th>${meal.description}</th>
        <th>${meal.calories}</th>
        <th><%=DateTimeUtil.toString(meal.getDateTime())%>
        </th>
            <th><a href="meals?id=${meal.id}&action=update">Update</a></th>
            <th><a href="meals?id=${meal.id}&action=delete">Delete</a></th>
        </c:forEach>
    </tr>

</table>
</body>
</html>
