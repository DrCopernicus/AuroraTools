package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.ParameterBuilder;
import drcopernicus.auroratools.parameter.ParameterCustomInput;
import drcopernicus.auroratools.parameter.VariableSetting;

public class ShipComponentHeaderBar extends ShipComponent {
    public ShipComponentHeaderBar(String title) {
        super(title);
    }

    @Override
    public VariableSetting[] getParameters() {
        return new VariableSetting[]{};
    }

    @Override
    public void updateShip(Ship ship) {

    }

    @Override
    public ShipComponent makeNew() {
        return null;
    }

}
