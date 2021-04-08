package org.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.eni.encheres.BusinessException;
import org.eni.encheres.bo.ArticleVendu;
import org.eni.encheres.bo.Utilisateur;


public class EncheresDAOImpl implements EncheresDAO{
	
	private final String SELECT_UTILISATEUR_BY_LOGIN = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM utilisateurs WHERE pseudo = ? OR email = ?";
	private final String INSERT_UTILISATEUR = "INSERT INTO utilisateurs (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String UPDATE_UTILISATEUR = "UPDATE utilisateurs SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ? WHERE no_utilisateur = ?";
	private final String DELETE_UTILISATEUR = "DELETE FROM utilisateurs WHERE no_utilisateur = ?";
	private final String SELECT_ARTICLE_BY_ID = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie FROM articles_vendus WHERE no_article = ?";
	private final String INSERT_ARTICLE = "INSERT INTO articles_vendus (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
	private final String UPDATE_ARTICLE = "UPDATE articles_vendus SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, prix_initial = ?, prix_vente = ?, no_utilisateur = ?, no_categorie = ?  WHERE no_article = ?";
	private final String DELETE_ARTICLE = "DELETE FROM articles_vendus WHERE no_article = ?";
	private final String SELECT_ARTICLES = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie FROM articles_vendus";

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
				utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
				utilisateur.setCredit(rs.getInt("credit"));
				utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
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
			stmt.setString(9, utilisateur.getMotDePasse());
			stmt.setInt(10, utilisateur.getCredit());
			stmt.setBoolean(11, utilisateur.isAdministrateur());
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
			stmt.setString(9, utilisateur.getMotDePasse());
			stmt.setInt(10, utilisateur.getNoUtilisateur());
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
			
			while(rs.next())
			{
				ArticleVendu article = new ArticleVendu();
				article.setNoArticle(noArticle);
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
//				article.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
//				article.setDateFinEncheres(rs.getDate("date_fin_encheres"));
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
//				article.setCategorieArticle(rs.getInt("no_categorie"));
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec selection d'article by id");
		}	
		
		return null;
	}

	@Override
	public ArticleVendu insertArticle(ArticleVendu article) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

}
