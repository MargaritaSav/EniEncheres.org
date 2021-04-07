<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Bootstrap core CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<title>Connexion</title>
</head>
<body>

<div class="container min-vh-100 d-flex align-items-center">
	
	<div class="row w-50 m-auto">
		<div class="col-12">
			<c:if test="${!empty error}">
				<p style="color:red;">${error}</p>
			</c:if>
		</div>
		<div class="col-12">
			<form style="min-width: 20%; max-width:90%;" action="${pageContext.request.contextPath}/connexion" method="POST">
			  <div class="mb-3">
			    <label for="login" class="form-label">Email ou pseudo</label>
			    <input type="text" class="form-control" id="login" name="login" aria-describedby="login" value="${param.login}">
			  </div>
			  <div class="mb-3">
			    <label for="password" class="form-label">Mot de passe</label>
			    <input type="password" class="form-control" id="password" name="password" value="${param.password}">
			  </div>
			  <div class="row">
			    <div class="col">
			      <button type="submit" class="btn btn-primary">Connexion</button>
			    </div>
			    <div class="col">
			    	<div class="row">
				      <div class="mb-3 form-check">
					    <input type="checkbox" class="form-check-input" id="resterConnecte">
					    <label class="form-check-label" for="resterConnecte">Se souvenir de moi</label>
					  </div>
					</div>
					<div class="row">
						<a class="btn" href="${pageContext.request.contextPath}/motDePasseOublie">Mot de passe oublié</a>
					</div>
			    </div> 
			  </div>
			  
			</form>
		</div>
		<div class="col-4">
	  		<a href="${pageContext.request.contextPath}/inscription" class="btn btn-primary">Créer un compte</a>
	  	</div>
	</div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</body>
</html>