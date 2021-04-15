<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
    <div class="row mt-1">
        <div class="col-12 col-md-4 d-flex align-items-center">
			<a href="${pageContext.request.contextPath}"><h1>ENI-ENCHERES</h1></a>
        </div>
        <div class="col-12 col-md-8 d-flex justify-content-end align-items-center">
            <c:choose>
                <c:when test="${sessionScope.session.equals('on')}">
                    <jsp:include page="./accueil/navConnected.jsp"/>
                </c:when>
                <c:otherwise>
                    <jsp:include page="./accueil/navGuest.jsp"/>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>