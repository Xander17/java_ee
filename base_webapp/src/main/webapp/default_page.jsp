<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${requestScope.content==null}"><jsp:forward page="/error"/></c:if>
<%@include file="header.jsp" %>
<jsp:include page="${requestScope.content}"/>
<%@include file="footer.jsp" %>