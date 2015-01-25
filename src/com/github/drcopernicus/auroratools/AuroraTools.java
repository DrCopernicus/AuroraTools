package com.github.drcopernicus.auroratools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	public SpecialPanel[] components;
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
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Flav", flavPanel);
		tabbedPane.addTab("Tech", techPanel);
		tabbedPane.addTab("Spec", specPanel);
		tabbedPane.addTab("Reqs", reqsPanel);
		tabbedPane.addTab("Disp", dispPanel);
		
		megaPanel.add(tabbedPane);
		settings = new Settings();
		components = new SpecialPanel[]{
				new OneStringPanel("Name", settings.shipName, flavPanel),
				new OneStringPanel("Class", settings.shipClass, flavPanel),
				new ThreeDoublePanel("Magazine feed efficiency", settings.techFeedEfficiency, techPanel),
				new ThreeDoublePanel("Magazine ejection", settings.techMagazineEjection, techPanel),
				new ThreeDoublePanel("EP / HS", settings.techEnginePower, techPanel),
				new ThreeDoublePanel("Build points per year per shipyard", settings.techBaseBuildRate, techPanel),
				new ThreeDoublePanel("Fuel consumption", settings.techFuelConsumption, techPanel),
				new ThreeDoublePanel("Geo sensor rank", settings.techGeoSensorRank, techPanel),
				new ThreeDoublePanel("Grav sensor rank", settings.techGravSensorRank, techPanel),
				new ThreeDoublePanel("Armor strength / HS", settings.techArmorWeight, techPanel),
				new ThreeDoublePanel("Deployment time (months)", settings.deploymentTime, specPanel),
				new ThreeDoublePanel("Armor rating", settings.armorRating, specPanel),
				new ThreeDoublePanel("Grav survey points", settings.gravSurveyPoints, specPanel),
				new ThreeDoublePanel("Geo survey points", settings.geoSurveyPoints, specPanel),
				new ThreeDoublePanel("Number of bridges", settings.numberOfBridges, specPanel),
				new ThreeDoublePanel("Number of maintenance storage", settings.numberOfMaintStorage, specPanel),
				new ThreeDoublePanel("Number of engineering spaces", settings.numberOfEngineerSpaces, specPanel),
				new ThreeDoublePanel("Number of magazines", settings.magazineNumber, specPanel),
				new ThreeDoublePanel("Magazine size", settings.magazineSize, specPanel),
				new ThreeDoublePanel("Magazine HTK", settings.magazineHTK, specPanel),
				new ThreeDoublePanel("Engine HS", settings.engineSize, specPanel),
				new ThreeDoublePanel("Number of engines", settings.numberOfEngines, specPanel),
				new ThreeDoublePanel("Engine power mod", settings.enginePowerMod, specPanel),
				new ThreeDoublePanel("Fuel reserves (kL)", settings.fuelReserves, specPanel),
				new CheckBoxPanel("Commercial ships allowed", settings.commercial, reqsPanel),
				new TwoValuePanel("Velocity (km/s)", settings.velocity, reqsPanel, 0, 9999999),
				new TwoValuePanel("Distance (billion km)", settings.distance, reqsPanel, 0, 9999999),
				new TwoValuePanel("Magazine capacity", settings.ammoCapacity, reqsPanel, 0, 8000),
				new TwoValuePanel("Mass (HS)", settings.mass, reqsPanel, 1, 1000),
				new TwoValuePanel("Build time (years)", settings.buildTime, reqsPanel, 0, 1),
				new TwoValuePanel("Annual Failure Rate", settings.annualFailureRate, reqsPanel, 0, 5)
		};
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
		AuroraTools cui = new AuroraTools();
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
			generateASetting(0);
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
			for (int i = 0; i < settings.listOfSettings.length; i++) {
				numOfShips *= settings.listOfSettings[i].getNumber();
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
	
	/**
	 * Changes one Settings value, and generates ships. Only generates ships up to MAX_SHIPS_DISPLAYED. Recursive function.
	 * @param settingsNumber
	 */
	public void generateASetting(int settingsNumber) {
		//change the settings value settings.listOfSettings[i].number times.
		for (int j = 0; j < settings.listOfSettings[settingsNumber].getNumber(); j++) {
			settings.listOfSettings[settingsNumber].advanceTo(j);
			if (settingsNumber < settings.listOfSettings.length-1) {
				generateASetting(settingsNumber+1);
			} else {
				if (shipsDisplayed >= MAX_SHIPS_DISPLAYED) {
					break;
				} else {
					generateShip();
				}
			}
		}
	}
	
	/**
	 * Generates an individual ship based off of Settings specifications. Adds the ship if it qualifies, otherwise ignores it.
	 */
	public void generateShip() {
		Ship ship = new Ship();
		shipsGenerated++;
		if (ship.mass<settings.mass[0]||ship.mass>settings.mass[1]) return;
		if (ship.distance<settings.distance[0]||ship.distance>settings.distance[1]) return;
		if (ship.magazineCapacity<settings.ammoCapacity[0]||ship.magazineCapacity>settings.ammoCapacity[1]) return;
		if (ship.velocity<settings.velocity[0]||ship.velocity>settings.velocity[1]) return;
		if (ship.buildTime<settings.buildTime[0]||ship.buildTime>settings.buildTime[1]) return;
		if (ship.annualFailureRate<settings.annualFailureRate[0]||ship.annualFailureRate>settings.annualFailureRate[1]) return;
		if (ship.commercial && !settings.commercial[0]) return;
		shipsToDisplay[shipsDisplayed] = ship;
		shipsDisplayed++;
		
		//record data for number of possible ships within certain parameters
		//useful for removing excess calculations that don't actually result in ships
		for (int i = 0; i < settings.listOfSettings.length; i++) {
			settings.listOfSettings[i].values[settings.listOfSettings[i].count]++;
		}
	}
	
	private class SpecialPanel extends JPanel {
		public JLabel label;
		public SpecialPanel(String title) {
			this.setLayout(new FlowLayout(FlowLayout.RIGHT));
			label = new JLabel(title);
		}
		public void save() {}
		public void updateText() {}
	}
	
	private class OneStringPanel extends SpecialPanel {
		public JTextField value = new JTextField(10);
		public String[] set;
		
		public OneStringPanel(String title, String[] set, JPanel panel) {
			super(title);
			this.set = set;
			this.add(label);
			this.add(value);
			panel.add(this);
		}
		
		public void save() {
			set[0] = value.getText();
		}
	}
	
	private class TwoValuePanel extends SpecialPanel {
		public JTextField min = new JTextField(8);
		public JTextField max = new JTextField(8);
		public double[] set;

		public TwoValuePanel(String title, double[] set, JPanel panel, double minV, double maxV) {
			super(title);
			this.set = set;
			this.add(label);
			this.add(min);
			min.setText(minV+"");
			this.add(max);
			max.setText(maxV+"");
			panel.add(this);

		}
		
		public void save() {
			set[0] = Double.parseDouble(min.getText());
			set[1] = Double.parseDouble(max.getText());
		}
	}
	
	private class CheckBoxPanel extends SpecialPanel {
		public JCheckBox val = new JCheckBox();
		public boolean[] set;

		public CheckBoxPanel(String title, boolean[] set, JPanel panel) {
			super(title);
			this.set = set;
			this.add(label);
			this.add(val);
			panel.add(this);
		}
		
		public void save() {
			set[0] = val.isSelected();
		}
	}
	
	private class ThreeDoublePanel extends SpecialPanel {
		public JTextField min = new JTextField(3);
		public JTextField max = new JTextField(3);
		public JTextField spacing = new JTextField(3);
		public VariableSetting set;
		public JLabel runs = new JLabel("0x");
		
		public ThreeDoublePanel(String title, VariableSetting set, JPanel panel) {
			super(title);
			this.set = set;
			this.add(label);
			this.add(min);
			min.setText(set.getMin()+"");
			this.add(max);
			max.setText(set.getMax()+"");
			this.add(spacing);
			spacing.setText(set.getSpacing()+"");
			runs.setPreferredSize(new Dimension(40,runs.getPreferredSize().height));
			this.add(runs);
			
			setToolTipText("No runs.");
			
			panel.add(this);
		}
		
		public void save() {
			set.setAll(Double.parseDouble(min.getText()), Double.parseDouble(max.getText()), Double.parseDouble(spacing.getText()));
			runs.setText((int)set.getNumber()+"x");
			setToolTipText("Tooltip");
		}
	}
}
