package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.*;

public class ShipComponentCargoHold extends ShipComponent {
    private ParameterCustomInput count;
    private Parameter size;

    public ShipComponentCargoHold() {
        super("Cargo Hold");
        count = ParameterBuilder.defaultCountParameter();
        size = new ParameterWithDescriptions("Size",new Integer[]{100,500},new String[]{"Small","Standard"});
    }

    @Override
    public VariableSetting[] getParameters() {
        return new VariableSetting[]{count, size};
    }

    @Override
    public void updateShip(Ship ship) {
        ship.mass += size.getValue() * count.getValue();
        ship.crew += ((int)size.getValue()==100 ? 2 : 5) * count.getValue();
        ship.buildPoints += ((int)size.getValue()==100 ? 12 : 50) * count.getValue();
        ship.cargoCapacity += size.getValue() * count.getValue();
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentCargoHold();
    }
}
