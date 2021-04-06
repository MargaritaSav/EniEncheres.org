package org.eni.encheres.dal;

public class DAOFactory {
	
	public static ListDAO getInstace() {
		return new ListDAOImpl();
	}

}
