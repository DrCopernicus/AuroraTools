package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.*;

public class ShipComponentSoriumHarvester extends ShipComponent {
    private ParameterCustomInput count;
    private Parameter harvestRate;

    public ShipComponentSoriumHarvester() {
        super("Sorium Harvester");
        count = ParameterBuilder.defaultCountParameter();
        harvestRate = new Parameter("kL per Annum",new Integer[]{24,28,32,40,50,60,72,84,100,120,140});
    }

    @Override
    public VariableSetting[] getParameters() {
        return new VariableSetting[]{count, harvestRate};
    }

    @Override
    public void updateShip(Ship ship) {
        ship.mass += 50*count.getValue();
        ship.crew += 10*count.getValue();
        ship.buildPoints += 30*count.getValue();
        ship.fuelPerAnnum += harvestRate.getValue()*count.getValue();
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentSoriumHarvester();
    }

}
