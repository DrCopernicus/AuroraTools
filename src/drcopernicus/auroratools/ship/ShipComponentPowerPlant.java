package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.*;

public class ShipComponentPowerPlant extends ShipComponent{
    private ParameterCustomInput count;
    private Parameter reactorPower;
    private Parameter size;
    private Parameter boost;

    public ShipComponentPowerPlant() {
        super("Power Plant");
        count = ParameterBuilder.defaultCountParameter();
        reactorPower = new ParameterWithDescriptions("Power Plant Technology",
                new Double[]{2.0,3.0,4.5,6.0,8.0,10.0,12.0,16.0,20.0,24.0,32.0,40.0},
                new String[]{"Pressurized Water", "Pebble Bed", "Gas-Cooled", "Stellarator Fusion", "Tokamak Fusion", "Magnetic Confinement", "Inertial Confinement", "Solid-core Anti-matter", "Gas-core Anti-matter", "Plasma-core Anti-matter", "Beam Core Anti-matter", "Vacuum Energy"});
        size = new Parameter("Size", new Double[]{0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0,10.0,11.0,12.0,13.0,14.0,15.0,16.0,17.0,18.0,19.0,20.0,21.0,22.0,23.0,24.0,25.0,26.0,27.0,28.0,29.0,30.0});
        boost = new ParameterWithDescriptions("Power vs Efficiency",
                new Double[]{0.05,0.1,0.15,0.2,0.25,0.3,0.4,0.5},
                new String[]{"5% / 7%",
                        "10% / 10%",
                        "15% / 12%",
                        "20% / 16%",
                        "25% / 20%",
                        "30% / 25%",
                        "40% / 30%",
                        "50% / 35%"});
    }

    @Override
    public VariableSetting[] getParameters() {
        return new VariableSetting[]{count, reactorPower, size, boost};
    }

    @Override
    public void updateShip(Ship ship) {
        double power = Math.round(size.getValue()*reactorPower.getValue()*(1+boost.getValue())*100.0)/100.0;

        ship.powerGenerated += power * count.getValue();
        ship.buildPoints += Math.max(Math.round(power * count.getValue()), 1);
        ship.mass += size.getValue() * count.getValue();
        ship.crew += Math.max(Math.round(size.getValue() * 2) * count.getValue(), 1);
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentPowerPlant();
    }

}
