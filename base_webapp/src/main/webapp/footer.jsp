<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="now" class="java.util.Date"/>
<fmt:formatDate value="${now}" var="year" pattern="yyyy"/>
<div align="center">Java EE.
    <c:out value="${year}"/>
</div>
</body>
</html>
