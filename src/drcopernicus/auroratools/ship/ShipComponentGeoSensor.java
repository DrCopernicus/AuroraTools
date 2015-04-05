package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;

public class ShipComponentGeoSensor extends ShipComponent{
    private Parameter count;
    private Parameter techRank;

    public ShipComponentGeoSensor() {
        count = ParameterBuilder.integerRangeParameter("Count",1,50);
        techRank = new Parameter("Tech Level", new Integer[]{1,2,5});
        makePanel("Geo Sensor");
    }

    @Override
    public Parameter[] getParameters() {
        return new Parameter[]{count, techRank};
    }

    @Override
    public void updateShip(Ship ship) {
        ship.geoSurveyPoints += count.getValue() * techRank.getValue();
    }
}
