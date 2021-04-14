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

<style>
    .form-control {
        border: 1px;
        border-radius: 0;
    }

    .col-8 {
        margin: auto;
    }
</style>
<body>

<div class="container min-vh-50">
    <header>
        <div class="row mt-1">
            <div class="col-12 col-md-4 d-flex align-items-center">
                <jsp:include page="./header.jsp"/>
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
    
    <div class="row mt-5">
        <div class="col-8 m-auto" style="max-width: 100%">
            <div class="col-12 ">
                <div class="d-flex justify-content-center">Detail vente</div>
			
                <div class="mb-3" style="margin-top: 20px;">
                    <label for="login" class="font-weight-bold">Article</label>
                    <p>${article.nomArticle}</p>
                </div>
                <div style="margin-top: 20px;">
                    <label for="categorie" class="font-weight-bold">Categorie</label>

                    <p>${article.categorieArticle.libelle}</p>
                </div>
                <div style="margin-top: 20px;">
                    <label for="description" class="font-weight-bold">Description</label>

                    <p>${article.description}</p>
                </div>
                <div style="margin-top: 20px;">
                    <label for="meilleurOffre" class="font-weight-bold">Meilleur offre</label>
                    <c:choose>
                        <c:when test="${article.prixVente != 0}">
                            <p>${article.prixVente} points par ${article.acheteur.pseudo}</p>
                        </c:when>

                        <c:otherwise>
                            <p>Pas d'offres pour le moment</p>
                        </c:otherwise>
                    </c:choose>

                </div>
                <div style="margin-top: 20px;">
                    <label for="miseAPrix" class="font-weight-bold">Mise à prix </label>

                    <p>${article.miseAPrix} points</p>
                </div>
                <div style="margin-top: 20px;">
                    <label for="finEnchere" class="font-weight-bold">Fin de l'enchère </label>

                    <p>
                        <fmt:parseDate value="${article.dateFinEncheres}" pattern="yyyy-MM-dd" var="finEncheres"
                                       type="date"/>
                        <fmt:formatDate pattern="dd/MMM/yyyy HH:mm" value="${finEncheres}"/>
                    </p>
                </div>
                <div style="margin-top: 20px;">
                    <label for="retrait" class="font-weight-bold">Retrait </label>

                    <p>${article.lieuRetrait.rue} ${article.lieuRetrait.code_postal} ${article.lieuRetrait.ville}</p>
                </div>
                <div style="margin-top: 20px;">
                    <label for="vendeur" class="font-weight-bold">Vendeur </label>

                    <p>${article.vendeur.pseudo}</p>
                </div>
                
                
                  <c:if test="${!empty sessionScope.user && sessionScope.user.noUtilisateur != article.vendeur.noUtilisateur}">
                
                	<form method="POST" action="${pageContext.request.contextPath}/vente?action=details&noArticle=${article.noArticle}" class ="row">
                		<div class="col-3"><label for="encherir">Prix de l'enchere:</label></div>
                		<div class="col-3"><input type="number" id="encherir" name="encherir"
                           min="${article.miseAPrix}" max="10000"></div>
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
                		<div class="col-6"><a type="button" href="${pageContext.request.contextPath}/vente?action=modifier&noArticle=${article.noArticle}" class="btn btn-primary">Modifier</a></div>
                		<div class="col-6"><a type="button" href="${pageContext.request.contextPath}/vente?action=supprimer&noArticle=${article.noArticle}" class="btn btn-primary">Annuler la vente</a></div>
                	</div>	
                		
                </c:if>
            </div>
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