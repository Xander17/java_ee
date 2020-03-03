<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${requestScope.content==null}"><jsp:forward page="/error"/></c:if>
<html>
<%@include file="header.jsp" %>
<body>
<%@include file="topmenu.jsp" %>
<jsp:include page="${requestScope.content}"/>
<%@include file="footer.jsp" %>
</body>
</html>