package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.ParameterBuilder;
import drcopernicus.auroratools.parameter.ParameterCustomInput;
import drcopernicus.auroratools.parameter.VariableSetting;

public class ShipComponentMaintenanceStorage extends ShipComponent {
    private ParameterCustomInput count;

    public ShipComponentMaintenanceStorage() {
        super("Maintenance Storage");
        count = ParameterBuilder.defaultCountParameter();
    }

    @Override
    public VariableSetting[] getParameters() {
        return new VariableSetting[]{count};
    }

    @Override
    public void updateShip(Ship ship) {
        ship.mSP += 1000 * count.getValue();
        ship.buildPoints += 15 * count.getValue();
        ship.mass += 5 * count.getValue();
        ship.crew += 2 * count.getValue();
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentMaintenanceStorage();
    }

}
