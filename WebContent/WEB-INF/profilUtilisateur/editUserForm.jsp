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
    <title>ENI-Encheres - Mon Profil</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	<div class="container">
		<header>
			<jsp:include page="../header.jsp" />
		</header>
<div class="mt-5 m-auto" style="max-width:600px">
    <form action="${pageContext.request.contextPath}/profil/edit" method="POST">
        <div class="row">
            <div class="col-12">
                <div class="mb-3">
                    <label for="pseudo" class="form-label">Pseudo :</label>
                    <input type="text" class="form-control" id="pseudo" name="pseudo" aria-describedby="pseudo"
                           value="${sessionScope.user.pseudo}">
                </div>
            </div>
            <div class="col-md-6">
                <div class="mb-3">
                    <label for="nom" class="form-label">Nom :</label>
                    <input type="text" class="form-control" id="nom" name="nom" aria-describedby="nom"
                           value="${sessionScope.user.nom}">
                </div>
            </div>
            <div class="col-md-6">
                <div class="mb-3">
                    <label for="prenom" class="form-label">Prénom :</label>
                    <input type="text" class="form-control" id="prenom" name="prenom" aria-describedby="prenom"
                           value="${sessionScope.user.prenom}">
                </div>
            </div>
            <div class="col-md-6">
                <div class="mb-3">
                    <label for="email" class="form-label">Email :</label>
                    <input type="email" class="form-control" id="email" name="email" aria-describedby="email"
                           value="${sessionScope.user.email}">
                </div>
            </div>
            <div class="col-md-6">
                <div class="mb-3">
                    <label for="tel" class="form-label">Téléphone :</label>
                    <input type="number" class="form-control" id="tel" name="tel" aria-describedby="tel"
                           value="${sessionScope.user.telephone}">
                </div>
            </div>
            <div class="col-12">
                <div class="mb-3">
                    <label for="rue" class="form-label">Rue :</label>
                    <input type="text" class="form-control" id="rue" name="rue" aria-describedby="rue"
                           value="${sessionScope.user.rue}">
                </div>
            </div>
            <div class="col-md-6">
                
                    <div class="mb-3">
                        <label for="ville" class="form-label">Ville :</label>
                        <input type="text" class="form-control" id="ville" name="ville" aria-describedby="ville"
                               value="${sessionScope.user.ville}">
                    </div>
               
            </div>
            <div class="col-md-6">
            <div class="mb-3">
                    <label for="cp" class="form-label">Code postal :</label>
                    <input type="number" class="form-control" id="cp" name="cp" aria-describedby="cp"
                           value="${sessionScope.user.codePostal}">
                </div>
            </div>
            <div class="col-12">
                <div class="mb-3">
                    <label for="password" class="form-label">Mot de passe actuel</label>
                    <input type="password" class="form-control" id="password" name="password_now" value="">
                </div>
            </div>
            <div class="col-md-6">
                <div class="mb-3">
                    <label for="password" class="form-label">Nouveau mot de passe</label>
                    <input type="password" class="form-control" id="password" name="password_new" value="">
                </div>
            </div>


            <div class="col-md-6">
                <div class="mb-3">
                    <label for="confirmation" class="form-label">Confirmation :</label>
                    <input type="password" class="form-control" id="confirmation" name="confirmation" value="">
                </div>
            </div>
            
            <div class="col-md-6">
                Credit : ${sessionScope.user.credit}
            </div>

        </div>
        <div class="row mt-3 mb-3">
            <div class="col-sm-6">
                <button type="submit" class="w-100 btn btn-primary">Enregistrer</button>
            </div>
            <div class="col-sm-6">
                <a class="btn w-100 btn-light" href="${pageContext.request.contextPath}/profil">Annuler</a>
            </div>
        </div>
    </form>
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