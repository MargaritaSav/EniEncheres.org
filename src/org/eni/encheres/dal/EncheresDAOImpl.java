package org.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.eni.encheres.BusinessException;
import org.eni.encheres.bo.Utilisateur;


public class EncheresDAOImpl implements EncheresDAO{
	
	private final String SELECT_UTILISATEUR_BY_LOGIN = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM utilisateurs WHERE pseudo = ?";
	private final String INSERT_UTILISATEUR = "INSERT INTO utilisateurs (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String UPDATE_UTILISATEUR = "UPDATE utilisateurs SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ? WHERE no_utilisateur = ?";
	private final String DELETE_UTILISATEUR = "DELETE FROM utilisateurs WHERE no_utilisateur = ?";
	

	@Override
	public Utilisateur selectUtilisateurByLogin(String login) {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement stmt = cnx.prepareStatement(SELECT_UTILISATEUR_BY_LOGIN);
			stmt.setString(1, login);
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
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ECHEC);
		}	
		
		return null;
	}

	@Override
	public void insertUtilisateur(Utilisateur utilisateur) throws BusinessException {
		if(utilisateur == null) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
			throw businessException;
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
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_UTILISATEUR_ECHEC);
		}
		
	}

	@Override
	public void updateUtilisateur(Utilisateur utilisateur) throws BusinessException {
		if(utilisateur == null) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
			throw businessException;
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
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_OBJET_ECHEC);
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
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.DELETE_ECHEC);
			throw businessException;
		}	
		
	}

}
