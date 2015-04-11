package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;

public class ShipComponentCrewQuarters extends ShipComponent {
    private Parameter months;

    public ShipComponentCrewQuarters() {
        super("Crew Quarters");
        months = ParameterBuilder.integerRangeParameter("Crew Deployment Time", 3, 50);
    }
    @Override
    public Parameter[] getParameters() {
        return new Parameter[]{months};
    }

    @Override
    public void updateShip(Ship ship) {
        ship.deploymentTime = (int)months.getValue();
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentCrewQuarters();
    }
}
