package org.eni.encheres.dal;

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import org.eni.encheres.BusinessException;
import org.eni.encheres.bo.*;


public class EncheresDAOImpl implements EncheresDAO{
	
	private final String SELECT_UTILISATEUR_BY_LOGIN = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, salt, credit, administrateur, is_active FROM utilisateurs WHERE pseudo = ? OR email = ?";
	
	private final String INSERT_UTILISATEUR = 
			"INSERT INTO utilisateurs "
			+ "(pseudo, nom, prenom, email, telephone, "
			+ "rue, code_postal, ville, "
			+ "mot_de_passe, credit, administrateur, salt) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private final String UPDATE_UTILISATEUR = "UPDATE utilisateurs SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ?, salt = ?, credit = ? WHERE no_utilisateur = ?";
	private final String DELETE_UTILISATEUR = "DELETE FROM utilisateurs WHERE no_utilisateur = ?";
	private final String SELECT_ALL_UTILISATEURS = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, credit, is_active, administrateur FROM utilisateurs";
	private final String UPDATE_UTILISATEUR_IS_ACTIVE = "UPDATE utilisateurs SET is_active = ? WHERE no_utilisateur = ?";
	private final String SELECT_ARTICLE_BY_ID = "SELECT a.no_acheteur, a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, a.etat, a.retraitEffectue, "
										 + "c.libelle,"
										 + "u.no_utilisateur, u.pseudo, "
										 + "r.rue, r.code_postal, r.ville "
										 + "FROM articles_vendus a "
										 + "INNER JOIN categories c ON c.no_categorie = a.no_categorie "
										 + "INNER JOIN utilisateurs u ON u.no_utilisateur = a.no_utilisateur "
										 + "INNER JOIN retraits r ON r.no_article = a.no_article "
										 + "WHERE a.no_article = ? ";	
	private final String SELECT_IMAGE = "SELECT path FROM images WHERE no_article= ?";
	private final String INSERT_IMAGE = "INSERT INTO images (path, no_article) VALUES(?, ?)";
	private final String UPDATE_IMAGE = "UPDATE images SET path = ? WHERE no_article= ?";
	private final String SELECT_ARTICLES_ACHETES_BY_USER = "SELECT u.pseudo, a.no_acheteur, a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, a.etat, a.retraitEffectue, "
												 + "c.libelle,"
												 + "r.rue, r.code_postal, r.ville "
												 + "FROM articles_vendus a "
												 + "INNER JOIN categories c ON c.no_categorie = a.no_categorie "
												 + "INNER JOIN retraits r ON r.no_article = a.no_article "
												 + "INNER JOIN utilisateurs u ON u.no_utilisateur = a.no_utilisateur "
												 + "WHERE a.no_acheteur = ? AND etat = 'Terminé' ";
	private final String SELECT_ARTICLES_VENDUS_BY_USER = "SELECT u.pseudo, a.no_acheteur, a.no_article, a.nom_article, a.description, "
												 + "a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, "
												 + "a.no_utilisateur, a.no_categorie, a.etat, a.retraitEffectue, "
												 + "c.libelle,"
												 + "r.rue, r.code_postal, r.ville "
												 + "FROM articles_vendus a "
												 + "INNER JOIN categories c ON c.no_categorie = a.no_categorie "
												 + "INNER JOIN retraits r ON r.no_article = a.no_article "
												 + "INNER JOIN utilisateurs u ON u.no_utilisateur = a.no_utilisateur "
												 + "WHERE a.no_utilisateur = ?";
	private final String SELECT_ENCHERES_FINIS = "SELECT a.no_acheteur, a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, a.etat, a.retraitEffectue, "
												+ "c.libelle,"
												 + "u.pseudo, "
												 + "r.rue, r.code_postal, r.ville "
												 + "FROM articles_vendus a "
												 + "INNER JOIN categories c ON c.no_categorie = a.no_categorie "
												 + "INNER JOIN utilisateurs u ON u.no_utilisateur = a.no_utilisateur "
												 + "INNER JOIN retraits r ON r.no_article = a.no_article "
												 + "WHERE date_fin_encheres < ? AND retraitEffectue = 0";
	private final String INSERT_ARTICLE = "INSERT INTO articles_vendus (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, etat,retraitEffectue) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String UPDATE_ARTICLE = "UPDATE articles_vendus SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, prix_initial = ?, prix_vente = ?, no_utilisateur = ?, no_categorie = ?, no_acheteur = ?, etat = ?, retraitEffectue = ? WHERE no_article = ?";
	private final String DELETE_ARTICLE = "DELETE FROM articles_vendus WHERE no_article = ?";


