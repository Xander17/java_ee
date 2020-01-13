<%@page contentType="text/html" pageEncoding="UTF-8" %>
<div class="topmenu" align="center">
    <a href="${pageContext.request.contextPath}">Главная страница</a>
    <a href="${pageContext.request.contextPath}/catalog">Каталог</a>
    <a href="${pageContext.request.contextPath}/cart">Корзина<c:if test="${(not empty sessionScope.cartSize)&&(sessionScope.cartSize>0)}">(<c:out
            value="${sessionScope.cartSize}"/>)</c:if></a>
    <a href="${pageContext.request.contextPath}/about">О компании</a>
</div>