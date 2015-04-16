package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.VariableSetting;

public class ShipComponentMaintenanceStorage extends ShipComponent {
    public ShipComponentMaintenanceStorage() {
        super("Maintenance Storage");
    }

    @Override
    public VariableSetting[] getParameters() {
        return new VariableSetting[0];
    }

    @Override
    public void updateShip(Ship ship) {

    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentMaintenanceStorage();
    }

}
