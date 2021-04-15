<nav class="nav">
	<a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/#">Ench�res</a>
	<a class="nav-link" href="${pageContext.request.contextPath}/nouvellevente">Vendre un article</a>
	<a class="nav-link" href="${pageContext.request.contextPath}/profil">Mon profil</a>
	<c:if test="${sessionScope.user.administrateur}">
		<a class="nav-link" href="${pageContext.request.contextPath}/admin">Admin</a>
	</c:if>
	<a class="nav-link" href="${pageContext.request.contextPath}/accueil?deconnexion">D�connexion</a>
</nav>