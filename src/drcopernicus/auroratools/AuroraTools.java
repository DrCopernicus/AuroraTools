package drcopernicus.auroratools;

//import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.VariableSetting;
import drcopernicus.auroratools.ship.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AuroraTools extends JFrame implements ActionListener {
    private static final int MAX_SHIPS_DISPLAYED = 1000;
    public JButton generateButton;
    public JButton refreshButton;
    public JButton dispForwardButton;
    public JButton dispBackwardButton;
    public JTextArea displayShipField;
    public JLabel dispLabel;
    public JButton addComponentButton;
    public JLabel shipsGeneratedLabel;
    private ShipComponent[] components;
    private ShipConstraint constraints;
    private ArrayList<ShipComponent> activeComponentArrayList;
    private JPanel activeComponentsPanel;
    private JScrollPane activeComponentsScroll;
    private JList<ShipComponent> availableComponents;
	private int shipsDisplayed;
	private int shipsGenerated;
	private String[] shipsToDisplay;
	private int displayThisShip;

	private JPanel littleButtonPanel;
	private JPanel megaPanel;
	
	public AuroraTools() {
        components = new ShipComponent[]{
                new ShipComponentHeaderBar(" * COMMON"),
                new ShipComponentPlaceholderComponent(),
                new ShipComponentBridge(),
                new ShipComponentCargoHold(),
                new ShipComponentEngine(),
                new ShipComponentEngineeringSpaces(),
                new ShipComponentFuelStorage(),
                new SmartComponentFuelStorage(),
                new ShipComponentHeaderBar(" * SURVEY"),
				new ShipComponentGeoSensor(),
                new ShipComponentGravSensor(),
                new ShipComponentThermalSensor(),
                new ShipComponentHeaderBar(" * MINING"),
                new ShipComponentSoriumHarvester(),
                new ShipComponentHeaderBar(" * CARRIER"),
                new ShipComponentFlightCrew(),
                new ShipComponentHangarDeck(),
				new ShipComponentHeaderBar(" * ENERGY WEAPONS"),
				new ShipComponentPowerPlant()
        };

        constraints = new ShipConstraint();

        activeComponentArrayList = new ArrayList<ShipComponent>();
        activeComponentArrayList.add(new ShipComponentArmor());
        activeComponentArrayList.add(new ShipComponentDeploymentTime());

		megaPanel = new JPanel();
		megaPanel.setLayout(new BoxLayout(megaPanel,BoxLayout.PAGE_AXIS));
		JPanel componentPanel = makeComponentPanel();
		JPanel reqsPanel = constraints.getPanel();
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
		tabbedPane.addTab("Spec", componentPanel);
		tabbedPane.addTab("Reqs", reqsPanel);
		tabbedPane.addTab("Disp", dispPanel);
		megaPanel.add(tabbedPane);

		refreshButton = new JButton("Refresh");
		generateButton = new JButton("Generate ships!");
		shipsGeneratedLabel = new JLabel("0 generated, 0 displayed");
		refreshButton.addActionListener(this);
		generateButton.addActionListener(this);
		littleButtonPanel = new JPanel();
		littleButtonPanel.add(refreshButton);
		littleButtonPanel.add(generateButton);
		littleButtonPanel.add(shipsGeneratedLabel);
		littleButtonPanel.setMaximumSize(littleButtonPanel.getPreferredSize());
		megaPanel.add(littleButtonPanel);
		this.add(megaPanel);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new AuroraTools();
	}

    private JPanel makeComponentPanel() {
        JPanel componentPanel = new JPanel(new GridLayout(1,0));
        activeComponentsScroll = new JScrollPane();
        activeComponentsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        makeActiveComponentsPanel();
        JPanel availableComponentsPanel = new JPanel(new BorderLayout());
        availableComponents = new JList<ShipComponent>(components);

        addComponentButton = new JButton("Add component");
        addComponentButton.addActionListener(this);
        availableComponentsPanel.add(availableComponents);
        availableComponentsPanel.add(addComponentButton, BorderLayout.PAGE_END);

        JScrollPane availableComponentsScroll = new JScrollPane(availableComponentsPanel);
        availableComponentsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JSplitPane availableComponentsSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, availableComponentsScroll, activeComponentsScroll);
        componentPanel.add(availableComponentsSplitPane);
        return componentPanel;
    }

    private void makeActiveComponentsPanel() {
        activeComponentsPanel = new JPanel();
		activeComponentsPanel.setLayout(new BoxLayout(activeComponentsPanel, BoxLayout.PAGE_AXIS));
        activeComponentsScroll.getViewport().add(activeComponentsPanel);
        for (ShipComponent component : activeComponentArrayList) {
            activeComponentsPanel.add(component.getPanel());
        }
    }

	public void actionPerformed(ActionEvent a) {
		if (a.getSource() == generateButton) {
			for (ShipComponent component : activeComponentArrayList) {
				component.save();
			}
            constraints.save();
			System.out.println("GENERATING SHIPS");
			shipsDisplayed = 0;
			shipsGenerated = 0;
			shipsToDisplay = new String[MAX_SHIPS_DISPLAYED];
			displayThisShip = 0;
            generate();
			updateDisplayShipField();
			System.out.println("SHIPS GENERATED");
			for (ShipComponent component : activeComponentArrayList) {
				component.updateText();
			}
			shipsGeneratedLabel.setText(shipsGenerated+" generated, "+shipsDisplayed+" displayed");
			littleButtonPanel.setMaximumSize(littleButtonPanel.getPreferredSize());
		} else if (a.getSource() == refreshButton) {
			for (ShipComponent component : activeComponentArrayList) {
				component.save();
			}
            constraints.save();
			long numOfShips = 1;
			for (ShipComponent component : activeComponentArrayList) {
                numOfShips *= component.getTimes();
            }
			generateButton.setText("Generate " + numOfShips + " ships!");
			littleButtonPanel.setMaximumSize(littleButtonPanel.getPreferredSize());
			megaPanel.updateUI();
		} else if (a.getSource() == addComponentButton) {
			ShipComponent comp = components[availableComponents.getSelectedIndex()].makeNew();
			if (comp != null)
            {
                activeComponentArrayList.add(comp);
                makeActiveComponentsPanel();
                activeComponentsScroll.repaint();
                activeComponentsScroll.revalidate();
            }
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
        ArrayList<VariableSetting> parameterArrayList = new ArrayList<VariableSetting>();
        for (ShipComponent component : activeComponentArrayList) {
            for (VariableSetting parameter : component.getParameters()) {
                parameterArrayList.add(parameter);
            }
        }
        generateASetting(parameterArrayList,0);
    }

	public void generateASetting(ArrayList<VariableSetting> parameterArrayList, int settingsNumber) {
		for (int j = 0; j < parameterArrayList.get(settingsNumber).getTimes(); j++) {
            parameterArrayList.get(settingsNumber).advance();
			if (settingsNumber < parameterArrayList.size()-1) {
				generateASetting(parameterArrayList,settingsNumber+1);
			} else {
				if (shipsDisplayed >= MAX_SHIPS_DISPLAYED) {
					break;
				} else {
                    generateShip();
				}
			}
		}
        parameterArrayList.get(settingsNumber).reset();
	}
	
	/**
	 * Generates an individual ship based off of Settings specifications. Adds the ship if it qualifies, otherwise ignores it.
	 */
    public void generateShip() {
		Ship ship = new Ship();
        for (ShipComponent component : activeComponentArrayList) {
            component.updateShip(ship);
        }
        shipsGenerated++;
        if (!ship.calculate(constraints)) return;

		shipsToDisplay[shipsDisplayed] = ship.toString();
		shipsDisplayed++;
	}
}
