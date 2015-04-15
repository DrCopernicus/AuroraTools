package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;

public class ShipComponentMaintenanceStorage extends ShipComponent {
    public ShipComponentMaintenanceStorage() {
        super("Maintenance Storage");
    }

    @Override
    public Parameter[] getParameters() {
        return new Parameter[0];
    }

    @Override
    public void updateShip(Ship ship) {

    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentMaintenanceStorage();
    }

}
