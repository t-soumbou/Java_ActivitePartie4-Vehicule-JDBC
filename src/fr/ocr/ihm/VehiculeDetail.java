package fr.ocr.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import voiture.Vehicule;
import voiture.option.Option;

public class VehiculeDetail extends JDialog {

	private static final long serialVersionUID = 1L;
	private Vehicule vInfo;
	private boolean sendData;
	private JLabel nomLabel, marqueLabel, typeMoteurLabel, prixVehiculeLabel, prixTotalVehiculeLabel, opts;

	public VehiculeDetail(JFrame parent, String title, boolean modal, Vehicule vInfo) {
		super(parent, title, modal);
		this.setSize(700,450);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.vInfo = vInfo;
		this.initComponent();
	}

	public Vehicule showZDialog() {
		this.setSendData(false);
		this.setVisible(true);
		return this.vInfo;
	}

	private void initComponent() {

		// Le nom Vehicule
		JPanel panNom = new JPanel();
		panNom.setBackground(Color.white);
		panNom.setPreferredSize(new Dimension(300, 60));
		panNom.setBorder(BorderFactory.createTitledBorder("Nom du Véhicule"));
		nomLabel = new JLabel(vInfo.getNom());
		panNom.add(nomLabel);

		// Marque du vehicule
		JPanel panMarque = new JPanel();
		panMarque.setBackground(Color.white);
		panMarque.setPreferredSize(new Dimension(300, 60));
		panMarque.setBorder(BorderFactory.createTitledBorder("Marque du véhicule"));
		marqueLabel = new JLabel(vInfo.getMarque().getNom());
		panMarque.add(marqueLabel);

		// Type de moteur du véhicule
		JPanel panTypeMoteur = new JPanel();
		panTypeMoteur.setBackground(Color.white);
		panTypeMoteur.setBorder(BorderFactory.createTitledBorder("Type de moteur du véhicule"));
		panTypeMoteur.setPreferredSize(new Dimension(600, 60));
		typeMoteurLabel = new JLabel(vInfo.getMoteur().toString());
		panTypeMoteur.add(typeMoteurLabel);

		// Le prix du véhicule
		JPanel panPrixVehicule = new JPanel();
		panPrixVehicule.setBackground(Color.white);
		panPrixVehicule.setPreferredSize(new Dimension(300, 60));
		panPrixVehicule.setBorder(BorderFactory.createTitledBorder("Prix du Véhicule sans options"));
		prixVehiculeLabel = new JLabel(vInfo.getPrix()+" € ");
		panPrixVehicule.add(prixVehiculeLabel);

		// Les options 
		JPanel panOption = new JPanel();
		panOption.setBackground(Color.white);
		panOption.setBorder(BorderFactory.createTitledBorder("Options Disponibles"));
		panOption.setPreferredSize(new Dimension(650, 60));
		StringBuilder opt = new StringBuilder();
		for (Option vOption : vInfo.getOptions()) {
			opt.append(vOption.getNom()).append("( ").append(vOption.getPrix()).append(" € ) ");
		}
		opts = new JLabel(opt.toString());
		panOption.add(opts);
		
		
		JPanel panPrixTotal = new JPanel();
		panPrixTotal.setBackground(Color.green);
		panPrixTotal.setBorder(BorderFactory.createTitledBorder("Prix TOTAL du Véhicule"));
		panPrixTotal.setPreferredSize(new Dimension(650, 60));
		prixTotalVehiculeLabel = new JLabel(vInfo.getPrixTotal() +" € ");
		panPrixTotal.add(prixTotalVehiculeLabel);

		JPanel content = new JPanel();
		content.setBackground(Color.white);
		content.add(panNom);
		content.add(panMarque);
		content.add(panTypeMoteur);
		content.add(panPrixVehicule);
		content.add(panOption);
		content.add(panPrixTotal);

		JPanel control = new JPanel();
		this.getContentPane().add(content, BorderLayout.CENTER);
		this.getContentPane().add(control, BorderLayout.SOUTH);
	}

	public boolean isSendData() {
		return sendData;
	}

	public void setSendData(boolean sendData) {
		this.sendData = sendData;
	}
}