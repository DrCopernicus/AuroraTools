package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;
import drcopernicus.auroratools.parameter.ParameterCustomInput;
import drcopernicus.auroratools.parameter.VariableSetting;

public class ShipComponentHangarDeck extends ShipComponent {
    private ParameterCustomInput count[];

    public ShipComponentHangarDeck() {
        super("Hangar Deck");
        count = new ParameterCustomInput[] {
            ParameterBuilder.countParameter("Boat Bay - Small", 0, 100),
            ParameterBuilder.countParameter("Boat Bay", 0, 100),
            ParameterBuilder.countParameter("Hangar Deck", 0, 100)
        };
    }

    @Override
    public VariableSetting[] getParameters() {
        return new VariableSetting[]{count[0], count[1], count[2]};
    }

    @Override
    public void updateShip(Ship ship) {
        ship.crew += count[0].getValue() * 3;
        ship.crew += count[1].getValue() * 5;
        ship.crew += count[2].getValue() * 15;
        ship.hangarDeckCapacity += count[0].getValue() * 125;
        ship.hangarDeckCapacity += count[1].getValue() * 250;
        ship.hangarDeckCapacity += count[2].getValue() * 1000;
        ship.mass += count[0].getValue() * 3;
        ship.mass += count[1].getValue() * 6;
        ship.mass += count[2].getValue() * 21;
        ship.buildPoints += count[0].getValue() * 18;
        ship.buildPoints += count[1].getValue() * 30;
        ship.buildPoints += count[2].getValue() * 100;

        if (count[0].getValue() > 0 || count[1].getValue() > 0 || count[2].getValue() > 0 )
            ship.military = true;
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentHangarDeck();
    }

}
