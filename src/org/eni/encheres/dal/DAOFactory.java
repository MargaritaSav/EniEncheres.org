package org.eni.encheres.dal;

public class DAOFactory {
	
	public static EncheresDAO getInstance() {
		return new EncheresDAOImpl();
	}

}
