package drcopernicus.auroratools.ship;

import com.sun.org.apache.xpath.internal.operations.Variable;
import drcopernicus.auroratools.parameter.Parameter;
import drcopernicus.auroratools.parameter.ParameterBuilder;
import drcopernicus.auroratools.parameter.ParameterWithDescriptions;
import drcopernicus.auroratools.parameter.VariableSetting;

public class ShipComponentArmor extends ShipComponent {
    private Parameter armorRating;
    private Parameter armorWeight;

    public ShipComponentArmor() {
        super("Armor");
        armorRating = ParameterBuilder.integerRangeParameter("Armor Rating",1,100);
        armorWeight = new ParameterWithDescriptions("Armor Weight",new Integer[]{2,5,6,8,10,12,15,18,21,25,30,36,45},
                new String[]{"Conventional","Duranium","High Density Duranium","Composite","Ceramic Composite",
                "Laminate Composite","Compressed Carbon","Biphase Carbon","Crystalline Composite","Superdense",
                "Bonded Superdense","Coherent Superdense","Collapsium"});
    }

    @Override
    public VariableSetting[] getParameters() {
        return new VariableSetting[]{armorRating, armorWeight};
    }

    @Override
    public void updateShip(Ship ship) {
        ship.armorRating = (int)armorRating.getValue();
        ship.armorWeight = (int)armorWeight.getValue();
    }

    @Override
    public ShipComponent makeNew() {
        return new ShipComponentArmor();
    }

}
