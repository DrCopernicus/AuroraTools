package drcopernicus.auroratools.parameter;

import javax.swing.*;

public abstract class VariableSetting {
    protected String name;
    protected int current;
    protected int min;
    protected int max;

    protected JPanel jPanel;
    protected JLabel nameLabel;

    protected VariableSetting(String name, int max) {
        this.name = name;
        min = 0;
        this.max = max;
        current = current;
    }

    public abstract void save();

    public void updateText() {
        nameLabel.setText(name+" ("+getTimes()+"x)");
    }

    /**
     * Number of choices
     * @return
     */
    public int getTimes() {
        return max-min+1;
    }

    public abstract double getValue();

    public abstract double getMin();

    public abstract double getMax();

    public void reset() {
        current = min-1;
    }

    public void advance() {
        if (current >= max) return;
        current++;
    }

    public abstract JPanel getPanel();
}
