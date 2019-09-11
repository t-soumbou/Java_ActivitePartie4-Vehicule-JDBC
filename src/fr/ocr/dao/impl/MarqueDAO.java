package fr.ocr.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.ocr.dao.DAO;
import voiture.Marque;

public class MarqueDAO extends DAO<Marque> {
	
	private static final String REQ_SEARCH_BY_UNIQUE_FIELD = "SELECT * FROM marque WHERE nom = ";
	private static final String REQ_SEARCH_BY_ID = "SELECT * FROM marque WHERE id = ";
	
	public MarqueDAO() {
		super();
	}

	@Override
	public boolean create(Marque marque) {
		Statement stmt =null;
		boolean res = false;
		try {
			stmt = connect.createStatement();
			String sql = "INSERT INTO MARQUE (id, nom) VALUES ("+marque.getId()+","+ "'"+marque.getNom()+"'" +")";
			res = stmt.executeUpdate(sql) >0;
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			rollbackConnection();
		}
	    return res;
	}

	@Override
	public boolean delete(Marque marque) {
		Statement stmt =null;
		boolean res = false;
		try { 
			stmt = connect.createStatement();
			String sql = "DELETE FROM MARQUE WHERE ID="+marque.getId();
			res = stmt.executeUpdate(sql) > 0;
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			rollbackConnection();
		}
	    return res;
	}

	@Override
	public boolean update(Marque marque) {
		Statement stmt = null;
		boolean res = false;
		try { 
			stmt = connect.createStatement();
			String sql = "UPDATE MARQUE SET ID="+marque.getId() +", NOM="+"'"+marque.getNom()+"'" +"WHERE ID="+marque.getId();
			res = stmt.executeUpdate(sql) > 0;
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			rollbackConnection();
		}
	    return res;
	}

	@Override
	public Marque findByUniqueField(String nom) {
		Marque marque = new Marque();
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery( REQ_SEARCH_BY_UNIQUE_FIELD+ "'"+nom+"'");
			if (result.first())
				marque = new Marque(result.getInt("id"), nom);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return marque;
	}

	@Override
	public Marque findByID(int id) {
		Marque marque = new Marque();
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery( REQ_SEARCH_BY_ID+ id);
			if (result.first())
				marque = new Marque(id, result.getString("nom"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return marque;
	}
	

	@Override
	public List<Marque> findAll(){
		List<Marque> marques = new ArrayList<Marque>();
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery( "SELECT * FROM MARQUE");
			while (result.next())
				marques.add (new Marque(result.getInt("id"), result.getString("nom")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return marques;
	}

}