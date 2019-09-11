package fr.ocr.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


import fr.ocr.dao.impl.MarqueDAO;
import fr.ocr.dao.impl.MoteurDAO;
import fr.ocr.dao.impl.OptionDAO;
import fr.ocr.dao.impl.VehiculeDAO;
import voiture.Marque;
import voiture.Vehicule;
import voiture.moteur.Moteur;
import voiture.option.Option;

public class VehiculeDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private Vehicule vInfo ;
	private boolean sendData;
	private JLabel nomLabel, marqueLabel, typeMoteurLabel, prixVehiculeLabel;
	private List<JCheckBoxMenuItem> jCheckBoxMenuItem = new ArrayList<>();
	private JComboBox<String> marque, typeMoteur;
	private JTextField nom, prix;
	private OptionDAO optDAO ;
	private VehiculeDAO voitDAO;
	private MarqueDAO marqueDAO ;
	private MoteurDAO moteurDAO ;
	private boolean isInserted ;

	public VehiculeDialog(JFrame parent, String title, boolean modal) {
		super(parent, title, modal);
		this.setSize(700,350);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.marqueDAO = new MarqueDAO();
		this.moteurDAO = new MoteurDAO();
		this.optDAO = new OptionDAO();
		this.voitDAO = new VehiculeDAO();
		this.vInfo = new Vehicule();
		this.setInserted(false);
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
		nom = new JTextField();
		nom.setPreferredSize(new Dimension(100, 25));
		panNom.setBorder(BorderFactory.createTitledBorder("Nom du Véhicule"));
		nomLabel = new JLabel("Saisir un nom :");
		panNom.add(nomLabel);
		panNom.add(nom);

		// Marque du vehicule
		JPanel panMarque = new JPanel();
		panMarque.setBackground(Color.white);
		panMarque.setPreferredSize(new Dimension(300, 60));
		panMarque.setBorder(BorderFactory.createTitledBorder("Marque du véhicule"));
		marque = new JComboBox<String>();
		List<Marque> marques = marqueDAO.findAll();
		for (Marque vMarque : marques) {
			marque.addItem(vMarque.getNom());
		}
		marqueLabel = new JLabel("Marque : ");
		panMarque.add(marqueLabel);
		panMarque.add(marque);

		// Type de moteur du véhicule
		JPanel panTypeMoteur = new JPanel();
		panTypeMoteur.setBackground(Color.white);
		panTypeMoteur.setBorder(BorderFactory.createTitledBorder("Type de moteur du véhicule"));
		panTypeMoteur.setPreferredSize(new Dimension(600, 60));
		typeMoteur = new JComboBox<String>();
		typeMoteur.setPreferredSize(new Dimension(200, 25));
		typeMoteurLabel = new JLabel("Marque : ");
		List<Moteur> moteurs = moteurDAO.findAll();
		for (Moteur vMoteur : moteurs) {
			typeMoteur.addItem(vMoteur.toString());
		}
		panTypeMoteur.add(typeMoteurLabel);
		panTypeMoteur.add(typeMoteur);

		// Le prix du véhicule
		JPanel panPrixVehicule = new JPanel();
		panPrixVehicule.setBackground(Color.white);
		panPrixVehicule.setPreferredSize(new Dimension(300, 60));
		panPrixVehicule.setBorder(BorderFactory.createTitledBorder("Prix du Véhicule"));
		prixVehiculeLabel = new JLabel("Prix : ");
		prix = new JTextField();
		prix.setPreferredSize(new Dimension(90, 25));
		panPrixVehicule.add(prixVehiculeLabel);
		panPrixVehicule.add(prix);

		// Les options 
		JPanel panOption = new JPanel();
		panOption.setBackground(Color.white);
		panOption.setBorder(BorderFactory.createTitledBorder("Options Disponibles"));
		panOption.setPreferredSize(new Dimension(650, 60));
		List<Option> options = optDAO.findAll();
		for (Option vOption : options) {
			JCheckBoxMenuItem vJCheckBoxMenuItem = new JCheckBoxMenuItem(vOption.getNom());
			jCheckBoxMenuItem.add(vJCheckBoxMenuItem);
			panOption.add(vJCheckBoxMenuItem);
		}

		JPanel content = new JPanel();
		content.setBackground(Color.white);
		content.add(panNom);
		content.add(panMarque);
		content.add(panTypeMoteur);
		content.add(panPrixVehicule);
		content.add(panOption);

		JPanel control = new JPanel();
		JButton okBouton = new JButton("OK");

		okBouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean okNom = Boolean.FALSE;
				boolean okPrix = Boolean.FALSE;
				if(checkData(nom.getText(), "nom")) {
					vInfo.setNom(nom.getText());
					okNom = true;
				}
				if(checkData(prix.getText(), "prix")) {
					Double vPrix = Double.valueOf(prix.getText());
					vInfo.setPrix(vPrix);
					okPrix = true;
				}
				vInfo.setListOptions(getOptions());
				vInfo.setMarque(getMarque());
				vInfo.setMoteur(getMoteur());
				if(okPrix && okNom) {
					vInfo.setId(voitDAO.getNextID());
					setInserted(voitDAO.create(vInfo));
				}
				setVisible(false);
			}

			public List<Option> getOptions() {
				ArrayList<Option> options = new ArrayList<>();
				for (JCheckBoxMenuItem jcheck : jCheckBoxMenuItem) {
					if (jcheck.isSelected()) {
						Option selectedOption = optDAO.findByUniqueField(jcheck.getText());
						options.add(selectedOption);
					}
				}
				return options;
			}
			
			public Marque getMarque() {
				String vmarque = (String)marque.getSelectedItem();
				return marqueDAO.findByUniqueField(vmarque);
			}
			
			public Moteur getMoteur() {
				String vtypeMoteur= (String)typeMoteur.getSelectedItem();
				return moteurDAO.findByUniqueField(vtypeMoteur);
			}
		});

		JButton cancelBouton = new JButton("Annuler");
		cancelBouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		control.add(okBouton);
		control.add(cancelBouton);

		this.getContentPane().add(content, BorderLayout.CENTER);
		this.getContentPane().add(control, BorderLayout.SOUTH);
	}

	public boolean isSendData() {
		return sendData;
	}

	public void setSendData(boolean sendData) {
		this.sendData = sendData;
	}
	
	private boolean checkData (String field, String champ) {
		boolean res = false;
		if(field.isEmpty() || field == null) {
			JOptionPane jop = new JOptionPane();
	        jop.showMessageDialog(null,"Le champ "+"'"+champ+"'" + " est obligatoire !" , "Champs Obligatoires manquants", JOptionPane.ERROR_MESSAGE);
		}else {
			res = true;	
		}
		return res;
	}

	public boolean isInserted() {
		return isInserted;
	}

	public void setInserted(boolean isInserted) {
		this.isInserted = isInserted;
	}
	

	public Vehicule getvInfo() {
		return vInfo;
	}

	public void setvInfo(Vehicule vInfo) {
		this.vInfo = vInfo;
	}

}