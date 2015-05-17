package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;
import drcopernicus.auroratools.parameter.ParameterCustomInput;
import drcopernicus.auroratools.parameter.VariableSetting;

public class SmartComponentFuelStorage extends ShipComponent {
    private ParameterCustomInput fuelAmount;
    private Parameter size;

    public SmartComponentFuelStorage() {
        super("Smart Fuel Storage");
        fuelAmount = ParameterBuilder.defaultCountParameter();
        size = new Parameter("Size",new Integer[]{5,10,50,250,1000,5000}) {
            public int getTimes() {
                return 1;
            }
        };
    }

    @Override
    public VariableSetting[] getParameters() {
        return new VariableSetting[]{fuelAmount, size};
    }

    @Override
    public void updateShip(Ship ship) {
        int leftover = (int)fuelAmount.getValue();
        for (int i = size.getMaxIterator(); i != size.getMinIterator()-1; i--) {
            leftover -= addMaximumOfSize(ship,
                    (int)size.getValueAt(i),
                    (int)(leftover/size.getValueAt(i)));
            if (leftover <= 0)
                return;
        }
        if (leftover > 0) {
            addMaximumOfSize(ship, (int) size.getMin(), (int)Math.max(leftover/size.getMin(),1));
        }
    }

    //returns amount added
    private int addMaximumOfSize(Ship ship, int size, int count) {
        int added = count * size;
        ship.fuelReserves += added;
        ship.mass += count * size / 50.0;
        switch (size) {
            case 5:
                ship.buildPoints += 2 * count;
                break;
            case 10:
                ship.buildPoints += 3 * count;
                break;
            case 50:
                ship.buildPoints += 10 * count;
                break;
            case 250:
                ship.buildPoints += 30 * count;
                break;
            case 1000:
                ship.buildPoints += 70 * count;
                break;
            case 5000:
                ship.buildPoints += 200 * count;
                break;
            default:
                //size=>buildPoints: 5=>2, 10=>4, 50=>11, 250=>31, 1000=>73, 5000=>200
                //a decent approximation
                ship.buildPoints += Math.floor(Math.pow(size, 1.6075)) * count;
                break;
        }
        return added;
    }

    @Override
    public ShipComponent makeNew() {
        return new SmartComponentFuelStorage();
    }

}
