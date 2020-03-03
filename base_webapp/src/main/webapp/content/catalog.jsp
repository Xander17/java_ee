<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<h1 align="center"><c:out value="${requestScope.pageTitle}"/></h1>

<div align="center">
    <c:url value="/catalog/create" var="productCreateUrl"/>
    <a href="${productCreateUrl}">Добавить товар</a>
</div>
<br>
<div>
    <table border="1" align="center">
        <thead>
        <tr>
            <th scope="col">Артикул</th>
            <th scope="col">Название</th>
            <th scope="col">Описание</th>
            <th scope="col">Цена</th>
            <th scope="col">В корзину</th>
            <th scope="col">Редактирование</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="product" items="${requestScope.products}">
            <tr>
                <th><fmt:formatNumber pattern="########" minIntegerDigits="8" value="${product.id}"/></th>
                <td>
                    <c:url var="productUrl" value="/catalog/product">
                        <c:param name="id" value="${product.id}"/>
                    </c:url>
                    <a href="${productUrl}"><c:out value="${product.name}"/></a>
                </td>
                <td><c:out value="${product.description}"/></td>
                <td align="center"><fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${product.price}"/></td>
                <td align="center">
                    <c:url var="addToCart" value="/catalog/cart"/>
                    <form id="cartForm" action="${addToCart}" method="post">
                        <input type="hidden" value="${product.id}" id="id" name="id">
                        <input type="number" value="1" id="quantity" name="quantity"><br>
                        <input type="submit" value="Добавить">
                    </form>
                </td>
                <td>
                    <c:url value="/catalog/edit" var="productEditUrl">
                        <c:param name="id" value="${product.id}"/>
                    </c:url>
                    <a href="${productEditUrl}">Изменить</a><br>
                    <c:url value="/catalog/delete" var="productDeleteUrl">
                        <c:param name="id" value="${product.id}"/>
                    </c:url>
                    <a href="${productDeleteUrl}">Удалить</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>