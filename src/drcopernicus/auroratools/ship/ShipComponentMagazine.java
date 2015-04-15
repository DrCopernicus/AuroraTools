package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;
import drcopernicus.auroratools.parameter.VariableSetting;

public class ShipComponentMagazine extends ShipComponent {
    private Parameter count;

    public ShipComponentMagazine() {
        super("Magazine");
        count = ParameterBuilder.integerRangeParameter("Count", 0, 50);

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
