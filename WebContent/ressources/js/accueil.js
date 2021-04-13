let articleContainer = $("#articles-container");
let urlApi = "/EniEncheres.org/api/rest/articles";
let projectPath = "/EniEncheres.org";
let searchInput = $("#search-input");
let selectCat = $("#select-categories");
let cleanButton = $("#clean-btn")
let lastSearchResult = null;
let articles = null;
let searchValue = "";
let categorieChoisie = "0";
let userSales = null;
let userPurchase = null;
let userEncheres = null;
let userId = $("#user-filters").data("user");
let currentUserArticles = null;
let userData;

window.onload = function () {

    $.get(urlApi, function (data) {
        if (data.length == 0) {
            articleContainer.append("<p style='font-size: 30px'>Désolé, il n'y a pas d'articles en vente</p>");
        } else {

            articles = data;
            data.forEach(article => addArticleCard(article))
        }

    });

    if (userId) {
        $.get(urlApi + "/" + userId, function (data) {
            console.log(data);
            userSales = data.AtticlesVendus;
            userPurchase = data.AtticlesAchetes;
            userEncheres = data.articlesEncheresEnCours;
        })
    }


    searchInput.keyup((e) => {
        let result;
        searchValue = e.target.value.toLowerCase();
        let arrayToFilter = currentUserArticles != null ? currentUserArticles : articles;
        temp = filterByNom(arrayToFilter, searchValue);

        if (categorieChoisie != "0") {
            result = filterByCategorie(temp, categorieChoisie)
        } else result = temp;

        articleContainer.empty();

        if (result.length == 0) {
            articleContainer.append("<p style='font-size: 30px'>Désolé, nous n'avons pas trouvé des articles qui contiennent '" + e.target.value + "'</p>");
        } else {
            result.forEach(article => addArticleCard(article))
        }

    })

    selectCat.change((e) => {
        let result;
        let arrayToFilter = currentUserArticles != null ? currentUserArticles : articles;
        categorieChoisie = e.target.value;
        let temp = filterByCategorie(arrayToFilter, categorieChoisie);
        if (searchValue.length > 0) {
            result = filterByNom(temp, searchValue)
        } else result = temp;

        articleContainer.empty();

        if (result.length == 0) {
            articleContainer.append("<p style='font-size: 30px'>Désolé, nous n'avons pas trouvé des articles pour la catégorie demandée</p>");
        } else {
            result.forEach(article => addArticleCard(article));
            lastSearchResult = result;
        }
    })

    cleanButton.click(() => {
        articleContainer.empty();
        selectCat.prop('selectedIndex', 0);
        searchInput.val('');
        articles.forEach(article => addArticleCard(article))
    })

}


function filterByNom(articlesArray, search) {
    return articlesArray.filter((article) => {
        let nomArticle = article.nomArticle.toLowerCase();
        return nomArticle.includes(search);
    })
}


function filterByCategorie(articlesArray, categorieId) {
    if (categorieId == "0") return articlesArray;
    return articlesArray.filter((article) => {
        let numCat = article.categorieArticle.noCategorie;
        return numCat == categorieId;
    })
}

function filterCurrentEncheres() {
    showArticles(userEncheres)
    currentUserArticles = userEncheres;
}

function filterFinishedEncheres() {
    showArticles(userPurchase)
    currentUserArticles = userPurchase;
}

function activateSales() {
    toggleSalesButtons(false)
    togglePurchaseButtons(true)

    showArticles(userSales)
    currentUserArticles = userSales;
}

function activatePurchase() {
    toggleSalesButtons(true)
    togglePurchaseButtons(false)
    showAllArticlesToBuy();
    currentUserArticles = articles;
}

function togglePurchaseButtons(bool) {
    $("#buy-ckbox-group input").each(function () {
        $(this).prop("disabled", bool);
        $(this).prop("checked", false);
    })
}

function toggleSalesButtons(bool) {
    $("#sales-ckbox-group input").each(function () {
        $(this).prop("checked", false);
        $(this).prop("disabled", bool);
    })
}

function showArticles(array) {
    articleContainer.empty();
    array.forEach(article => {
        addArticleCard(article)
    })
}

function filterBySales() {
    let result = [];
    let allUnchecked = true;
    $("#sales-ckbox-group input").each(function () {
        allUnchecked *= !$(this).is(":checked");
        if ($(this).is(":checked")) {
            switch ($(this).val()) {
                case "current-sales" :
                    userSales.forEach((article) => {
                        if (article.etatVente == "En cours") {
                            result.push(article)
                        }
                    })
                    break;
                case "future-sales" :
                    userSales.forEach((article) => {
                        if (article.etatVente == "Non débutée") {
                            result.push(article)
                        }
                    })
                    break;
                case "finished-sales" :
                    userSales.forEach((article) => {
                        if (article.etatVente == "Terminé") {
                            result.push(article)
                        }
                    })
                    break;
            }
        }

    })
    if (allUnchecked) {
        result = userSales;
    }
    currentUserArticles = result;
    ;
    showArticles(result);
}

function addArticleCard(article) {
    let html = ` 
					<div class="col-md-6 mb-3">					
                        <div class="card"> 
                                            
                            <div class="card-body">
                             
                                <div class="row">
                                    <div class="col-md-4 d-flex flex-column align-items-center justify-content-around">                                                             
                                        <img class="card-img-top" src="./ressources/images/no-image.png" alt="Card image cap">
                                        <span class="badge badge-info">${article.categorieArticle.libelle}</span>
                                    </div>
                                    <div class="col-md-8">
                                        <a href="${projectPath}/vente/details?noArticle=${article.noArticle}"><h5 class="card-title">${article.nomArticle}</h5></a>
                                        <p class="card-text">${article.description}</p>
                                        <p>Prix:
                                        
                                        	${article.prixVente == 0 ? article.miseAPrix : article.prixVente}
                                          
                                        </p>
                                        <p>Fin de l'enchère :
                                            ${formatDate(article.dateFinEncheres)}
                                        </p>
                                        <p>Vendeur: <a href="${projectPath}/profil?pseudo=${article.vendeur.pseudo}" >${article.vendeur.pseudo}</a></p>
                                    </div>
                                </div>                                
                            </div>                       
                        </div>
                    </div>

                 `;

    articleContainer.append(html);
}

function showAllArticlesToBuy() {
    articleContainer.empty();
    articles.forEach(article => {
        addArticleCard(article)
    })
}

function formatDate(date) {
    return (date.dayOfMonth < 10 ? '0' : '')
        + date.dayOfMonth
        + "/"
        + (date.monthValue < 10 ? '0' : '')
        + date.monthValue
        + "/"
        + date.year
        + " "
        + (date.hour < 10 ? '0' : '')
        + date.hour
        + "H"
        + (date.minute < 10 ? '0' : '')
        + date.minute;
}
