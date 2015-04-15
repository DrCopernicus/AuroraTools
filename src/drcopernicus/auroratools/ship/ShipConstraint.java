package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;

public class ShipConstraint extends ShipComponent {
    public Parameter mass;
    public Parameter cargoCapacity;
    public Parameter velocity;

    public ShipConstraint() {
        super("Ship Constraints");
        mass = ParameterBuilder.integerRangeParameter("Mass (10HS)", 0, 50);
        cargoCapacity = ParameterBuilder.integerRangeParameter("Cargo Capacity (HS)", 0, 50);
        velocity = ParameterBuilder.integerRangeParameter("Velocity (Mm/s)", 0, 50);
    }

    @Override
    public Parameter[] getParameters() {
        return new Parameter[]{mass,cargoCapacity,velocity};
    }

    @Override
    public void updateShip(Ship ship) {

    }

    @Override
    public ShipComponent makeNew() {
        return new ShipConstraint();
    }
}
