package fr.ocr.ihm.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import fr.ocr.dao.impl.VehiculeDAO;
import fr.ocr.sql.AbstractTableModelVehicule;
import voiture.Vehicule;

//Notre listener pour le bouton
public class ButtonListener implements ActionListener {
	protected int column, row, id;
	protected JTable table;
	private VehiculeDAO vehiculeDAO = new VehiculeDAO();


	public void setColumn(int col) {
		this.column = col;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public void actionPerformed(ActionEvent event) {
		/*
		Ici, il vous faut définir comment supprimer un véhicule
		et n'oubliez pas de supprimer toutes les options de ceui-ci...
		*/
		JOptionPane jop = new JOptionPane();			
		int option = jop.showConfirmDialog(null, "Voulez-vous supprimer ce Véhicule ?", "Effacer les données", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(option == JOptionPane.OK_OPTION) {
			this.id = Integer.parseInt(table.getValueAt(row, 4).toString());
			Vehicule voit = vehiculeDAO.findByID(id);
	       boolean  isDrop =  vehiculeDAO.delete(voit);
	       if(isDrop) {
		       ((AbstractTableModelVehicule) table.getModel()).removeRow(row); 
	       }
	        
		}
	}
}