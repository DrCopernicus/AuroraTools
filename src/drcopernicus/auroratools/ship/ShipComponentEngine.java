package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;

public class ShipComponentEngine extends ShipComponent {
    private Parameter count;
    private Parameter engineSize;
    private Parameter enginePower;
    private Parameter powerMod;
    private Parameter fuelEfficiency;

    public ShipComponentEngine() {
        super("Engine");
        count = ParameterBuilder.integerRangeParameter("Count",1,50);
        engineSize = ParameterBuilder.integerRangeParameter("Engine Size",1,50);
        enginePower = new Parameter("Engine Power", new Double[]{5.0,8.0,12.0,16.0,20.0,25.0,32.0,40.0,50.0,60.0,80.0,100.0});
        powerMod = new Parameter("Power Mod", new Double[]{0.1,1.0,3.0});
        fuelEfficiency = new Parameter("Fuel Efficiency", new Integer[]{1,2,3,4,5});
    }

    @Override
    public Parameter[] getParameters() {
        return new Parameter[]{count, engineSize, enginePower, powerMod, fuelEfficiency};
    }

    @Override
    public void updateShip(Ship ship) {
        ship.rawEP += enginePower.getValue() * count.getValue() * engineSize.getValue();
        ship.fuelUse += fuelEfficiency.getValue() * (1 - engineSize.getValue() * 0.01) * Math.pow(powerMod.getValue(), 2.5);
        ship.crew += (int)(engineSize.getValue() * count.getValue() * powerMod.getValue());
        ship.mass += engineSize.getValue() * count.getValue();
        ship.buildPoints += (enginePower.getValue() * count.getValue() * engineSize.getValue())/2; //not accurate, needs thermal calculations
        ship.commercial = (powerMod.getValue() <= 0.5 && engineSize.getValue() >= 25) || ship.commercial;
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentEngine();
    }

}
