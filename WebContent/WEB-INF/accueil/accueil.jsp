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
    <header>
        <div class="row mt-1">
            <div class="col-12 col-md-4 d-flex align-items-center">
                <jsp:include page="../header.jsp"/>
            </div>
            <div class="col-12 col-md-8 d-flex justify-content-end align-items-center">
                <!-- TODO : condition de la connexion -->
                <c:choose>
                    <c:when test="${sessionScope.session.equals('on')}">
                        <jsp:include page="./navConnected.jsp"/>
                    </c:when>
                    <c:otherwise>
                        <jsp:include page="./navGuest.jsp"/>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </header>
    <div class="row">
        <div class="col-12 text-center"><h2>Liste des enchères</h2></div>
        <div class="col-12 col-md-6 mt-2">
            <label for="search-control" class="form-label">Filters</label>
            <div class="input-group" id="search-control">
                <input type="text" class="form-control" placeholder="Le  nom de l'article contient">
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
        <div class="col-12 col-md-6 d-flex align-items-end">
            <div class="form-group m-0 w-100">
                <label for="exampleFormControlSelect1">Example select</label>
                <select class="form-control" id="exampleFormControlSelect1">
                    <option>1</option>
                    <option>2</option>
                    <option>3</option>
                    <option>4</option>
                    <option>5</option>
                </select>
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