<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Bootstrap core CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Connexion</title>
</head>
<body>

<div class="container-sm">
	<jsp:include page="./header.jsp"/>
	<div class="row position-absolute top-50 start-50 translate-middle" style="max-width: 500px; min-width: 300px">
		<div class="col-sm-12 ">
			<h2 class="text-center mb-3" >Connectez-vous</h2>
			
			<form action="${pageContext.request.contextPath}/connexion" method="POST">
			  <div class="mb-3">
			    <label for="login" class="form-label">Email ou pseudo</label>
			    <input type="text" class="form-control" id="login" name="login" aria-describedby="login" value="${cookie.eni_login != null ? cookie.eni_login.value : param.login}">
			  </div>
			  <div>
			    <label for="password" class="form-label">Mot de passe</label>
			    <input type="password" class="form-control" id="password" name="password" value="${param.password}">
			  </div>
			  <div class="row mb-3">
		    	<div class="col-6 mt-2">
		    		<a class="link-primary" href="${pageContext.request.contextPath}/motDePasseOublie">Mot de passe oublié</a>
		    	</div>
			  </div>
			  <div class="row mb-3">
		    	<div class="col-12">
		    		<div class="form-check">
				    <input type="checkbox" class="form-check-input" id="resterConnecte" name="resterConnecte">
				    <label class="form-check-label" for="resterConnecte">Se souvenir de moi</label>
				  </div>
		    	</div>
			  </div>
			  <div class="row">
			    <div class="col-12">
			      <button type="submit" class="btn btn-primary w-100">Connexion</button>
			    </div>
			  </div>
			</form>
		</div>
		<div class="col-12 d-flex justify-content-center mt-3">
	  		<a href="${pageContext.request.contextPath}/inscription" class="btn btn-light w-100">Créer un compte</a>
	  	</div>
	</div>
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