<%@ page import="ru.javawebinar.topjava.util.DateTimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>Add/Edit Meal form</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>

<table>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="id" value="${meal.id}">
    <tr>
        <th align="left">Описание</th>
        <th><input type="text" value="${meal.description}" name="description" required></th>
    </tr>
    <tr>
        <th>Калорийность</th>
        <th><input type="number" value="${meal.calories}" name="calories" required></th>
    </tr>
    <tr>
        <th align="left">Дата</th>
        <th><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></th>
    </tr>
    <tr>
        <th><button type="submit">Сохранить</button></th>
        <th><button onclick="window.history.back()">Отменить</button></th>
    </tr>
</form>
</table>
</body>
</html>
