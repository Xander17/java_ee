<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<h1 align="center"><c:out value="${requestScope.pageTitle}"/></h1>

<div>
    <c:url value="${requestScope.action}" var="productPostUrl"/>
    <form action="${productPostUrl}" method="post">
        <input type="hidden" value="${product.id}" id="id" name="id">
        <table align="center">
            <c:if test="${requestScope.action=='edit'}">
                <tr>
                    <td><label>Артикул</label></td>
                    <td><label><fmt:formatNumber pattern="########" minIntegerDigits="8" value="${product.id}"/></label>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td><label>Название товара</label></td>
                <td><input type="text" value="${product.name}" id="name" name="name" placeholder="Введите название">
                </td>
            </tr>
            <tr>

                <td><label>Описание товара</label></td>
                <td><input type="text" value="${product.description}" id="description" name="description"
                           placeholder="Введите описание"></td>
            </tr>
            <tr>

                <td><label>Цена</label></td>
                <td><input type="number" value="${product.price}" id="price" name="price"
                           placeholder="Введите цену"></td>
            </tr>
            <tr>
                <td>
                    <button type="submit">
                        <c:choose>
                            <c:when test="${requestScope.action=='create'}"><c:out value="Добавить товар"/></c:when>
                            <c:when test="${requestScope.action=='edit'}"><c:out value="Сохранить изменения"/></c:when>
                        </c:choose>
                    </button>
                </td>
            </tr>
        </table>
    </form>
</div>