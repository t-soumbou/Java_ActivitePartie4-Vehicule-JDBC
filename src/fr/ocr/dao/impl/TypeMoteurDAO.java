package fr.ocr.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.ocr.dao.DAO;
import voiture.moteur.TypeMoteur;

public class TypeMoteurDAO extends DAO<TypeMoteur> {

	private static final String REQ_SEARCH_BY_ID = "SELECT * FROM TYPE_MOTEUR WHERE id = ";

	public TypeMoteurDAO() {
		super();
	}

	@Override
	public boolean create(TypeMoteur typeMoteur) {
		Statement stmt = null;
		boolean res = false;
		try {
			stmt = connect.createStatement();
			String sql = "INSERT INTO TYPE_MOTEUR (ID, DESCRIPTION) VALUES (" + typeMoteur.getId() + ","+ "'"+typeMoteur.getNom()+"'" + ")";
			res = stmt.executeUpdate(sql) > 0;
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			rollbackConnection();
		}
		return res;
	}

	@Override
	public boolean delete(TypeMoteur typeMoteur) {
		Statement stmt = null;
		boolean res = false;
		try {
			stmt = connect.createStatement();
			String sql = "DELETE FROM TYPE_MOTEUR WHERE ID=" + typeMoteur.getId();
			res = stmt.executeUpdate(sql) > 0;
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			rollbackConnection();
		}
		return res;
	}

	@Override
	public boolean update(TypeMoteur typeMoteur) {
		Statement stmt = null;
		boolean res = false;
		try {
			stmt = connect.createStatement();
			String sql = "UPDATE TYPE_MOTEUR SET ID =" + typeMoteur.getId() + ", DESCRIPTION=" + "'"+typeMoteur.getNom()+"'"+ "WHERE ID=" + typeMoteur.getId();
			res = stmt.executeUpdate(sql) > 0;
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			rollbackConnection();
		}
		return res;
	}

	@Override
	public TypeMoteur findByUniqueField(String description) {
		TypeMoteur typeMoteur = null;
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM TYPE_MOTEUR WHERE DESCRIPTION = " +"'"+description+"'");
			if (result.first())
				typeMoteur = new TypeMoteur(result.getInt("id"), description);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return typeMoteur;
	}

	@Override
	public TypeMoteur findByID(int id) {
		TypeMoteur typeMoteur = null;
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery(REQ_SEARCH_BY_ID + id);
			if (result.first())
				typeMoteur = new TypeMoteur(id, result.getString("DESCRIPTION"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return typeMoteur;
	}
	

	@Override
	public List<TypeMoteur> findAll(){
		List<TypeMoteur> types = new ArrayList<TypeMoteur>();
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery( "SELECT * FROM TYPE_MOTEUR");
			while (result.next())
				types.add (new TypeMoteur(result.getInt("id"), result.getString("DESCRIPTION")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return types;
	}
}
