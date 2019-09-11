package fr.ocr.ihm.listener;

import java.awt.event.ActionEvent;


import fr.ocr.dao.impl.VehiculeDAO;
import fr.ocr.ihm.VehiculeDetail;
import voiture.Vehicule;

public class ViewDetailVehiculeListener extends ButtonListener {
	private Integer id;
	private VehiculeDAO vehiculeDAO = new VehiculeDAO();
	
	public ViewDetailVehiculeListener() {
	}
	
	public ViewDetailVehiculeListener(Integer id) {
		this.id=id;
	}
	
	

	public void actionPerformed(ActionEvent e) {		
		/*
		Vous devez définir comment afficher le détail d'un véhicule
		ceci dans un popup personnalisée
		*/
		this.id = Integer.parseInt(table.getValueAt(row, 4).toString());
		Vehicule voit = vehiculeDAO.findByID(this.id);
		VehiculeDetail zd = new VehiculeDetail(null, "Détail d'un véhicule", true, voit);
		zd.showZDialog(); 
	}
}
