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
				new OneDoublePanel("Magazine feed efficiency", settings.techFeedEfficiency, techPanel),
				new OneDoublePanel("Magazine ejection", settings.techMagazineEjection, techPanel),
				new OneDoublePanel("EP / HS", settings.techEnginePower, techPanel),
				new OneDoublePanel("Build points per year per shipyard", settings.techBaseBuildRate, techPanel),
				new OneDoublePanel("Fuel consumption", settings.techFuelConsumption, techPanel),
				new OneDoublePanel("Geo sensor rank", settings.techGeoSensorRank, techPanel),
				new OneDoublePanel("Grav sensor rank", settings.techGravSensorRank, techPanel),
				new OneDoublePanel("Armor strength / HS", settings.techArmorWeight, techPanel),
				new ThreeDoublePanel("Deployment time (months)", settings.deploymentTime, specPanel, 24, 24, 1),
				new ThreeDoublePanel("Armor rating", settings.armorRating, specPanel, 1, 100, 1),
				new ThreeDoublePanel("Grav survey points", settings.gravSurveyPoints, specPanel, 0, 0, 1),
				new ThreeDoublePanel("Geo survey points", settings.geoSurveyPoints, specPanel, 0, 0, 1),
				new ThreeDoublePanel("Number of bridges", settings.numberOfBridges, specPanel, 1, 1, 1),
				new ThreeDoublePanel("Number of maintenance storage", settings.numberOfMaintStorage, specPanel, 0, 0, 1),
				new ThreeDoublePanel("Number of engineering spaces", settings.numberOfEngineerSpaces, specPanel, 1, 1, 1),
				new ThreeDoublePanel("Number of magazines", settings.magazineNumber, specPanel, 0, 0, 1),
				new ThreeDoublePanel("Magazine size", settings.magazineSize, specPanel, 1, 30, 1),
				new ThreeDoublePanel("Magazine HTK", settings.magazineHTK, specPanel, 1, 10, 1),
				new ThreeDoublePanel("Engine HS", settings.engineSize, specPanel, 1, 50, 1),
				new ThreeDoublePanel("Number of engines", settings.numberOfEngines, specPanel, 1, 3, 1),
				new ThreeDoublePanel("Engine power mod", settings.enginePowerMod, specPanel, 0.1, 3.0, 0.05),
				new ThreeDoublePanel("Fuel reserves (kL)", settings.fuelReserves, specPanel, 500, 5000, 500),
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
	
	/**
	 * returns the current value of the test (given the test number)
	 * @param vs
	 * @param i
	 * @return
	 */
	private double currentTestValue(VariableSetting vs, int i) {
		return vs.min + vs.spacing*i;
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
				numOfShips *= settings.listOfSettings[i].number;
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
		for (int j = 0; j < settings.listOfSettings[settingsNumber].number; j++) {
			settings.listOfSettings[settingsNumber].current = currentTestValue(settings.listOfSettings[settingsNumber], j);
			settings.listOfSettings[settingsNumber].count = j;
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
	
	private class OneDoublePanel extends SpecialPanel {
		public JTextField value = new JTextField(3);
		public double[] set;
		
		public OneDoublePanel(String title, double[] set, JPanel panel) {
			super(title);
			this.set = set;
			this.add(label);
			this.add(value);
			panel.add(this);
		}
		
		public void save() {
			set[0] = Double.parseDouble(value.getText());
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
		public JLabel runs = new JLabel("0");
		
		public ThreeDoublePanel(String title, VariableSetting set, JPanel panel, double minV, double maxV, double spaV) {
			super(title);
			this.set = set;
			this.add(label);
			this.add(min);
			min.setText(minV+"");
			this.add(max);
			max.setText(maxV+"");
			this.add(spacing);
			spacing.setText(spaV+"");
			runs.setPreferredSize(new Dimension(40,runs.getPreferredSize().height));
			this.add(runs);
			panel.add(this);
		}
		
		public void save() {
			set.min = Double.parseDouble(min.getText());
			set.max = Double.parseDouble(max.getText());
			set.spacing = Double.parseDouble(spacing.getText());
			set.current = set.min;
			set.recalculate();
			runs.setText(""+set.number);
		}
		
		public void updateText() {
			String tooltipText = "<html><pre>";
			for (int i = 0; i < set.values.length; i++) {
				tooltipText += currentTestValue(set,i)+"   "+set.values[i]+"<br/>";
			}
			tooltipText += "</pre></html>";
			label.setToolTipText(tooltipText);
		}
	}
}
