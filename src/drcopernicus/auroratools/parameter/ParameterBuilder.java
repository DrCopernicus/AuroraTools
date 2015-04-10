package drcopernicus.auroratools.parameter;

public class ParameterBuilder {
    public static Parameter integerRangeParameter(String name, int min, int max) {
        Integer[] range = new Integer[max-min+1];
        for (int i = 0; i < range.length; i++) {
            range[i] = min+i;
        }
        return new Parameter(name, range);
    }
}
