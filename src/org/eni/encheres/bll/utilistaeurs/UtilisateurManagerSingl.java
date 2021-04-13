package org.eni.encheres.bll.utilistaeurs;

public class UtilisateurManagerSingl {

    private static UtilisateursManagerImpl instance;

    public static UtilisateursManagerImpl getInstance() {
        if(instance == null)
            instance = new UtilisateursManagerImpl();
        return instance;
    }
}
