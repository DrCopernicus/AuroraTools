package drcopernicus.auroratools.parameter;

import javax.swing.*;

public class Parameter {
    private String name;
    private Number[] choices;
    private int current;
    private int min;
    private int max;

    private JPanel jPanel;
    private JComboBox minField;
    private JComboBox maxField;
    private JLabel timesLabel;

    public Parameter(String name, Number[] choices) {
        this.name = name;
        this.choices = choices;
        this.min = 0;
        this.max = choices.length-1;
        current = min;

        jPanel = new JPanel();
        minField = new JComboBox(choices);
        minField.setSelectedIndex(min);
        maxField = new JComboBox(choices);
        maxField.setSelectedIndex(max);
        jPanel.add(minField);
        jPanel.add(maxField);
    }

    public void save() {
        min = minField.getSelectedIndex();
        max = maxField.getSelectedIndex();
        updateText();
    }

    public void updateText() {
        timesLabel.setText(getTimes()+"");
    }

    public double getValue() {
        return choices[current].doubleValue();
    }

    public void reset() {
        current = min-1;
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
        return max-min+1;
    }

    public JPanel getPanel() {
        return jPanel;
    }
}
