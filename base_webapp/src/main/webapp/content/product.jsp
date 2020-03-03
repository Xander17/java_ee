<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<h1 align="center"><c:out value="${requestScope.pageTitle}"/></h1>
<table align="center">
    <tr>
        <td><label>Артикул</label></td>
        <td><fmt:formatNumber pattern="########" minIntegerDigits="8" value="${product.id}"/></td>
    </tr>
    <tr>
        <td><label>Название товара</label></td>
        <td><c:out value="${product.name}"/></td>
    </tr>
    <tr>

        <td><label>Описание товара</label></td>
        <td><c:out value="${product.description}"/></td>
    </tr>
    <tr>

        <td><label>Цена</label></td>
        <td><fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${product.price}"/></td>
    </tr>
    <tr><td colspan="2"><br></td></tr>
    <c:url var="addToCart" value="/catalog/cart"/>
    <form id="cartForm" action="${addToCart}" method="post">
        <tr>
            <input type="hidden" value="${product.id}" id="id" name="id">
            <td><label for="quantity">Кол-во</label></td>
            <td><input type="number" value="1" id="quantity" name="quantity"></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Добавить"></td>
        </tr>
    </form>
</table>