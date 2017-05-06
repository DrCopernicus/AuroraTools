package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;
import drcopernicus.auroratools.parameter.ParameterCustomInput;
import drcopernicus.auroratools.parameter.VariableSetting;

public class ShipComponentDeploymentTime extends ShipComponent {
    private ParameterCustomInput months;

    public ShipComponentDeploymentTime() {
        super("Deployment Time");
        months = new ParameterCustomInput("Crew Deployment Time",3.0,27.0,6.0);
    }
    @Override
    public VariableSetting[] getParameters() {
        return new VariableSetting[]{months};
    }

    @Override
    public void updateShip(Ship ship) {
        ship.deploymentTime = months.getValue();
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentDeploymentTime();
    }
}
