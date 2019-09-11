package fr.ocr.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.ocr.dao.DAO;
import voiture.option.Option;

public class OptionDAO extends DAO<Option>{
	private static final String REQ_SEARCH_BY_UNIQUE_FIELD = "SELECT * FROM option WHERE DESCRIPTION = ";
	private static final String REQ_SEARCH_BY_ID = "SELECT * FROM OPTION WHERE id = ";
	
	public OptionDAO() {
		super();
	}

	@Override
	public boolean create(Option option) {
		Statement stmt =null;
		boolean res = false;
		try {
			stmt = connect.createStatement();
			String sql = "INSERT INTO OPTION VALUES ("+option.getId()+","+ option.getNom() +","+option.getPrix()+")";
			res = stmt.executeUpdate(sql) > 0;
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			rollbackConnection();
		}
	    return res;
	}

	@Override
	public boolean delete(Option option) {
		Statement stmt =null;
		boolean res = false;
		try { 
			stmt = connect.createStatement();
			String sql = "DELETE FROM OPTION WHERE ID="+option.getId();
			res = stmt.executeUpdate(sql) > 0;
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			rollbackConnection();
		}
	    return res;
	}

	@Override
	public boolean update(Option option) {
		Statement stmt = null;
		boolean res = false;
		try { 
			stmt = connect.createStatement();
			String sql = "UPDATE OPTION SET ID="+option.getId() +", DESCRIPTION="+option.getNom() +", PRIX="+ option.getPrix()+"WHERE ID="+option.getId();
			res = stmt.executeUpdate(sql) > 0;
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			rollbackConnection();
		}
	    return res;
	}

	@Override
	public Option findByUniqueField(String description) {
		Option option = null;
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery( REQ_SEARCH_BY_UNIQUE_FIELD+ "'"+description+"'");
			if (result.first())
				option = new Option(result.getInt("ID"), description, result.getDouble("PRIX"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return option;
	}

	@Override
	public Option findByID(int id) {
		Option option = new Option();
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery( REQ_SEARCH_BY_ID+ id);
			if (result.first())
				option = new Option(id, result.getString("DESCRIPTION"),result.getDouble("PRIX"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return option;
	}
	

	@Override
	public List<Option> findAll(){
		List<Option> options = new ArrayList<Option>();
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery( "SELECT * FROM OPTION");
			while (result.next())
				options.add (new Option(result.getInt("id"), result.getString("DESCRIPTION"),result.getDouble("PRIX")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return options;
	}
}
