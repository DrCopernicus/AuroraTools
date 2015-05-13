package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.*;

public class ShipComponentEngine extends ShipComponent {
    private ParameterCustomInput count;
    private Parameter engineSize;
    private ParameterWithDescriptions enginePower;
    private Parameter powerMod;
    private Parameter fuelEfficiency;

    public ShipComponentEngine() {
        super("Engine");
        count = ParameterBuilder.defaultCountParameter();
        engineSize = ParameterBuilder.integerRangeParameter("Engine Size",1,50);
        enginePower = new ParameterWithDescriptions("Engine Power",
                new Double[]{1.0,5.0,8.0,12.0,16.0,20.0,25.0,32.0,40.0,50.0,60.0,80.0,100.0},
                new String[]{"Conventional","Nuclear Thermal","Nuclear Pulse","Ion","Magneto-plasma",
                        "Internal Confinement","Magnetic Confinement","Inertial Confinement",
                        "Solid Core Anti-matter","Gas Core Anti-matter","Plasma Core Anti-matter",
                        "Beam Core Anti-matter","Photonic Drive"});
        powerMod = new Parameter("Power Mod", new Double[]{0.1,1.0,3.0});
        fuelEfficiency = new Parameter("Fuel Efficiency", new Double[]{0.1,0.125,0.16,0.2,0.25,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0});
    }

    @Override
    public VariableSetting[] getParameters() {
        return new VariableSetting[]{count, engineSize, enginePower, powerMod, fuelEfficiency};
    }

    @Override
    public void updateShip(Ship ship) {
        ship.numberOfEngines += count.getValue();
        ship.rawEP += enginePower.getValue() * count.getValue() * engineSize.getValue();
        ship.fuelUse += fuelEfficiency.getValue() * (1 - engineSize.getValue() * 0.01) * Math.pow(powerMod.getValue(), 2.5);
        ship.crew += (int)(engineSize.getValue() * count.getValue() * powerMod.getValue());
        ship.mass += engineSize.getValue() * count.getValue();
        ship.buildPoints += (enginePower.getValue() * count.getValue() * engineSize.getValue())/2; //not accurate, needs thermal calculations
        boolean military = (powerMod.getValue() > 0.5 || engineSize.getValue() < 25);
        ship.military = (military || ship.military);
        ship.engineTitle = (military ? "Military " : "Commercial ") + enginePower.getString() + " Engine";
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentEngine();
    }

}
