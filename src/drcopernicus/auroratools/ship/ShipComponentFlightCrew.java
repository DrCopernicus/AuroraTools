package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;
import drcopernicus.auroratools.parameter.ParameterCustomInput;
import drcopernicus.auroratools.parameter.VariableSetting;

public class ShipComponentFlightCrew extends ShipComponent {
    private ParameterCustomInput count;

    public ShipComponentFlightCrew() {
        super("Flight Crew Members");
        count = ParameterBuilder.defaultCountParameter();
    }

    @Override
    public VariableSetting[] getParameters() {
        return new VariableSetting[]{count};
    }

    @Override
    public void updateShip(Ship ship) {
        ship.crew += count.getValue();
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentFlightCrew();
    }

}
