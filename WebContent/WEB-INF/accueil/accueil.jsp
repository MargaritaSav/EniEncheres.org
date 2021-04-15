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
    <title>ENI-Encheres</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<div class="container">
    <jsp:include page="../header.jsp"/>
    
    <div class="row">
        <div class="col-12 text-center"><h2>Liste des enchères</h2></div>
        <div class="col-12 col-md-5 mt-2 mb-2">
            <label for="search-control" class="form-label">Recherche</label>
            <div class="input-group" id="search-control">
                <input type="text" class="form-control" id="search-input" placeholder="Le  nom de l'article contient">
                <div class="input-group-btn">
                    <button class="btn btn-secondary rounded-0" type="submit">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                             class="bi bi-search" viewBox="0 0 16 16">
                            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                        </svg>
                    </button>
                </div>
            </div>
        </div>
        <div class="col-12 col-md-4 mb-2 d-flex align-items-end">
            <div class="form-group m-0 w-100">
                <label for="select-categories">Categories</label>
                <select class="form-control" id="select-categories">
                    <option value="0">Toutes les categories</option>
                    <c:forEach var="сategorie" items="${categories}">
                        <option value="${сategorie.noCategorie}">${сategorie.libelle}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="col-12 col-md-3 mb-2 d-flex align-items-end justify-content-center">

            <button class="btn btn-primary" id="clean-btn">Effacer les filters</button>
        </div>

    </div>
	<c:if test="${sessionScope.session.equals('on')}">
    <div class="row" id="user-filters" data-user="${sessionScope.user.noUtilisateur }">
        <div class="col-12 col-md-6">
            <div class="form-group m-0 w-100">
                <div class="form-check">
                    <input class="form-check-input" type="radio" id="buy-radio-btn"
                           value="achat" name="optprincipal" checked onclick="activatePurchase()">
                    <label class="form-check-label" for="buy-radio-btn">
                        Achats
                    </label>
                    <div id="buy-ckbox-group">
                        <div class="form-check">
                            <input class="form-check-input" type="radio" value="all" id="all-ckbox" name="optradio" checked onclick="showAllArticlesToBuy()">
                            <label class="form-check-label" for="all-ckbox" >
                                Enchères ouvertes
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" value="current" id="current-ckbox" name="optradio" onclick="filterCurrentEncheres()">
                            <label class="form-check-label" for="current-ckbox">
                                Mes enchères en cours
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" value="finished" id="finished-ckbox" name="optradio" onclick="filterFinishedEncheres()">
                            <label class="form-check-label" for="finished-ckbox">
                                Mes enchères remportées
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-12 col-md-6">
			<div class="form-group m-0 w-100">
                <div class="form-check">
                    <input class="form-check-input" type="radio" id="sales-radio-btn"
                           value="achat" name="optprincipal" onclick="activateSales()">
                    <label class="form-check-label" for="sales-radio-btn" >
                        Mes ventes
                    </label>
                    <div id="sales-ckbox-group">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" value="current-sales" id="current-sales-ckbox" name="chk[]" onclick="filterBySales()" disabled>
                            <label class="form-check-label" for="current-sales-ckbox" >
                                Mes ventes en cours
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" value="future-sales" id="future-sales-ckbox" name="chk[]" onclick="filterBySales()" disabled>
                            <label class="form-check-label" for="future-sales-ckbox">
                                Ventes non débutées
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" value="finished-sales" id="finished-sales-ckbox" name="chk[]" onclick="filterBySales()" disabled> 
                            <label class="form-check-label" for="finished-sales-ckbox">
                                Ventes terminées
                            </label>
                        </div>

                    </div>

                </div>

            </div>
        </div>

    </div>
    
    </c:if>

    <div class="row mt-5" id="articles-container">

    </div>


</div>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"
        integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
        crossorigin="anonymous"></script>

<script src="./ressources/js/accueil.js" type="text/javascript"></script>
</body>
</html>