package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;

public class ShipComponentBridge extends ShipComponent {
    private Parameter count;

    public ShipComponentBridge() {
        super("Bridge");
        count = ParameterBuilder.integerRangeParameter("Count", 1, 50);
    }

    @Override
    public Parameter[] getParameters() {
        return new Parameter[]{count};
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
