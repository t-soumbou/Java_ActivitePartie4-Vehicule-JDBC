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
	 * Méthode de création
	 * 
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean create(T obj);

	/**
	 * Méthode pour effacer
	 * 
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean delete(T obj);

	/**
	 * Méthode de mise à jour
	 * 
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean update(T obj);

	/**
	 * Méthode de recherche des informations with id
	 * 
	 * @param id
	 * @return T
	 */
	public abstract T findByID(int id);

	/**
	 * Méthode de recherche des informations with field with unique property
	 * 
	 * @param field
	 * @return T
	 */
	public abstract T findByUniqueField(String field);
	
	/**
	 * Méthode de recuperetous les éléments de la table
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