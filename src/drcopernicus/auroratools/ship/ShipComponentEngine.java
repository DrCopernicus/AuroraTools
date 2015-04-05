package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;

import javax.swing.*;

public class ShipComponentEngine extends ShipComponent {
    private Parameter count;
    private Parameter engineSize;
    private Parameter powerMod;
    private Parameter fuelEfficiency;

    public ShipComponentEngine() {
        count = ParameterBuilder.integerRangeParameter("Count",1,50);
        engineSize = ParameterBuilder.integerRangeParameter("Count",1,50);
        powerMod = new Parameter("Power Mod", new Integer[]{1,2,3,4,5});
        fuelEfficiency = new Parameter("Fuel Efficiency", new Integer[]{1,2,3,4,5});
        makePanel("Engine");
    }

    @Override
    public Parameter[] getParameters() {
        return new Parameter[]{count, engineSize, powerMod, fuelEfficiency};
    }

    @Override
    public void updateShip(Ship ship) {

    }
}
