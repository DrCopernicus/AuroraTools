package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;

public class ShipComponentFuelStorage extends ShipComponent {
    private Parameter count;
    private Parameter size;

    public ShipComponentFuelStorage() {
        super("Fuel Storage");
        count = ParameterBuilder.integerRangeParameter("Count", 1, 50);
        size = new Parameter("Size",new Integer[]{5,10,50,250,1000,5000});
    }

    @Override
    public Parameter[] getParameters() {
        return new Parameter[]{count, size};
    }

    @Override
    public void updateShip(Ship ship) {
        ship.fuelReserves += count.getValue() * size.getValue();
        ship.mass += count.getValue() * size.getValue() / 50.0;
        switch ((int)size.getValue()) {
            case 5:
                ship.buildPoints += 2 * count.getValue();
                break;
            case 10:
                ship.buildPoints += 3 * count.getValue();
                break;
            case 50:
                ship.buildPoints += 10 * count.getValue();
                break;
            case 250:
                ship.buildPoints += 30 * count.getValue();
                break;
            case 1000:
                ship.buildPoints += 70 * count.getValue();
                break;
            case 5000:
                ship.buildPoints += 200 * count.getValue();
                break;
            default:
                //size=>buildPoints: 5=>2, 10=>4, 50=>11, 250=>31, 1000=>73, 5000=>200
                //a decent approximation
                ship.buildPoints += Math.floor(Math.pow(size.getValue(), 1.6075)) * count.getValue();
                break;
        }
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentFuelStorage();
    }

}