	private final String SELECT_ARTICLES = "SELECT a.no_acheteur, a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, a.etat, a.retraitEffectue, "
										 + "c.libelle, "
										 + "u.pseudo, "
										 + "r.rue, r.code_postal, r.ville "
										 + "FROM articles_vendus a "
										 + "INNER JOIN categories c ON c.no_categorie = a.no_categorie "
										 + "INNER JOIN utilisateurs u ON u.no_utilisateur = a.no_utilisateur "
										 + "INNER JOIN retraits r ON r.no_article = a.no_article "
										 + "WHERE a.date_debut_encheres <= ? AND a.date_fin_encheres >= ?";
	private final String INSERT_RETRAIT = "INSERT INTO retraits (no_article, rue, code_postal, ville) VALUES (?,?,?,?)";
	private final String UPDATE_RETRAIT = "UPDATE retraits SET rue = ?, code_postal = ?, ville = ? WHERE no_article = ?";
	private final String INSERT_ENCHERE = "INSERT INTO encheres (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?, ?, ?, ?)";
	private final String SELECT_ENCHERES_BY_ARTICLE = "SELECT e.no_utilisateur, e.date_enchere, e.montant_enchere, u.pseudo "
													+ "FROM encheres e "
													+ "INNER JOIN utilisateurs u ON u.no_utilisateur = e.no_utilisateur "
													+ "WHERE no_article = ?";
	private final String SELECT_ENCHERES_BY_USER = "SELECT e.no_utilisateur, e.no_article, e.date_enchere, e.montant_enchere, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_categorie, a.no_acheteur, a.etat, a.retraitEffectue, "
												 + "c.libelle, "
												 + "u.pseudo, "
												 + "r.rue, r.code_postal, r.ville "
												 + "FROM encheres e "
												 + "INNER JOIN utilisateurs u ON u.no_utilisateur = e.no_utilisateur "
												 + "INNER JOIN retraits r ON r.no_article = e.no_article "
												 + "INNER JOIN articles_vendus a ON a.no_article = e.no_article "
												 + "INNER JOIN categories c ON c.no_categorie = a.no_categorie "
												 + "WHERE e.no_utilisateur = ?";
	private final String SELECT_CATEGORIES = "SELECT no_categorie, libelle FROM categories";
	private final String DELETE_ENCHERES = "DELETE FROM encheres WHERE no_article = ?";
	private final String UPDATE_ENCHERE = "UPDATE encheres SET montant_enchere = ?, date_enchere = ? WHERE no_utilisateur = ? AND no_article = ?";
	private final String DELETE_ENCHERESBYUSER = "DELETE FROM encheres WHERE no_utilisateur = ?";

	
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
				utilisateur.setActive(rs.getBoolean("is_active"));
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
			PreparedStatement stmt = cnx.prepareStatement(INSERT_UTILISATEUR, 
										PreparedStatement.RETURN_GENERATED_KEYS);
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
			stmt.setInt(11, utilisateur.getCredit());
			stmt.setInt(12, utilisateur.getNoUtilisateur());
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
			ArticleVendu article = null;
			while(rs.next())
			{
				article = new ArticleVendu();
				article = mapArticle(rs, null);
				
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
			stmt.setTimestamp(3,  Timestamp.valueOf(article.getDateDebutEncheres()));
			stmt.setTimestamp(4,  Timestamp.valueOf(article.getDateFinEncheres()));
			stmt.setInt(5,  article.getMiseAPrix());
			stmt.setInt(6, 0);
			stmt.setInt(7, article.getVendeur().getNoUtilisateur());
			stmt.setInt(8, article.getCategorieArticle().getNoCategorie());
			stmt.setString(9, article.getEtatVente());
			stmt.setBoolean(10, article.isRetraitEffectue());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next())
			{
				article.setNoArticle(rs.getInt(1));
				article.getLieuRetrait().setArticle(article);
				article.getImage().setArticle(article);
				insertImage(article.getImage());
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
	
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(UPDATE_ARTICLE);
			stmt.setString(1,  article.getNomArticle());
			stmt.setString(2,  article.getDescription());
			stmt.setTimestamp(3,  Timestamp.valueOf(article.getDateDebutEncheres()));
			stmt.setTimestamp(4,  Timestamp.valueOf(article.getDateFinEncheres()));
			stmt.setInt(5,  article.getMiseAPrix());
			stmt.setInt(6, article.getPrixVente());
			stmt.setInt(7, article.getVendeur().getNoUtilisateur());
			stmt.setInt(8, article.getCategorieArticle().getNoCategorie());
			if(article.getAcheteur() == null) {
				stmt.setNull(9, java.sql.Types.INTEGER);
			}else {
				stmt.setInt(9, article.getAcheteur().getNoUtilisateur());
			}
			
			stmt.setString(10, article.getEtatVente());
			stmt.setBoolean(11, article.isRetraitEffectue());
			stmt.setInt(12, article.getNoArticle());
			stmt.executeUpdate();
			
			if(article.getImage() != null) {
				updateImageByArticle(article.getImage());
			}
			updateRetrait(article.getLieuRetrait(), article.getNoArticle());
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec rajout nouvel article");
		}
		System.out.println("Modification d'article reussie");
		return article;
	}

	@Override
	public void deleteArticle(int noArticle) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			deleteEncheres(noArticle);
			PreparedStatement stmt = cnx.prepareStatement(DELETE_ARTICLE);
			stmt.setInt(1, noArticle);
			stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec suppression d'article");
		}	
		System.out.println("Suppression d'article reussie");

	}
	
	@Override
	public ArrayList<ArticleVendu> selectArticlesAchetesByUser(Utilisateur utilisateur) throws BusinessException {
		ArrayList<ArticleVendu> articles = new ArrayList<>();
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(SELECT_ARTICLES_ACHETES_BY_USER);
			stmt.setInt(1, utilisateur.getNoUtilisateur());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				ArticleVendu article = mapArticle(rs, utilisateur);
				articles.add(article);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec selection des articles par utilisateur");
		}
		
		return articles;
	}
	
	
	@Override
	public ArrayList<ArticleVendu> selectArticlesVendusByUser(Utilisateur utilisateur) throws BusinessException {
		ArrayList<ArticleVendu> articles = new ArrayList<>();
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(SELECT_ARTICLES_VENDUS_BY_USER);
			stmt.setInt(1, utilisateur.getNoUtilisateur());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				ArticleVendu article = mapArticle(rs, utilisateur);
				articles.add(article);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec selection des articles par utilisateur");
		}
		
		return articles;
	}

	@Override
	public ArrayList<ArticleVendu> selectAllArticles() throws BusinessException {
		ArrayList<ArticleVendu> articles = new ArrayList<>();
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(SELECT_ARTICLES);
			stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
			stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				ArticleVendu article = mapArticle(rs, null);
				articles.add(article);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec selection de tous les articles");
		}
		return articles;
	}
	
	public ArticleVendu mapArticle(ResultSet rs, Utilisateur utilisateur) throws BusinessException, SQLException {
		ArticleVendu article = new ArticleVendu();
		article.setNoArticle(rs.getInt("no_article"));
		article.setNomArticle(rs.getString("nom_article"));
		article.setDescription(rs.getString("description"));
		article.setDateDebutEncheres(rs.getTimestamp("date_debut_encheres").toLocalDateTime());
		article.setDateFinEncheres(rs.getTimestamp("date_fin_encheres").toLocalDateTime());
		article.setMiseAPrix(rs.getInt("prix_initial"));
		article.setPrixVente(rs.getInt("prix_vente"));
		article.setCategorieArticle(new Categorie(rs.getInt("no_categorie"), rs.getString("libelle")));
		article.setImage(selectImageByArticle(article.getNoArticle()));
		
		if(utilisateur != null && rs.getInt("no_utilisateur") == utilisateur.getNoUtilisateur()) {
			utilisateur.setPseudo(rs.getString("pseudo"));
			article.setVendeur(utilisateur);
		} else {
			Utilisateur vendeur = new Utilisateur();
			
			vendeur.setNoUtilisateur(rs.getInt("no_utilisateur"));
			vendeur.setPseudo(rs.getString("pseudo"));
				
			article.setVendeur(vendeur);
		}
	
		if (utilisateur != null && rs.getInt("no_acheteur") == utilisateur.getNoUtilisateur()) {
			utilisateur.setPseudo(rs.getString("pseudo"));
			article.setAcheteur(utilisateur);
		}
		
		article.setEtatVente();
		article.setRetraitEffectue(rs.getBoolean("retraitEffectue"));
		
		Retrait retrait = new Retrait();
		retrait.setCode_postal(rs.getString("code_postal"));
		retrait.setRue(rs.getString("rue"));
		retrait.setVille(rs.getString("ville"));
		article.setLieuRetrait(retrait);
		
		ArrayList<Enchere> encheres = selectEncheresByNoArticle(article.getNoArticle());
		
		if(encheres.size() > 0) {
			Collections.sort(encheres);
			article.setPrixVente(encheres.get(0).getMontant_enchere());
			article.setAcheteur(encheres.get(0).getUtilisateur());
			article.setEncheres(encheres);
		}
		
		return article;
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
		System.out.println("Insertion retrait reussie");
		return retrait;
	}

	@Override
	public Retrait updateRetrait(Retrait retrait, int noArticle) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(UPDATE_RETRAIT);
			stmt.setString(1,  retrait.getRue());
			stmt.setString(2, retrait.getCode_postal());
			stmt.setString(3, retrait.getVille());
			stmt.setInt(4,  noArticle);
			stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec rajout lieu de retrait");
		}
		return retrait;
	}

	@Override
	public ArrayList<Categorie> selectAllCategories() throws BusinessException {
		ArrayList<Categorie> categories = new ArrayList<>();
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(SELECT_CATEGORIES);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Categorie categorie = new Categorie();
				categorie.setNoCategorie(rs.getInt(1));
				categorie.setLibelle(rs.getString(2));
				categories.add(categorie);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec selection des categories");
		}
		return categories;
	}

	@Override
	public Enchere insertEnchere(Enchere enchere, int noArticle, int noUtilisateur) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(INSERT_ENCHERE);
			stmt.setInt(1, noUtilisateur);
			stmt.setInt(2, noArticle);
			stmt.setTimestamp(3, Timestamp.valueOf(enchere.getDateEnchere()));
			stmt.setInt(4, enchere.getMontant_enchere());
			stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec rajout enchere");
		}	
		System.out.println("Insertion enchere reussie");
		return enchere;
	}

	@Override
	public ArrayList<Enchere> selectEncheresByNoArticle(int noArticle) throws BusinessException {
		ArrayList<Enchere> encheres = new ArrayList<>();
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(SELECT_ENCHERES_BY_ARTICLE);
			stmt.setInt(1, noArticle);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Enchere enchere = new Enchere();
				enchere.setDateEnchere(rs.getTimestamp("date_enchere").toLocalDateTime());
				enchere.setMontant_enchere(rs.getInt("montant_enchere"));
				Utilisateur utilisateur = new Utilisateur();
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));
				enchere.setUtilisateur(utilisateur);
				encheres.add(enchere);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec de selection d'encheres pour l'article no " + noArticle);
		}
		return encheres;
	}

	@Override
	public void deleteEncheres(int noArticle) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(DELETE_ENCHERES);
			stmt.setInt(1, noArticle);
			stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec suppression d'enchere");
		}	
		System.out.println("Suppression d'encheres reussie");
		
	}

	@Override
	public ArrayList<Enchere> selectEncheresByUser(Utilisateur utilisateur) throws BusinessException {
		ArrayList<Enchere> encheres = new ArrayList<>();
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(SELECT_ENCHERES_BY_USER);
			stmt.setInt(1, utilisateur.getNoUtilisateur());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Enchere enchere = new Enchere();
				enchere.setUtilisateur(utilisateur);
				enchere.setDateEnchere(rs.getTimestamp("date_enchere").toLocalDateTime());
				enchere.setMontant_enchere(rs.getInt("montant_enchere"));
				ArticleVendu article = mapArticle(rs, utilisateur);
				enchere.setArticle(article);
				encheres.add(enchere);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec de selection d'encheres pour l'utilisateur no " + utilisateur.getNoUtilisateur());
		}
		return encheres;
	}


	@Override
	public ArrayList<ArticleVendu> selectEncheresFinis() throws BusinessException {
		ArrayList<ArticleVendu> articles = new ArrayList<>();
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(SELECT_ENCHERES_FINIS);
			stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				ArticleVendu article = mapArticle(rs, null);
				
				articles.add(article);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec selection des articles par utilisateur");
		}
		System.out.println(articles.size());
		return articles;
	}

	@Override
	public Enchere updateEnchere(Enchere enchere) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(UPDATE_ENCHERE);
			stmt.setInt(1, enchere.getMontant_enchere());
			stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			stmt.setInt(3, enchere.getUtilisateur().getNoUtilisateur());
			stmt.setInt(4, enchere.getArticle().getNoArticle());
			stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec mis a jour enchere");
		}	
		System.out.println("Mise a jour d'un enchere reussie");
		return enchere;
	}

	@Override
	public ArrayList<Utilisateur> selectAllUtilisateurs() throws BusinessException {
		ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(SELECT_ALL_UTILISATEURS);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Utilisateur utilisateur = new Utilisateur();
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setNom(rs.getString("nom"));
				utilisateur.setPrenom(rs.getString("prenom"));
				utilisateur.setEmail(rs.getString("email"));
				utilisateur.setTelephone(rs.getString("telephone"));
				utilisateur.setRue(rs.getString("rue"));
				utilisateur.setCodePostal(rs.getString("code_postal"));
				utilisateur.setVille(rs.getString("ville"));
				utilisateur.setCredit(rs.getInt("credit"));
				utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
				utilisateur.setActive(rs.getBoolean("is_active"));
				utilisateurs.add(utilisateur);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec selection des utilisateurs");
		}
		
		return utilisateurs;
	}

	@Override
	public void desactiverUtilisateur(int noUtilisateur) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(UPDATE_UTILISATEUR_IS_ACTIVE);
			stmt.setBoolean(1, false);
			stmt.setInt(2,  noUtilisateur);
			stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec mis a jour is_active utilisateur");
		}	
	}
	
	@Override
	public void deleteEncheresByUser(Utilisateur utilisateur) throws BusinessException {
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = cnx.prepareStatement(DELETE_ENCHERESBYUSER);
            stmt.setInt(1, utilisateur.getNoUtilisateur());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("Echec suppression d'enchere par utilisateur");
        }
        System.out.println("Suppression d'encheres reussies");

    }
	
	public void insertImage(Image image) throws BusinessException {
		if(image == null) {
			throw new BusinessException("Image est null");
		}
		
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(INSERT_IMAGE, 
										PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, image.getPath());
			stmt.setInt(2, image.getArticle().getNoArticle());

			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next())
			{
				image.setId(rs.getInt(1));
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec rajout nouvel utilisateur");
		}
		
	}
	
	@Override
	public Image selectImageByArticle(int noArticle) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(SELECT_IMAGE);
			stmt.setInt(1, noArticle);
			ResultSet rs = stmt.executeQuery();
			Image image = null;
			while(rs.next())
			{
				image = new Image();
				image.setPath(rs.getString("path"));;
				
				
			}
			return image;
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec selection d'image by id");
		}	
	}
	
	@Override
	public void updateImageByArticle(Image image) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(UPDATE_IMAGE);
			stmt.setString(1, image.getPath());
			stmt.setInt(2, image.getArticle().getNoArticle());
			int result = stmt.executeUpdate();
			
			if(result == 0) {
				insertImage(image);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException("Echec mis à jour image by id");
		}
	}


}

