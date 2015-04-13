package drcopernicus.auroratools.parameter;

import javax.swing.*;
import java.awt.*;

public class Parameter {
    protected String name;
    protected Number[] choices;
    protected int current;
    protected int min;
    protected int max;

    protected JPanel jPanel;
    protected JLabel nameLabel;
    protected JComboBox minField;
    protected JComboBox maxField;

    public Parameter(String name, Number[] choices) {
        this.name = name;
        this.choices = choices;
        this.min = 0;
        this.max = choices.length-1;
        current = min;
    }

    public void save() {
        min = minField.getSelectedIndex();
        max = maxField.getSelectedIndex();
        updateText();
    }

    public void updateText() {
        nameLabel.setText(name+" ("+getTimes()+"x)");
    }

    public double getValue() {
        return choices[current].doubleValue();
    }

    public double getMin() {
        return choices[min].doubleValue();
    }

    public double getMax() {
        return choices[max].doubleValue();
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
        if (jPanel == null) {
            jPanel = new JPanel();
            jPanel.setLayout(new GridBagLayout());
            JPanel dropDownPanel = new JPanel();
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 1;
            nameLabel = new JLabel(name);
            minField = new JComboBox(choices);
            minField.setSelectedIndex(min);
            minField.setPrototypeDisplayValue("hello world");
            maxField = new JComboBox(choices);
            maxField.setPrototypeDisplayValue("hello world");
            maxField.setSelectedIndex(max);
            gbc.gridx++;
            jPanel.add(nameLabel, gbc);
            dropDownPanel.add(minField);
            dropDownPanel.add(maxField);
            gbc.anchor = GridBagConstraints.EAST;
            gbc.gridx++;
            jPanel.add(dropDownPanel, gbc);
        }
        return jPanel;
    }
}
