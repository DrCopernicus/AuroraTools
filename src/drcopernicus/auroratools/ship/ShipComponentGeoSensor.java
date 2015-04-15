package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;

public class ShipComponentGeoSensor extends ShipComponent{
    private Parameter count;
    private Parameter techRank;

    public ShipComponentGeoSensor() {
        super("Geo Sensor");
        count = ParameterBuilder.integerRangeParameter("Count",0,50);
        techRank = new Parameter("Tech Level", new Integer[]{1,2,3,5});
    }

    @Override
    public Parameter[] getParameters() {
        return new Parameter[]{count, techRank};
    }

    @Override
    public void updateShip(Ship ship) {
        ship.geoSurveyPoints += count.getValue() * techRank.getValue();
        ship.buildPoints += ((techRank.getValue() + 1) * 50) * count.getValue();
        ship.mass += 5 * count.getValue();
        ship.crew += 10 * count.getValue();
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentGeoSensor();
    }

}
