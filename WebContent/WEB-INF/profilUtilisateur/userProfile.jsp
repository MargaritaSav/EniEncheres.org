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

                        <a href="${pageContext.request.contextPath}/profil/delete" class="btn btn-danger" id="edit-btn">Supprimer
                            mon profil</a>
                    </div>
                </div>
            </div>

        </div>
    </div>

</div>
</body>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"
        integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
        crossorigin="anonymous"></script>

<script>
    function loadHtml(href) {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open("GET", href, false);
        xmlhttp.send();
        return xmlhttp.responseText;
    }

    document.getElementById("edit-btn").addEventListener("click", () => {
        let form = loadHtml("./profil/edit");
        document.getElementById("show-profile").innerHTML = form;

    })
</script>
</html>