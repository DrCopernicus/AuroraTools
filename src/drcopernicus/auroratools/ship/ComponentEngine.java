package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;

public class ComponentEngine extends Component {
    private Parameter count;
    private Parameter engineSize;

    public ComponentEngine() {
        count = new Parameter("Count", new Integer[]{0,1,2,3,4,5},0,5);
        engineSize = new Parameter("Size", new Integer[]{0,1,2,3,4,5},0,5);
    }
}
