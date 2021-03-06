package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;
import drcopernicus.auroratools.parameter.ParameterCustomInput;
import drcopernicus.auroratools.parameter.VariableSetting;

import javax.swing.*;

public class ShipComponentGravSensor extends ShipComponent {
    private ParameterCustomInput count;
    private Parameter techRank;

    public ShipComponentGravSensor() {
        super("Grav Sensor");
        count = ParameterBuilder.defaultCountParameter();
        techRank = new Parameter("Tech Level", new Integer[]{1,2,3,5});
    }

    @Override
    public VariableSetting[] getParameters() {
        return new VariableSetting[]{count, techRank};
    }

    @Override
    public void updateShip(Ship ship) {
        ship.gravSurveyPoints += count.getValue() * techRank.getValue();
        ship.buildPoints += ((techRank.getValue() + 1) * 50) * count.getValue();
        ship.mass += 5 * count.getValue();
        ship.crew += 10 * count.getValue();
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentGravSensor();
    }
}
