package org.eni.encheres.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import org.eni.encheres.BusinessException;
import org.eni.encheres.bo.ArticleVendu;
import org.eni.encheres.bo.Categorie;
import org.eni.encheres.bo.Retrait;
import org.eni.encheres.bo.Utilisateur;


public class EncheresDAOImpl implements EncheresDAO{
	
	private final String SELECT_UTILISATEUR_BY_LOGIN = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, salt, credit, administrateur FROM utilisateurs WHERE pseudo = ? OR email = ?";
	private final String INSERT_UTILISATEUR = "INSERT INTO utilisateurs (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur, salt) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String UPDATE_UTILISATEUR = "UPDATE utilisateurs SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ?, salt = ? WHERE no_utilisateur = ?";
	private final String DELETE_UTILISATEUR = "DELETE FROM utilisateurs WHERE no_utilisateur = ?";
	private final String SELECT_ARTICLE_BY_ID = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, "
										 + "c.libelle,"
										 + "u.pseudo, "
										 + "r.rue, r.code_postal, r.ville "
										 + "FROM articles_vendus a "
										 + "INNER JOIN categories c ON c.no_categorie = a.no_categorie "
										 + "INNER JOIN utilisateurs u ON u.no_utilisateur = a.no_utilisateur "
										 + "INNER JOIN retraits r ON r.no_article = a.no_article"
										 + "INNER JOIN encheres e ON e.no_article = a.no_article"
										 + "WHERE a.no_article = ?";	
	private final String INSERT_ARTICLE = "INSERT INTO articles_vendus (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
	private final String UPDATE_ARTICLE = "UPDATE articles_vendus SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, prix_initial = ?, prix_vente = ?, no_utilisateur = ?, no_categorie = ?  WHERE no_article = ?";
	private final String DELETE_ARTICLE = "DELETE FROM articles_vendus WHERE no_article = ?";
	private final String SELECT_ARTICLES = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, "
										 + "c.libelle,"
										 + "u.pseudo "
										 + "FROM articles_vendus a "
										 + "INNER JOIN categories c ON c.no_categorie = a.no_categorie "
										 + "INNER JOIN utilisateurs u ON u.no_utilisateur = a.no_utilisateur";
	private final String INSERT_RETRAIT = "INSERT INTO retraits (no_article, rue, code_postal, ville) VALUES (?,?,?,?)";
	private final String INSERT_ENCHERE = "INSERT INTO encheres (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?, ?, ?, ?)";

	
	@Override
	public Utilisateur selectUtilisateurByLogin(String login, String email) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(SELECT_UTILISATEUR_BY_LOGIN);
			stmt.setString(1, login);
			stmt.setString(2, email);

			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				Utilisateur utilisateur = new Utilisateur();
				utilisateur.setPseudo(login);
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.setVille(rs.getString("ville"));
				utilisateur.setMotDePasse(rs.getBytes("mot_de_passe"));
				utilisateur.setCredit(rs.getInt("credit"));
				utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
				utilisateur.setSalt(rs.getBytes("salt"));
				return utilisateur;
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec dans select_user_by_login");
		}	
		
		return null;
	}

	@Override
	public void insertUtilisateur(Utilisateur utilisateur) throws BusinessException {
		if(utilisateur == null) {
			throw new BusinessException("Utilisateur est null");
		}
		
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(INSERT_UTILISATEUR, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, utilisateur.getPseudo());
			stmt.setString(2, utilisateur.getNom());
			stmt.setString(3, utilisateur.getPrenom());
			stmt.setString(4, utilisateur.getEmail());
			stmt.setString(5, utilisateur.getTelephone());
			stmt.setString(6, utilisateur.getRue());
			stmt.setString(7, utilisateur.getCodePostal());
			stmt.setString(8, utilisateur.getVille());
			stmt.setBytes(9, utilisateur.getMotDePasse());
			stmt.setInt(10, utilisateur.getCredit());
			stmt.setBoolean(11, utilisateur.isAdministrateur());
			stmt.setBytes(12, utilisateur.getSalt());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next())
			{
				utilisateur.setNoUtilisateur(rs.getInt(1));
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec rajout nouvel utilisateur");
		}
		
	}

	@Override
	public void updateUtilisateur(Utilisateur utilisateur) throws BusinessException {
		if(utilisateur == null) {
			throw new BusinessException("Utilisateur est null");
		}
		
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(UPDATE_UTILISATEUR);
			stmt.setString(1, utilisateur.getPseudo());
			stmt.setString(2, utilisateur.getNom());
			stmt.setString(3, utilisateur.getPrenom());
			stmt.setString(4, utilisateur.getEmail());
			stmt.setString(5, utilisateur.getTelephone());
			stmt.setString(6, utilisateur.getRue());
			stmt.setString(7, utilisateur.getCodePostal());
			stmt.setString(8, utilisateur.getVille());
			stmt.setBytes(9, utilisateur.getMotDePasse());
			stmt.setBytes(10, utilisateur.getSalt());
			stmt.setInt(11, utilisateur.getNoUtilisateur());
			stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec modification d'utilisateur");
		}
		
	}

	@Override
	public void deleteUtilisateur(int noUtilisateur) throws BusinessException  {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(DELETE_UTILISATEUR);
			stmt.setInt(1, noUtilisateur);
			stmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec suppression d'utilisateur");
		}	
		
	}

	@Override
	public ArticleVendu selectArticleById(int noArticle) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(SELECT_ARTICLE_BY_ID);
			stmt.setInt(1, noArticle);
			ResultSet rs = stmt.executeQuery();
			ArticleVendu article = new ArticleVendu();
			while(rs.next())
			{
				article.setNoArticle(noArticle);
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				Categorie categorie = new Categorie();
				categorie.setNoCategorie(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
				article.setCategorieArticle(categorie);
				
			}
			return article;
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec selection d'article by id");
		}	
	}

	@Override
	public ArticleVendu insertArticle(ArticleVendu article) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1,  article.getNomArticle());
			stmt.setString(2,  article.getDescription());
			stmt.setDate(3,  Date.valueOf(article.getDateDebutEncheres()));
			stmt.setDate(4,  Date.valueOf(article.getDateFinEncheres()));
			stmt.setInt(5,  article.getMiseAPrix());
			stmt.setInt(6, 0);
			stmt.setInt(7, article.getVendeur().getNoUtilisateur());
			stmt.setInt(8, article.getCategorieArticle().getNoCategorie());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next())
			{
				article.setNoArticle(rs.getInt(1));
				insertRetrait(article.getLieuRetrait(), article.getNoArticle());
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec rajout nouvel article");
		}
		System.out.println("Insertion d'article reussie");
		return article;
	}

	@Override
	public ArticleVendu updateArticle(ArticleVendu article) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteArticle(int noArticle) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<ArticleVendu> selectAllArticles() throws BusinessException {
		ArrayList<ArticleVendu> articles = new ArrayList<>();
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(SELECT_ARTICLES);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				ArticleVendu article = new ArticleVendu();
				article.setNoArticle(rs.getInt("no_article"));
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				if(LocalDate.now().compareTo(article.getDateFinEncheres()) > 0) {
					article.setEtatVente("Terminé");
				} else {
					article.setEtatVente("En cours");
				}
				article.setCategorieArticle(new Categorie(rs.getInt("no_categorie"), rs.getString("libelle")));
				
				Utilisateur vendeur = new Utilisateur();
				vendeur.setPseudo(rs.getString("pseudo"));
				article.setVendeur(vendeur);
				
//				Retrait retrait = new Retrait();
//				retrait.setCode_postal(rs.getString("code_postal"));
//				retrait.setRue(rs.getString("rue"));
//				retrait.setVille(rs.getString("ville"));
//				article.setLieuRetrait(retrait);
				
				articles.add(article);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec selection de tous les articles");
		}
		return articles;
	}

	@Override
	public Retrait insertRetrait(Retrait retrait, int noArticle) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(INSERT_RETRAIT);
			stmt.setInt(1,  noArticle);
			stmt.setString(2,  retrait.getRue());
			stmt.setString(3, retrait.getCode_postal());
			stmt.setString(4, retrait.getVille());
			stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec rajout lieu de retrait");
		}
		return retrait;
	}

}
