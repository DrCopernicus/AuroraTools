package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;

public class ShipComponentMagazine extends ShipComponent {
    private Parameter count;


    public ShipComponentMagazine() {
        count = ParameterBuilder.integerRangeParameter("Count", 1, 50);

    }

    @Override
    public Parameter[] getParameters() {
        return new Parameter[0];
    }

    @Override
    public void updateShip(Ship ship) {

    }
}
