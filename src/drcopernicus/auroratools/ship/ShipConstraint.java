package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.ConstraintCustomInput;
import drcopernicus.auroratools.parameter.VariableSetting;

public class ShipConstraint extends ShipComponent {
    public ConstraintCustomInput mass;
    public ConstraintCustomInput cargoCapacity;
    public ConstraintCustomInput velocity;
    public ConstraintCustomInput hangarDeckCapacity;
    public ConstraintCustomInput distance;

    public ShipConstraint() {
        super("Ship Constraints");
        mass = new ConstraintCustomInput("Mass (tons)",0,10000);
        cargoCapacity = new ConstraintCustomInput("Cargo Capacity (tons)",0,1000000);
        velocity = new ConstraintCustomInput("Velocity (km/s)",0,1000000);
        hangarDeckCapacity = new ConstraintCustomInput("Hangar Deck Capacity (tons)",0,1000000);
        distance = new ConstraintCustomInput("Range (billion km)", 0, 1000000);
    }

    @Override
    public VariableSetting[] getParameters() {
        return new VariableSetting[]{mass, cargoCapacity, velocity, hangarDeckCapacity, distance};
    }

    @Override
    public void updateShip(Ship ship) {

    }

    @Override
    public ShipComponent makeNew() {
        return new ShipConstraint();
    }
}
