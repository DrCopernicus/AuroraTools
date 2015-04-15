package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;

public class ShipConstraint extends ShipComponent {
    private Parameter mass;
    private Parameter cargoCapacity;

    public ShipConstraint() {
        super("Ship Constraints");
        mass = ParameterBuilder.integerRangeParameter("Mass (HS/10)", 0, 50);
        cargoCapacity = ParameterBuilder.integerRangeParameter("Cargo Capacity (HS)", 0, 50);
    }

    @Override
    public Parameter[] getParameters() {
        return new Parameter[]{mass,cargoCapacity};
    }

    @Override
    public void updateShip(Ship ship) {

    }

    @Override
    public ShipComponent makeNew() {
        return new ShipConstraint();
    }

    public boolean passable(Ship ship) {
        return (ship.mass>=mass.getMin()*10&&ship.mass<=mass.getMax()*10)
                &&(ship.cargoCapacity>=cargoCapacity.getMin()&&ship.mass<=cargoCapacity.getMax());
    }
}
