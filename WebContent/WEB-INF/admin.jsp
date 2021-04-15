<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des utilisateurs</title>
    <!-- Bootstrap core CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body>

<div class="container min-vh-100">
    <jsp:include page="./header.jsp"/>
    <h2 class="text-center mb-3" >Gestion des utilisateurs </h2>
    <table class="table table-hover table-responsive">
	  <thead>
	    <tr>
	      <th scope="col">#</th>
	      <th scope="col">Pseudo</th>
	      <th scope="col">E-mail</th>
	      <th scope="col">Actif</th>
	      <th scope="col">Admin</th>
	      <th scope="col">Actions</th>
	    </tr>
	  </thead>
	  <tbody>
	    <c:forEach var="user" items="${users}">
			<tr>
		      <th scope="row">${user.noUtilisateur}</th>
		      <td scope="col">${user.pseudo}</td>
		      <td scope="col">${user.email}</td>
		      <td scope="col">${user.active ? "Oui" : "Non"}</td>
		      <td scope="col">${user.administrateur ? "Oui" : "Non"}</td>
		      <td scope="col">
		      	<div class="row">	
			      	<form method="POST" action="${pageContext.request.contextPath}/admin?action=desactiver&pseudo=${user.pseudo}" class ="col-6">
	                		<input class="btn btn-sm btn-secondary" type="submit" value="Désactiver" ${user.active ? "" : "disabled"}>		
	                </form>	
	                <form method="POST" action="${pageContext.request.contextPath}/admin?action=supprimer&noUser=${user.noUtilisateur}" class ="col-6">
	                		<input class="btn btn-sm btn-danger" type="submit" value="Supprimer">		
	                </form>
                </div>
		      </td>
		    </tr>
		</c:forEach>
	  </tbody>
	</table>
	<c:if test="${!empty error || !empty success}">
	  	<div class="position-fixed bottom-0 end-0 p-3">
			<div class="toast align-items-center text-white ${!empty error ? 'bg-danger' : 'bg-success'} border-0" role="alert" aria-live="assertive" aria-atomic="true">
			  <div class="d-flex">
			    <div class="toast-body">
					${!empty error ? error : success}
			    </div>
			    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
			  </div>
			</div>
		</div>
	</c:if>
</div>


<script src="https://code.jquery.com/jquery-3.5.1.min.js"
        integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
        crossorigin="anonymous"></script>
<script>
$(document).ready(function(){
	var toastElList = [].slice.call(document.querySelectorAll('.toast'))
	var toastList = toastElList.map(function (toastEl) {
	  return new bootstrap.Toast(toastEl);
	})
	toastList.forEach(toast => toast.show());
})

</script>
</body>
</html>