package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.*;

public class ShipComponentThermalSensor extends ShipComponent {
    private ParameterCustomInput count;
    private Parameter sensitivity, size, hardening;

    public ShipComponentThermalSensor() {
        super("Thermal Sensor");
        count = ParameterBuilder.defaultCountParameter();
        sensitivity = new Parameter("Sensitivity", new Integer[]{5,6,8,11});
        size = new Parameter("Size", new Double[]{0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0,1.2,1.4,1.6,1.8,2.0,2.25,2.5,
                2.75,3.0,3.25,3.5,3.75,4.0,4.25,4.5,4.75,5.0,6.0,7.0,8.0,9.0,10.0,11.0,12.0,13.0,14.0,15.0,16.0,17.0,
                18.0,19.0,20.0,21.0,22.0,23.0,24.0,25.0,26.0,27.0,28.0,29.0,30.0,31.0,32.0,33.0,34.0,35.0,36.0,37.0,
                38.0,39.0,40.0,41.0,42.0,43.0,44.0,45.0,46.0,47.0,48.0,49.0,50.0}); //unholy abomination
        hardening = new Parameter("Hardening", new Integer[]{0,1,2});
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentThermalSensor();
    }

    @Override
    public VariableSetting[] getParameters() {
        return new VariableSetting[]{count, sensitivity, size, hardening};
    }

    @Override
    public void updateShip(Ship ship) {
        ship.crew += 2*count.getValue();
        ship.buildPoints += count.getValue()*sensitivity.getValue()*size.getValue()*(1+hardening.getValue()/4.0);
        ship.mass += size.getValue()*count.getValue();
        boolean military = (size.getValue() > 1.0 || hardening.getValue() > 0);
        ship.military = (military || ship.military);
    }
}
