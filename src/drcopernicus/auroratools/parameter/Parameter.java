package drcopernicus.auroratools.parameter;

public class Parameter {
    private String name;
    private Number[] choices;
    private int current;
    private int min;
    private int max;

    public Parameter(String name, Number[] choices, int min, int max) {
        this.name = name;
        this.choices = choices;
        this.min = min;
        this.max = max;
        current = min;
    }

    public Number getValue() {
        return choices[current];
    }

    public void reset() {
        current = min;
    }

    public void advance() {
        if (current >= max) return;
        current++;
    }

    /**
     * Number of choices
     * @return
     */
    public int getTimes() {
        return max-min;
    }

    public String getName() {
        return name;
    }
}
