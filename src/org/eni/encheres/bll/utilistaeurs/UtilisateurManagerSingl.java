package org.eni.encheres.bll.utilistaeurs;

import org.eni.encheres.bll.BusinessException;

public class UtilisateurManagerSingl {

    private static UtilisateursManagerImpl instance;

    public static UtilisateursManagerImpl getInstance() throws BusinessException {
        if(instance == null)
            instance = new UtilisateursManagerImpl();
        return instance;
    }
}
