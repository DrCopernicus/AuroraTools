package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;
import drcopernicus.auroratools.parameter.VariableSetting;

public class ShipComponentEngineeringSpaces extends ShipComponent {
    private Parameter count;

    public ShipComponentEngineeringSpaces() {
        super("Engineering Spaces");
        count = ParameterBuilder.integerRangeParameter("Count", 0, 50);
    }

    @Override
    public VariableSetting[] getParameters() {
        return new VariableSetting[]{count};
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
