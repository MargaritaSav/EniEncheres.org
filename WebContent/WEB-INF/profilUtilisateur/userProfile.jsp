<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title>ENI-Encheres - Mon Profil</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div class="container">
    <header>
        <jsp:include page="../header.jsp"/>
    </header>
    <div class="vh-100 d-flex align-items-center justify-content-center" id="show-profile">
        <div class="row">
            <div class="col-6 text-right">Pseudo :</div>
            <div class="col-6">${sessionScope.user.pseudo}</div>

            <div class="col-6 text-right">Nom :</div>
            <div class="col-6">${sessionScope.user.nom}</div>

            <div class="col-6 text-right">Prénom :</div>
            <div class="col-6">${sessionScope.user.prenom}</div>

            <div class="col-6 text-right">Email</div>
            <div class="col-6">${sessionScope.user.email}</div>

            <div class="col-6 text-right">Teléphone</div>
            <div class="col-6">${sessionScope.user.telephone}</div>

            <div class="col-6 text-right">Rue</div>
            <div class="col-6">${sessionScope.user.rue}</div>

            <div class="col-6 text-right">Code postal</div>
            <div class="col-6">${sessionScope.user.codePostal}</div>

            <div class="col-6 text-right">Ville</div>
            <div class="col-6">${sessionScope.user.ville}</div>

            <div class="col-6 text-right">Credit :</div>
            <div class="col-6">${sessionScope.user.credit}</div>

            <div class="col-12">
                <div class="row justify-content-center">
                    <div class="col-4 mt-3 text-right">

                        <a href="${pageContext.request.contextPath}/profil/edit" class="btn btn-warning" id="edit-btn">Modifier
                            mon profil</a>
                    </div>

                    <div class="col-4 mt-3">
                  	
                    	<button type="button" class="btn btn-danger" id="edit-btn" data-target="#confirmation-modal" data-toggle="modal">Supprimer
                            mon profil</button>
                    
                        
                    </div>
                </div>
            </div>

        </div>
    </div>

</div>

<div class="modal fade" id="confirmation-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Suppression d'utilisateur</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        Etes-vous sur(e) de vouloir supprimer votre profil ?
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
        <form action="${pageContext.request.contextPath}/profil/delete" method="POST">             
                    	<button type="submit" class="btn btn-danger" id="edit-btn">Supprimer
                           </button>
         </form>
       
      </div>
    </div>
  </div>
</div>
</body>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

</html>