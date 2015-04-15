package drcopernicus.auroratools.parameter;

import javax.swing.*;
import java.awt.*;

public class Parameter extends VariableSetting {
    protected Number[] choices;

    protected JComboBox minField;
    protected JComboBox maxField;

    public Parameter(String name, Number[] choices) {
        super(name, choices.length-1);
        this.choices = choices;
    }

    public void save() {
        min = minField.getSelectedIndex();
        max = maxField.getSelectedIndex();
        updateText();
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
