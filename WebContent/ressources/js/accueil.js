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
let userPseudo = $("#user-filters").data("user");
let userData;

window.onload = function(){
		
	$.get(urlApi, function(data){
		if(data.length == 0){
			articleContainer.append("<p style='font-size: 30px'>Désolé, il n'y a pas d'articles en vente</p>");
		} else{
			
			articles = data;
			data.forEach(article=>addArticleCard(article))
		}
			
	});
	
	$.get(urlApi + "/" + userPseudo, function(data){
		console.log(data)	
	})
	
	searchInput.keyup((e)=>{
		let result;
		searchValue = e.target.value.toLowerCase();
		temp = filterByNom(articles, searchValue);

		if(categorieChoisie != "0"){
			console.log("verifSearchInput")
			result = filterByCategorie(temp, categorieChoisie)
		} else result = temp;
		
		articleContainer.empty();
		
		if(result.length == 0){
			articleContainer.append("<p style='font-size: 30px'>Désolé, nous n'avons pas trouvé des articles qui contiennent '" + e.target.value  +"'</p>");
		} else {
			result.forEach(article=>addArticleCard(article))
		}	
		
	})
	
	selectCat.change((e)=>{
		let result;
		categorieChoisie = e.target.value;
		let temp = filterByCategorie(articles, categorieChoisie);
		if (searchValue.length > 0){
			result = filterByNom(temp, searchValue)
		} else result = temp;		
		
		articleContainer.empty();
		
		if(result.length == 0){
			articleContainer.append("<p style='font-size: 30px'>Désolé, nous n'avons pas trouvé des articles pour la catégorie demandée</p>");
		} else {
			result.forEach(article=>addArticleCard(article));
			lastSearchResult = result;
		}
	})
	
	cleanButton.click(()=>{
		articleContainer.empty();
		selectCat.prop('selectedIndex', 0);
		searchInput.val('');
		articles.forEach(article=>addArticleCard(article))
	})
	
} 


function filterByNom(articlesArray, search){
	return articlesArray.filter((article) => {
		let nomArticle = article.nomArticle.toLowerCase();
		return nomArticle.includes(search);
	})
}


function filterByCategorie(articlesArray, categorieId){
	if(categorieId == "0") return articlesArray;
	return articlesArray.filter((article) => {
		let numCat = article.categorieArticle.noCategorie;
		return numCat == categorieId;
	})
}



function addArticleCard(article){
	let html = `<div class="col-md-6 mb-3">
                        <div class="card">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-4 d-flex flex-column align-items-center justify-content-around">
                                    
                                        <img class="card-img-top" src="./ressources/images/no-image.png" alt="Card image cap">
                                        <span class="badge badge-info">${article.categorieArticle.libelle}</span>
                                    </div>
                                    <div class="col-md-8">
                                        <h5 class="card-title">${article.nomArticle}</h5>
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

function formatDate(date){
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

function setUserSales(sales){
	userSales = sales;
	console.log(sales)
	
}