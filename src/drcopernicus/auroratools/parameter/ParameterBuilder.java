package drcopernicus.auroratools.parameter;

public class ParameterBuilder {
    public static Parameter integerRangeParameter(String name, int min, int max) {
        Integer[] range = new Integer[max-min+1];
        for (int i = 0; i < range.length; i++) {
            range[i] = min+i;
        }
        return new Parameter(name, range);
    }

    public static ParameterCustomInput defaultCountParameter() {
        return new ParameterCustomInput("Count",0.0,10.0,1.0);
    }

    public static ParameterCustomInput countParameter(int min, int max) {
        return new ParameterCustomInput("Count",min,max,1.0);
    }

    public static ParameterCustomInput countParameter(String name, int min, int max) {
        return new ParameterCustomInput(name ,min,max,1.0);
    }
}
