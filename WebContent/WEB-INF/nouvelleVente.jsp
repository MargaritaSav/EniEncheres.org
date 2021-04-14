<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
        integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
 
    <link href="https://unpkg.com/gijgo@1.9.13/css/gijgo.min.css" rel="stylesheet" type="text/css" />
    <title>Nouvelle vente</title>
</head>

<body>

    <div class="container ">
    	<header>
	        <div class="row mt-1">
	            <div class="col-12 col-md-4 d-flex align-items-center">
	                <jsp:include page="./header.jsp"/>
	            </div>
	            <div class="col-12 col-md-8 d-flex justify-content-end align-items-center">
	                <jsp:include page="./accueil/navConnected.jsp"/>
	            </div>
	        </div>
	    </header>
        <div class="row m-auto" style="max-width: 600px">
            <div class="col-12">
	            <c:if test="${!empty error}">
					<p style="color:red;white-space: pre-line">${error}</p>
				</c:if>
				<form  action="${pageContext.request.contextPath}/nouvellevente?action=${!empty article || param.action == 'modifier' ? 'modifier' : 'creer'}" method="POST" enctype="multipart/form-data">
	                <c:if test="${!empty article || param.noArticle != null}">
	                	<input id="prodId" name="noArticle" type="hidden" value="${!empty article ? article.noArticle : param.noArticle}">
	                </c:if>
	                
	                <div class="mb-3">
	                    <label for="titre" class="form-label">Article</label>
	                    <input type="text" class="form-control" id="titre" name="titre" aria-describedby="login" value="${!empty article ? article.nomArticle : param.titre}">
	                </div>
	                <div class="mb-3">
	                	<label for="description" class="form-label">Description</label>
					  	<textarea class="form-control" placeholder="Mon article est le meilleur" id="description" name="description" style="height: 100px">${!empty article ? article.description : param.description}</textarea>
					</div>
	
	                <div class="form-group m-0 w-100 mb-3">
		                <label for="categorie">Categories</label>
		                <select class="form-control" id="categorie" name="categorie" >
		                	<option value="0" >Toutes les categories</option>
		                	<c:forEach var="сategorie" items="${categories}">
		                		<c:choose>
		                			<c:when test="${!empty article}">
		                				<option value="${сategorie.noCategorie}" ${article.categorieArticle.noCategorie == сategorie.noCategorie ? "selected" : "" } >${сategorie.libelle}</option>
		                			</c:when>
		                			
		                			<c:otherwise>
		                				<option value="${сategorie.noCategorie}" ${param.categorie == сategorie.noCategorie ? "selected" : "" } >${сategorie.libelle}</option>
		                			</c:otherwise>
		                		</c:choose>
		                		
		                	</c:forEach>
		                </select>
		            </div>
	                <div class="bd-highlight mb-3">
	                    <div class="p-2 bd-highlight">
	                        <input id="input-b2" name="image" type="file" class="file" data-show-preview="false">
	                    </div>
	                </div>
	                <div class="mb-3" >
	                    <label for="prix">Prix de l'enchere:</label>
	
	                    <input type="number" id="prix" name="prix" min="10" max="10000" value="${!empty article ? article.miseAPrix : param.prix ? param.prix : 10}">
	
	                </div>
	                <div class="mb-3">
	                    <label class="form-label" for="debut">Début de l'enchere :</label>
	
	                    <input type="datetime-local" class="form-control" id="debut" name="debut" class="form-control" value="${!empty article ? article.dateDebutEncheres : param.debut}" />
	                </div>
	                <div class="mb-3">
	                    <label class="form-label" for="fin">Fin de l'enchere :</label>
	
	                    <input type="datetime-local" class="form-control" id="fin" name="fin" class="form-control" value="${!empty article ? article.dateFinEncheres : param.fin}" />
	                </div>
	               
	
	                <fieldset class="scheduler-border">
	                    <legend class="scheduler-border">Retrait</legend>
	                    <div class="control-group ">
	                        <label class="control-label input-label" for="rueRetrait">Rue :</label>
	                            <input type="text" class="datetime" type="text" id="rueRetrait" name="rueRetrait"
	                                value="${!empty article ? article.lieuRetrait.rue : param.rueRetrait ? param.rueRetrait : sessionScope.user.rue }" />
	                    </div>
	                       
	                    <div class="control-group ">
	                        <label class="control-label input-label" for="villeRetrait">Ville :</label>
	                            <input type="text" class="datetime" type="text" id="villeRetrait" name="villeRetrait"
	                                value="${!empty article ? article.lieuRetrait.ville : param.villeRetrait ? param.villeRetrait : sessionScope.user.ville }"/>
	                    </div>
	                    
	                    <div class="control-group">
	
	                        <label class="control-label input-label" for="code_postal">Code Postal :</label>
	                    <input type="text" class="datetime" type="text" id="code_postal" name="code_postal"
	                        value="${!empty article ? article.lieuRetrait.code_postal : param.code_postal ? param.code_postal : sessionScope.user.codePostal }" />
	                    </div>
	                    
	                </fieldset>
	                
	                <div class="row">
					    <div class="col-sm-6">
					      <button type="submit" class="w-100 btn btn-primary">Valider</button>
					    </div>
					    <div class="col-sm-6">
					      <a class="btn w-100 btn-light" href="${pageContext.request.contextPath}/accueil"> Annuler</a>
					    </div>
					</div>
				</form>
        	</div>
		</div>
	</div>
    <style>
           fieldset.scheduler-border {
               border: 1px groove #ddd !important;
               padding: 0 1.4em 1.4em 1.4em !important;
               margin: 0 0 1.5em 0 !important;
               -webkit-box-shadow: 0px 0px 0px 0px #000;
               box-shadow: 0px 0px 0px 0px #000;
               flex-flow: wrap-reverse;

           }

           legend.scheduler-border {
               font-size: 1.2em !important;
               font-weight: bold !important;
               text-align: left !important;
               width: auto;
               padding: 0 10px;
               border-bottom: none;
           }

           .btn-secondary {
               color: #fff;
               background-color: #6c757d;
               border-color: #6c757d;
               margin-top: 10px;
           }

           label {
		       display: inline-block;
		       color: #777777;
		     }
    </style>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
    <script src="https://unpkg.com/gijgo@1.9.13/js/gijgo.min.js" type="text/javascript"></script>
    </body>
    </html>