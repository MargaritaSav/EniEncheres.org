package org.eni.encheres.dal;

public class DAOFactory {
	
	public static EncheresDAO getInstace() {
		return new EncheresDAOImpl();
	}

}
