<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Detail Article</title>
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body>

<div class="container">

	<jsp:include page="./header.jsp"/>

    
     <div class="card mt-5">
        <c:if test="${article.etatVente == 'Terminé' && sessionScope.user.pseudo == article.acheteur.pseudo}">
            <h3 class="card-header">Felicitations ! Vous avez remporté cette vente !</h3>
        </c:if>

        <c:if test="${article.etatVente == 'Terminé' && sessionScope.user.pseudo == article.vendeur.pseudo}">
            <h3 class="card-header"><a href="${pageContext.request.contextPath}/profil?pseudo=${article.acheteur.pseudo }">${article.acheteur.pseudo }</a> a remporté cette vente !</h3>
        </c:if>

        <div class="card-body" style="font-size: 20px">
            <div class="row">
                <div class="col-md-6 mb-1 font-weight-bold border-bottom">Article</div>
                <div class="col-md-6 mb-1 border-bottom">${article.nomArticle}</div>
                <div class="col-md-6 font-weight-bold mb-1 border-bottom">Categorie</div>
                <div class="col-md-6 mb-1 border-bottom"><span class="badge badge-info">${article.categorieArticle.libelle}</span></div>
                <div class="col-md-6 font-weight-bold mb-1 border-bottom">Description</div>
                <div class="col-md-6 mb-1 border-bottom">${article.description}</div>
                <div class="col-md-6 font-weight-bold mb-1 border-bottom">Meilleur offre</div>
                <div class="col-md-6 mb-1 border-bottom">
                    <c:choose>
                        <c:when test="${article.prixVente != 0}">
                            ${article.prixVente} points par ${article.acheteur.pseudo}
                        </c:when>

                        <c:otherwise>
                            Pas d'offres pour le moment
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col-md-6 font-weight-bold mb-1 border-bottom">Mise à prix</div>
                <div class="col-md-6 mb-1 border-bottom">${article.miseAPrix} points</div>
                <div class="col-md-6 font-weight-bold mb-1 border-bottom">Fin de l'enchère</div>
                <div class="col-md-6 mb-1 border-bottom">
                    <fmt:parseDate value="${article.dateFinEncheres}" pattern="yyyy-MM-dd'T'HH:mm" var="finEncheres"
                                   type="date"/>
                    <fmt:formatDate pattern="dd/MMM/yyyy HH:mm" value="${finEncheres}"/>
                </div>
                <div class="col-md-6 font-weight-bold mb-1 border-bottom">Retrait</div>
                <div class="col-md-6 mb-1 border-bottom">${article.lieuRetrait.rue} ${article.lieuRetrait.code_postal} ${article.lieuRetrait.ville}</div>
                <div class="col-md-6 font-weight-bold mb-1 border-bottom">Vendeur</div>
                <div class="col-md-6 mb-1 border-bottom">${article.vendeur.pseudo}</div>
            </div>
        </div>
        
        <div class="card-footer">
        	<c:if test="${!empty sessionScope.user && sessionScope.user.noUtilisateur != article.vendeur.noUtilisateur && article.etatVente == 'En cours'}">
                
                	<form method="POST" action="${pageContext.request.contextPath}/vente?action=details&noArticle=${article.noArticle}">
                	<div class="form-row align-items-center">
                		<div class="col-3 d-flex justify-content-center align-items-center">
                			<label for="encherir">Prix de l'enchere:</label>
                		</div>
                		<div class="col-4 ">
                			<div class="row">
                				<div class="col d-flex justify-content-center align-items-center">
                					<input type="number" id="encherir" name="encherir"
                           			min="${article.miseAPrix}" max="10000">
                           		</div>
                				<div class="col"><input class="btn btn-primary" type="submit" value="Encherir"></div>
                			</div>
                		</div>
                		<div class="col-12">
                			<c:if test="${!empty error}">
								<p style="color:red;">${error}</p>
							</c:if>
							<c:if test="${!empty success}">
								<p style="color:green;">${success}</p>
							</c:if>
                		</div>
                	</div>
                </form>	
                </c:if>
                
                <c:if test="${!empty sessionScope.user && sessionScope.user.active && sessionScope.user.noUtilisateur != article.vendeur.noUtilisateur && article.etatVente == 'En cours'}">
                
                	<form method="POST" action="${pageContext.request.contextPath}/vente?action=details&noArticle=${article.noArticle}" class ="row">
                		<div class="col-3"><label for="encherir">Prix de l'enchere:</label></div>
                		<div class="col-3"><input type="number" id="encherir" name="encherir"
                           value="${article.prixVente != 0 ? article.prixVente : article.miseAPrix }"></div>
                		<div class="col-3"><input class="btn btn-primary" type="submit" value="Encherir"></div>
                		
                		<c:if test="${!empty error}">
							<p style="color:red;">${error}</p>
						</c:if>
						<c:if test="${!empty success}">
							<p style="color:green;">${success}</p>
						</c:if>
                	</form>	
                </c:if>
                <c:if test="${!empty sessionScope.user && sessionScope.user.noUtilisateur == article.vendeur.noUtilisateur && article.etatVente == 'Non débutée'}">
                	<div class="row">
                		<div class="col-6 text-right"><a type="button" href="${pageContext.request.contextPath}/vente?action=modifier&noArticle=${article.noArticle}" class="btn btn-warning">Modifier</a></div>
                		<div class="col-6"><a type="button" href="${pageContext.request.contextPath}/vente?action=supprimer&noArticle=${article.noArticle}" class="btn btn-danger">Annuler la vente</a></div>
                	</div>	
                		
                </c:if>
        </div>
    </div>
</div>
                
                
                  
          


<script src="https://code.jquery.com/jquery-3.5.1.min.js"
        integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
        crossorigin="anonymous"></script>
</body>
</html>