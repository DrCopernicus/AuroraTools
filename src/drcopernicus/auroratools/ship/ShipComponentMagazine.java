package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.ParameterBuilder;
import drcopernicus.auroratools.parameter.ParameterCustomInput;
import drcopernicus.auroratools.parameter.VariableSetting;

public class ShipComponentMagazine extends ShipComponent {
    private ParameterCustomInput count;

    public ShipComponentMagazine() {
        super("Magazine");
        count = ParameterBuilder.defaultCountParameter();

    }

    @Override
    public VariableSetting[] getParameters() {
        return new VariableSetting[]{count};
    }

    @Override
    public void updateShip(Ship ship) {

    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentMagazine();
    }

}
