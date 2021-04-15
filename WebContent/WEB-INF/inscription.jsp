<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap core CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<title>Inscription</title>
</head>
<body>

<div class="container ">
	<jsp:include page="./header.jsp"/>
	<div class="row m-auto" style="max-width: 70%;">
		<div class="col-12">
			<c:if test="${!empty error}">
				<p style="color:red;white-space: pre-line">${error}</p>
			</c:if>
			<form  class="min-vh-80 d-flex align-items-center" action="${pageContext.request.contextPath}/inscription" method="POST">
			  	<div class="row">
				  	<div class="col-md-6">
					  <div class="mb-3">
					    <label for="pseudo" class="form-label">Pseudo :</label>
					    <input type="text" class="form-control" id="pseudo" name="pseudo" aria-describedby="pseudo" value="${param.pseudo}">
					  </div>
					  <div class="mb-3">
					    <label for="prenom" class="form-label">Prénom :</label>
					    <input type="text" class="form-control" id="prenom" name="prenom" aria-describedby="prenom" value="${param.prenom}">
					  </div>
					  <div class="mb-3">
					    <label for="tel" class="form-label">Téléphone :</label>
					    <input type="number" class="form-control" id="tel" name="tel" aria-describedby="tel" value="${param.tel}">
					  </div>
					  <div class="mb-3">
					    <label for="cp" class="form-label">Code postal :</label>
					    <input type="number" class="form-control" id="cp" name="cp" aria-describedby="cp" value="${param.cp}">
					  </div>
					  <div class="mb-3">
					    <label for="password" class="form-label">Mot de passe</label>
					    <input type="password" class="form-control" id="password" name="password" value="${param.password}">
					  </div>
					</div>
					<div class="col-md-6">
					  <div class="mb-3">
					    <label for="nom" class="form-label">Nom :</label>
					    <input type="text" class="form-control" id="nom" name="nom" aria-describedby="nom" value="${param.nom}">
					  </div>
					  <div class="mb-3">
					    <label for="email" class="form-label">Email :</label>
					    <input type="email" class="form-control" id="email" name="email" aria-describedby="email" value="${param.email}">
					  </div>
					  <div class="mb-3">
					    <label for="rue" class="form-label">Rue :</label>
					    <input type="text" class="form-control" id="rue" name="rue" aria-describedby="rue" value="${param.rue}">
					  </div>
					  <div class="mb-3">
					    <label for="ville" class="form-label">Ville :</label>
					    <input type="text" class="form-control" id="ville" name="ville" aria-describedby="ville" value="${param.ville}">
					  </div>
					  <div class="mb-3">
					    <label for="confirmation" class="form-label">Confirmation :</label>
					    <input type="password" class="form-control" id="confirmation" name="confirmation" value="${param.confirmation}">
					  </div>
					</div>
				</div>
				<div class="row">
				    <div class="col-sm-6">
				      <button type="submit" class="w-100 btn btn-primary">Créer</button>
				    </div>
				    <div class="col-sm-6">
				      <a class="btn w-100 btn-light" href="${pageContext.request.contextPath}/accueil" >Annuler</a>
				    </div>
				</div>
			</form>
		</div>
	</div>			
</div>
<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</body>
</html>