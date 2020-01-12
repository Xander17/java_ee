<%@page contentType="text/html;charset=UTF-8" %>
<div class="topmenu" align="center">
    <a href="/base_webapp/">Главная страница</a>
    <a href="/base_webapp/catalog">Каталог</a>
    <a href="/base_webapp/cart">Корзина<c:if test="${(not empty sessionScope.cartSize)&&(sessionScope.cartSize>0)}">(<c:out
            value="${sessionScope.cartSize}"/>)</c:if></a>
    <a href="/base_webapp/about">О компании</a>
</div>