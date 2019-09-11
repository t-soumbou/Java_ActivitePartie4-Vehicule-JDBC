package fr.ocr.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.ocr.dao.DAO;
import voiture.Marque;
import voiture.Vehicule;
import voiture.moteur.Moteur;
import voiture.option.Option;

public class VehiculeDAO extends DAO<Vehicule>{
	
	private static final String REQ_SEARCH_BY_ID = "SELECT * FROM Vehicule WHERE id = ";
	private MoteurDAO moteurDAO;
	private MarqueDAO marqueDAO ;
	private OptionDAO opt ;

	public VehiculeDAO() {
		super();
		this.marqueDAO = new MarqueDAO();
		this.moteurDAO = new MoteurDAO();
		this.opt= new OptionDAO();
	}

	@Override
	public boolean create(Vehicule vehicule) { 
		Statement stmt = null;
		boolean res = false;
		try {
			stmt = connect.createStatement();
			res = addVehicule(vehicule);
			if(res) {
				for (Option option : vehicule.getOptions()) {
					String req = "INSERT INTO vehicule_option VALUES ("+vehicule.getId() + ","+option.getId() +")";
					res = stmt.executeUpdate(req) > 0;
					connect.commit();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollbackConnection();
		}
		return res;
	}

	@Override
	public boolean delete(Vehicule vehicule) {
		Statement stmt = null;
		
		boolean res = vehicule.getOptions().size()>0 ? Boolean.FALSE : Boolean.TRUE; 
		try {
			stmt = connect.createStatement();
			for (Option option : vehicule.getOptions()) {
				String sql = "DELETE FROM vehicule_option WHERE ID_VEHICULE=" + vehicule.getId() +" AND ID_OPTION="+option.getId();
				res = stmt.executeUpdate(sql) > 0;
				connect.commit();
			}
			if(res) {
			   res = deleteVehicule(vehicule);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollbackConnection();
		}
		return res;
	}

	@Override
	public boolean update(Vehicule vehicule) {
		Statement stmt = null;
		boolean res = Boolean.FALSE;
		try {
			stmt = connect.createStatement();
			res = updateVehicule(stmt, vehicule);
		} catch (SQLException e) {
			e.printStackTrace();
			rollbackConnection();
		}
		return res;
	}

	@Override
	public Vehicule findByUniqueField(String field) {
		return null;
	}

	@Override
	public Vehicule findByID(int id) {
		Vehicule vehicule = new Vehicule();
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery(REQ_SEARCH_BY_ID + id);
			if (result.first()) {
				List<Option> options = getOptionsIDByVehicule(id) ; 
				Marque marque = marqueDAO.findByID(result.getInt("marque"));
				Moteur mot = moteurDAO.findByID(result.getInt("MOTEUR"));
				vehicule = new Vehicule(id, result.getString("NOM"),marque,mot,options,result.getDouble("PRIX"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vehicule;
	}
	
	
	@Override
	public List<Vehicule> findAll(){
		List<Vehicule> vehicules = new ArrayList<Vehicule>();
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery( "SELECT * FROM Vehicule");
			while (result.next()) {
				List<Option> options = getOptionsIDByVehicule(result.getInt("id")); 
				Marque marque  = marqueDAO.findByID(result.getInt("MARQUE"));
				Moteur mot = moteurDAO.findByID(Integer.valueOf(result.getString("MOTEUR")) );
				vehicules.add(new Vehicule(result.getInt("id"), result.getString("NOM"),marque,mot,options,result.getDouble("PRIX")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vehicules;
	}
	
	
	private boolean addVehicule (Vehicule vehicule ) {
		boolean res = false;
		Statement stmt =null; 
		try {
			stmt = connect.createStatement();
			String sql = "INSERT INTO Vehicule VALUES ("+ vehicule.getMarque().getId() + "," + vehicule.getMoteur().getId() 
					                                  + ","+vehicule.getPrix()+","+"'"+vehicule.getNom()+"'"+","+vehicule.getId() +")";
			res = stmt.executeUpdate(sql) > 0;
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			rollbackConnection();
		}
		return res;
	}
	
	private boolean deleteVehicule(Vehicule vehicule) {
		boolean res = false; 
		Statement stmt = null;
		try {
			stmt = connect.createStatement();
			String sql = "DELETE FROM Vehicule WHERE ID=" + vehicule.getId();
			res = stmt.executeUpdate(sql) > 0;
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			rollbackConnection();
		}
		return res;
	}
	
	private boolean updateVehicule(Statement stmt, Vehicule vehicule) {
		boolean res = false;
		try {
			stmt = connect.createStatement();
			String sql = "UPDATE Vehicule SET ID =" + vehicule.getId() + ", NOM=" + "'"+vehicule.getNom()+"'" + ", PRIX="
					+ vehicule.getPrix() + ", marque=" + vehicule.getMarque().getId()+", MOTEUR="+vehicule.getMoteur().getId() + " WHERE ID=" + vehicule.getId();
			res = stmt.executeUpdate(sql) > 0;
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			rollbackConnection();
		}
		return res;
	}
	
	private List<Option> getOptionsIDByVehicule(int id_vehicule){
		List<Option> options = new ArrayList<>();
		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT ID_OPTION FROM vehicule_option WHERE ID_VEHICULE =" + id_vehicule);
			while (result.next()) {
				options.add(opt.findByID(result.getInt("id_option")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return options;
	}
	
	public Integer getNextID() {
		Integer nextAvailableID = null;
		try {
			ResultSet nextID = connect.prepareStatement("CALL NEXT VALUE FOR seq_vehicule_id").executeQuery();
			 if (nextID.next()) {
				 nextAvailableID = nextID.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nextAvailableID;		
	}

}
