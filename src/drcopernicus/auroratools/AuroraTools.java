package drcopernicus.auroratools;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.ship.Ship;
import drcopernicus.auroratools.ship.ShipComponent;
import drcopernicus.auroratools.ship.ShipComponentEngine;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AuroraTools extends JFrame implements ActionListener {
	public static Settings settings;
//	public SpecialPanel[] components;
    public ShipComponent[] components;
	public JButton generateButton;
	public JButton refreshButton;
	public JButton dispForwardButton;
	public JButton dispBackwardButton;
	public JTextArea displayShipField;
	public JLabel dispLabel;
	private int shipsDisplayed;
	private int shipsGenerated;
	public JLabel shipsGeneratedLabel;
	
	private static final int MAX_SHIPS_DISPLAYED = 1000;
	private Ship[] shipsToDisplay;
	private int displayThisShip;
	
	private JPanel megaPanel;
	
	public AuroraTools() {
		megaPanel = new JPanel();
		megaPanel.setLayout(new BoxLayout(megaPanel,BoxLayout.PAGE_AXIS));
		JPanel flavPanel = new JPanel(new GridLayout(0,1));
		JPanel techPanel = new JPanel(new GridLayout(0,1));
		JPanel specPanel = new JPanel(new GridLayout(0,1));
		JPanel reqsPanel = new JPanel(new GridLayout(0,1));
		JPanel dispPanel = new JPanel();
		JPanel dispSuperButtons = new JPanel();
		JPanel dispButtons = new JPanel();
		dispLabel = new JLabel("Displaying ship #0");
		dispForwardButton = new JButton("+");
		dispBackwardButton = new JButton("-");
		dispForwardButton.addActionListener(this);
		dispBackwardButton.addActionListener(this);
		dispSuperButtons.add(dispLabel);
		dispButtons.add(dispForwardButton);
		dispButtons.add(dispBackwardButton);
		displayShipField = new JTextArea();
		dispSuperButtons.add(dispButtons);
		dispPanel.add(dispSuperButtons);
		dispPanel.add(displayShipField,BorderLayout.CENTER);
		JTabbedPane tabbedTechPane = new JTabbedPane();
		//adding individual tech panels to the parent tech panel, for organizational purposes
		techPanel.add(tabbedTechPane);
//		tabbedTechPane.addTab("Biology / Genetics", techBioPanel);
//		tabbedTechPane.addTab("Construction / Production", techConPanel);
//		tabbedTechPane.addTab("Defensive Systems", techDefPanel);
//		tabbedTechPane.addTab("Energy Weapons", techEnePanel);
//		tabbedTechPane.addTab("Logistics / Ground Combat", techLogPanel);
//		tabbedTechPane.addTab("Missiles / Kinetic Weapons", techMisPanel);
//		tabbedTechPane.addTab("Power and Propulsion", techPowPanel);
//		tabbedTechPane.addTab("Sensors and Fire Control", techSenPanel);
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Spec", specPanel);
//		tabbedPane.addTab("Reqs", reqsPanel);
		tabbedPane.addTab("Disp", dispPanel);
		
		megaPanel.add(tabbedPane);
		settings = new Settings();
        components = new ShipComponent[]{new ShipComponentEngine()};
        for (ShipComponent component : components) {
            specPanel.add(component.getPanel());
        }
		refreshButton = new JButton("Refresh");
		generateButton = new JButton("Generate ships!");
		shipsGeneratedLabel = new JLabel("0 generated");
		refreshButton.addActionListener(this);
		generateButton.addActionListener(this);
		JPanel littleButtonPanel = new JPanel();
		littleButtonPanel.add(refreshButton);
		littleButtonPanel.add(generateButton);
		littleButtonPanel.add(shipsGeneratedLabel);
		megaPanel.add(littleButtonPanel);
		this.add(megaPanel);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new AuroraTools();
	}

	public void actionPerformed(ActionEvent a) {
		if (a.getSource() == generateButton) {
			for (int i = 0; i < components.length; i++) {
				components[i].save();
			}
			System.out.println("GENERATING SHIPS");
			shipsDisplayed = 0;
			shipsGenerated = 0;
			shipsToDisplay = new Ship[MAX_SHIPS_DISPLAYED];
			displayThisShip = 0;
            generate();
			updateDisplayShipField();
			System.out.println("SHIPS GENERATED");
			for (int i = 0; i < components.length; i++) {
				components[i].updateText();
			}
			shipsGeneratedLabel.setText(shipsGenerated+" generated");
		} else if (a.getSource() == refreshButton) {
			for (int i = 0; i < components.length; i++) {
				components[i].save();
			}
			int numOfShips = 1;
			for (ShipComponent component : components) {
                numOfShips *= component.getTimes();
            }
			generateButton.setText("Generate " + numOfShips + " ships!");
			megaPanel.updateUI();
		} else if (a.getSource() == dispForwardButton) {
			displayThisShip++;
			updateDisplayShipField();
		} else if (a.getSource() == dispBackwardButton) {
			displayThisShip--;
			updateDisplayShipField();
		}
	}
	
	public void updateDisplayShipField() {
		if (shipsToDisplay[displayThisShip] == null) {
			displayShipField.setText("Ship does not exist");
		} else {
			displayShipField.setText(shipsToDisplay[displayThisShip].toString());
		}
		dispLabel.setText("Displaying ship #"+displayThisShip);
		megaPanel.updateUI();
	}

    public void generate() {
        ArrayList<Parameter> parameterArrayList = new ArrayList<Parameter>();
        for (ShipComponent component : components) {
            for (Parameter parameter : component.getParameters()) {
                parameterArrayList.add(parameter);
            }
        }
        generateASetting(parameterArrayList,0);
    }

	/**
	 * Changes one Settings value, and generates ships. Only generates ships up to MAX_SHIPS_DISPLAYED. Recursive function.
	 * @param settingsNumber
	 */
	public void generateASetting(ArrayList<Parameter> parameterArrayList, int settingsNumber) {
		//change the settings value settings.listOfSettings[i].number times.
		for (int j = 0; j < parameterArrayList.get(settingsNumber).getTimes(); j++) {
            parameterArrayList.get(settingsNumber).advance();
			if (settingsNumber < parameterArrayList.size()-1) {
				generateASetting(parameterArrayList,settingsNumber+1);
			} else {
				if (shipsDisplayed >= MAX_SHIPS_DISPLAYED) {
					break;
				} else {
					generateShip(parameterArrayList);
				}
			}
		}
        parameterArrayList.get(settingsNumber).reset();
	}
	
	/**
	 * Generates an individual ship based off of Settings specifications. Adds the ship if it qualifies, otherwise ignores it.
	 */
	public void generateShip(ArrayList<Parameter> parameterArrayList) {
		Ship ship = new Ship();
        for (ShipComponent component : components) {
            component.updateShip(ship);
        }
		shipsGenerated++;
//		if (ship.mass<settings.mass[0]||ship.mass>settings.mass[1]) return;
//		if (ship.distance<settings.distance[0]||ship.distance>settings.distance[1]) return;
//		if (ship.magazineCapacity<settings.ammoCapacity[0]||ship.magazineCapacity>settings.ammoCapacity[1]) return;
//		if (ship.velocity<settings.velocity[0]||ship.velocity>settings.velocity[1]) return;
//		if (ship.buildTime<settings.buildTime[0]||ship.buildTime>settings.buildTime[1]) return;
//		if (ship.annualFailureRate<settings.annualFailureRate[0]||ship.annualFailureRate>settings.annualFailureRate[1]) return;
//		if (ship.commercial && !settings.commercial[0]) return;
		shipsToDisplay[shipsDisplayed] = ship;
		shipsDisplayed++;
		
		//record data for number of possible ships within certain parameters
		//useful for removing excess calculations that don't actually result in ships
//		for (int i = 0; i < settings.listOfSettings.length; i++) {
//			settings.listOfSettings[i].values[settings.listOfSettings[i].count]++;
//		}
	}
}
