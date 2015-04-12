package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;

public class ShipComponentArmor extends ShipComponent {
    private Parameter armorRating;

    public ShipComponentArmor() {
        super("Armor");
        armorRating = ParameterBuilder.integerRangeParameter("Armor Rating",1,50);
    }

    @Override
    public Parameter[] getParameters() {
        return new Parameter[]{armorRating};
    }

    @Override
    public void updateShip(Ship ship) {
        ship.armorRating = (int)armorRating.getValue();
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentArmor();
    }

}
