package org.eni.encheres.api;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eni.encheres.BusinessException;
import org.eni.encheres.bll.encheres.EnchereManager;
import org.eni.encheres.bll.encheres.EnchereManagerSingl;
import org.eni.encheres.bll.utilistaeurs.UtilisateurManager;
import org.eni.encheres.bll.utilistaeurs.UtilisateurManagerSingl;
import org.eni.encheres.bo.ArticleVendu;
import org.eni.encheres.bo.Utilisateur;


@Path("/articles")
public class ArtclesAPI {
	private EnchereManager eManager = EnchereManagerSingl.getInstance();
	private UtilisateurManager uManager = UtilisateurManagerSingl.getInstance();
	
	@GET
	public List<ArticleVendu> getArticles(){
		try {
			return eManager.getArticles();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/{pseudo}")
	public Utilisateur getUserWithArticles(@PathParam("pseudo") String pseudo) {
		
		try {
			return uManager.getUtilisateurByPseudo(pseudo);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
	}
	
	
}
