package org.eni.encheres.bll.encheres;

public class EnchereManagerSingl {
    private static EnchereManagerImpl instance;

    public static EnchereManagerImpl getInstance() {
        if(instance == null)
            instance = new EnchereManagerImpl();
        return instance;
    }
}
