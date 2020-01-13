<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<h1 align="center"><c:out value="${requestScope.pageTitle}"/></h1>
<div>
    <table border="1" align="center">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Id</th>
            <th scope="col">Название</th>
            <th scope="col">Описание</th>
            <th scope="col">Цена</th>
            <th scope="col">Количество</th>
            <th scope="col">Сумма</th>
            <th scope="col">Редактирование</th>
        </tr>
        </thead>
        <tbody>
        <c:set var="count" value="0"/>
        <c:set var="sum" value="0"/>
        <c:forEach var="product" items="${requestScope.products}">
            <c:set var="count" value="${count + 1}"/>
            <c:set var="sum" value="${sum + (product.key.price * product.value)}"/>
            <tr>
                <th><c:out value="${count}"/></th>
                <td><fmt:formatNumber pattern="########" minIntegerDigits="8" value="${product.key.id}"/></td>
                <td>
                    <c:url var="productUrl" value="/catalog/product">
                        <c:param name="id" value="${product.key.id}"/>
                    </c:url>
                    <a href="${productUrl}"><c:out value="${product.key.name}"/></a>
                </td>
                <td><c:out value="${product.key.description}"/></td>
                <td align="center"><fmt:formatNumber minFractionDigits="2" maxFractionDigits="2"
                                                     value="${product.key.price}"/></td>
                <td align="center">
                    <c:url var="editQuantityUrl" value="/cart/update"/>
                    <form action="${editQuantityUrl}" method="post">
                        <input type="hidden" value="${product.key.id}" id="id" name="id">
                        <input type="number" value="${product.value}" id="quantity" name="quantity"><br>
                        <input type="submit" value="Изменить">
                    </form>
                </td>
                <td align="center"><fmt:formatNumber minFractionDigits="2" maxFractionDigits="2"
                                                     value="${product.key.price * product.value}"/></td>
                <td>
                    <c:url value="/cart/delete" var="productDeleteUrl">
                        <c:param name="id" value="${product.key.id}"/>
                    </c:url>
                    <a href="${productDeleteUrl}">Удалить</a>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="6"></td>
            <td><fmt:formatNumber minFractionDigits="2" maxFractionDigits="2"
                                  value="${sum}"/></td>
            <td></td>
        </tr>
        </tbody>
    </table>
    <c:if test="${(not empty sessionScope.cartSize)&&(sessionScope.cartSize>0)}">
        <p align="center">
            <br>
            <c:url var="orderUrl" value="/cart/order"/>
            <a href="${orderUrl}">Оформить заказ</a>
        </p>
    </c:if>
</div>