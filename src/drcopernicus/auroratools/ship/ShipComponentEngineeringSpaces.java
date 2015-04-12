package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;

public class ShipComponentEngineeringSpaces extends ShipComponent {
    private Parameter count;

    public ShipComponentEngineeringSpaces() {
        super("Engineering Spaces");
        count = ParameterBuilder.integerRangeParameter("Armor Rating", 1, 50);
    }

    @Override
    public Parameter[] getParameters() {
        return new Parameter[]{count};
    }

    @Override
    public void updateShip(Ship ship) {
        ship.buildPoints += 10 * count.getValue();
        ship.mass += count.getValue();
        ship.crew += 5 * count.getValue();
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentEngineeringSpaces();
    }
}
