package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;

import javax.swing.*;

public class ShipComponentEngine extends ShipComponent {
    private Parameter count;
    private Parameter engineSize;
    private Parameter powerMod;
    private Parameter fuelEfficiency;

    public ShipComponentEngine() {
        count = new Parameter("Count", new Integer[]{0,1,2,3,4,5},0,5);
        engineSize = new Parameter("Size", new Integer[]{1,2,3,4,5},0,4);
        powerMod = new Parameter("Power Mod", new Integer[]{1,2,3,4,5},0,4);
        fuelEfficiency = new Parameter("Fuel Efficiency", new Integer[]{1,2,3,4,5},0,4);

        jPanel = new JPanel();
        jLabel = new JLabel("Engine");
        jPanel.add(count.getPanel());
        jPanel.add(engineSize.getPanel());
    }

    @Override
    public Parameter[] getParameters() {
        return new Parameter[]{count, engineSize, powerMod, fuelEfficiency};
    }

    @Override
    public void updateShip(Ship ship) {

    }
}
