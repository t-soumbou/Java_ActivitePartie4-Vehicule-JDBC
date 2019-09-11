package fr.ocr.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import fr.ocr.sql.HsqldbConnection;

public abstract class DAO<T> {
	protected Connection connect = null;

	public DAO() {
		this.connect = HsqldbConnection.getInstance();
	}

	/**
	 * M�thode de cr�ation
	 * 
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean create(T obj);

	/**
	 * M�thode pour effacer
	 * 
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean delete(T obj);

	/**
	 * M�thode de mise � jour
	 * 
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean update(T obj);

	/**
	 * M�thode de recherche des informations with id
	 * 
	 * @param id
	 * @return T
	 */
	public abstract T findByID(int id);

	/**
	 * M�thode de recherche des informations with field with unique property
	 * 
	 * @param field
	 * @return T
	 */
	public abstract T findByUniqueField(String field);
	
	/**
	 * M�thode de recuperetous les �l�ments de la table
	 * @param field
	 * @return List<T>
	 */
	public abstract List<T> findAll();
	
	protected void rollbackConnection() {
		try {
			connect.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}