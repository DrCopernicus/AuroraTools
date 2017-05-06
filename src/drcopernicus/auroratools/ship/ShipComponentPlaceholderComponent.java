package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.*;

public class ShipComponentPlaceholderComponent extends ShipComponent {
    private ParameterCustomInput count;
    private ParameterSingleInput size;
    private ParameterSingleInput buildPoints;
    private ParameterSingleInput crew;
    private ParameterSingleInput powerRequirement;

    public ShipComponentPlaceholderComponent() {
        super("Placeholder Component");
        count = ParameterBuilder.defaultCountParameter();
        size = new ParameterSingleInput("Size", 0.0);
        buildPoints = new ParameterSingleInput("Build Points", 0.0);
        crew = new ParameterSingleInput("Crew", 0.0);
        powerRequirement = new ParameterSingleInput("Power Requirement", 0.0);
    }

    @Override
    public VariableSetting[] getParameters() {
        return new VariableSetting[]{count, size, buildPoints, crew, powerRequirement};
    }

    @Override
    public void updateShip(Ship ship) {
        double numberOfComponents = count.getValue();
        ship.mass += size.getValue() * numberOfComponents;
        ship.buildPoints += buildPoints.getValue() * numberOfComponents;
        ship.crew += crew.getValue() * numberOfComponents;
        ship.powerRequirement += powerRequirement.getValue() * numberOfComponents;
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentPlaceholderComponent();
    }

}
