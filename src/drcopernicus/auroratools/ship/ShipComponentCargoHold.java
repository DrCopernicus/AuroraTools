package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;
import drcopernicus.auroratools.parameter.ParameterWithDescriptions;

public class ShipComponentCargoHold extends ShipComponent {
    private Parameter count;
    private Parameter size;

    public ShipComponentCargoHold() {
        super("Cargo Hold");
        count = ParameterBuilder.integerRangeParameter("Count", 1, 50);
        size = new ParameterWithDescriptions("Size",new Integer[]{100,500},new String[]{"Small","Standard"});
    }

    @Override
    public Parameter[] getParameters() {
        return new Parameter[]{count, size};
    }

    @Override
    public void updateShip(Ship ship) {
        ship.mass += size.getValue() * count.getValue();
        ship.crew += 1 * count.getValue();
        ship.cargoCapacity += 50 * size.getValue() * count.getValue();
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentCargoHold();
    }
}
