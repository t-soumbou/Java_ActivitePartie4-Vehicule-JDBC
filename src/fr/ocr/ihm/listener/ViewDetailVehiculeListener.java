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
		Vous devez d�finir comment afficher le d�tail d'un v�hicule
		ceci dans un popup personnalis�e
		*/
		this.id = Integer.parseInt(table.getValueAt(row, 4).toString());
		Vehicule voit = vehiculeDAO.findByID(this.id);
		VehiculeDetail zd = new VehiculeDetail(null, "D�tail d'un v�hicule", true, voit);
		zd.showZDialog(); 
	}
}
