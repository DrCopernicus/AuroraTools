package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.ParameterBuilder;
import drcopernicus.auroratools.parameter.ParameterCustomInput;
import drcopernicus.auroratools.parameter.VariableSetting;

public class ShipComponentBridge extends ShipComponent {
    private ParameterCustomInput count;

    public ShipComponentBridge() {
        super("Bridge");
        count = ParameterBuilder.defaultCountParameter();
    }

    @Override
    public VariableSetting[] getParameters() {
        return new VariableSetting[]{count};
    }

    @Override
    public void updateShip(Ship ship) {
        ship.mass += count.getValue();
        ship.crew += count.getValue() * 5;
        ship.buildPoints += count.getValue() * 10;
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentBridge();
    }

}
