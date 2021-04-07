package org.eni.encheres.bll.utilistaeurs;

public class UtilisateurManagerSingl {

    private static UtilisateursManagerImpl instance;

    public static UtilisateursManagerImpl getInstance() throws Exception {
        if(instance == null)
            instance = new UtilisateursManagerImpl();
        return instance;
    }
}
