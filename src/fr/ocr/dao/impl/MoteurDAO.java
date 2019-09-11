package fr.ocr.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.ocr.dao.DAO;
import voiture.moteur.Moteur;
import voiture.moteur.TypeMoteur;

public class MoteurDAO extends DAO<Moteur> {

	private static final String REQ_SEARCH_BY_ID = "SELECT * FROM MOTEUR WHERE id = ";
	private TypeMoteurDAO typeMoteurDAO ;

	public MoteurDAO() {
		super();
		typeMoteurDAO = new TypeMoteurDAO();
	}

	@Override
	public boolean create(Moteur moteur) {
		Statement stmt = null;
		boolean res = false;
		try {
			stmt = connect.createStatement();
			String sql = "INSERT INTO MOTEUR VALUES (" + moteur.getId() + "," +moteur.getCylindre()+ ","
					+ moteur.getType().getId()  + "," +   moteur.getPrix() + ")";
			res = stmt.executeUpdate(sql) > 0;
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			rollbackConnection();
		}
		return res;
	}

	@Override
	public boolean delete(Moteur moteur) {
		Statement stmt = null;
		boolean res = false;
		try {
			stmt = connect.createStatement();
			String sql = "DELETE FROM MOTEUR WHERE ID=" + moteur.getId();
			res = stmt.executeUpdate(sql) > 0;
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			rollbackConnection();
		}
		return res;
	}

	@Override
	public boolean update(Moteur moteur) {
		Statement stmt = null;
		boolean res = false;
		try {
			stmt = connect.createStatement();
			String sql = "UPDATE MOTEUR SET ID =" + moteur.getId() + ", MOTEUR=" + moteur.getType().getId() + ", PRIX="
					+ moteur.getPrix() + ", CYLINDRE=" + moteur.getCylindre() + "WHERE ID=" + moteur.getId();
			res = stmt.executeUpdate(sql) > 0;
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			rollbackConnection();
		}
		return res;
	}

	@Override
	public Moteur findByUniqueField(String field) {
		String type = field.split(" ",2)[0];
		String cylindre = field.split(" ",2)[1];
        Moteur moteur = null;
        TypeMoteur typeMoteur = typeMoteurDAO.findByUniqueField(type);
		
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM MOTEUR WHERE CYLINDRE = " + "'"+cylindre+"'" + " AND MOTEUR =" +typeMoteur.getId());
			if (result.first())
				moteur = new Moteur(result.getInt("id"), typeMoteur,result.getString("cylindre"),result.getDouble("prix"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return moteur;
	}

	@Override
	public Moteur findByID(int id) {
		Moteur moteur = null;
		
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery(REQ_SEARCH_BY_ID + id);
			if (result.first())
				moteur = new Moteur(id, typeMoteurDAO.findByID(result.getInt("MOTEUR")),result.getString("cylindre"),result.getDouble("prix"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return moteur;
	}
	

	@Override
	public List<Moteur> findAll(){
		List<Moteur> moteurs = new ArrayList<Moteur>();
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery( "SELECT * FROM MOTEUR");
			while (result.next())
				moteurs.add (new Moteur(result.getInt("id"), typeMoteurDAO.findByID(result.getInt("MOTEUR")),result.getString("cylindre"),result.getDouble("prix")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return moteurs;
	}

}
