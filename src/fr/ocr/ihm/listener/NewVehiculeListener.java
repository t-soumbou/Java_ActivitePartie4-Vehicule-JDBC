package fr.ocr.ihm.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import fr.ocr.ihm.VehiculeDialog;

public class NewVehiculeListener implements ActionListener {

	private JFrame frame;

	public NewVehiculeListener(JFrame f) {
		frame = f;
	}

	public void actionPerformed(ActionEvent e) {
		VehiculeDialog zd = new VehiculeDialog(null, "Ajouter un nouveau véhicule", true);
		 zd.showZDialog(); 
	}
}
