package drcopernicus.auroratools.parameter;

import javax.swing.*;
import java.awt.*;

public class ParameterWithDescriptions extends Parameter {
    protected String[] choicesString;

    public ParameterWithDescriptions(String name, Number[] choices, String[] choicesString) {
        super(name, choices);
        this.choicesString = choicesString;
    }

    @Override
    public JPanel getPanel() {
        if (jPanel == null) {
            jPanel = new JPanel();
            jPanel.setLayout(new GridBagLayout());
            JPanel dropDownPanel = new JPanel();
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 1;
            nameLabel = new JLabel(name);
            minField = new JComboBox(choicesString);
            minField.setSelectedIndex(min);
            minField.setPrototypeDisplayValue("hello world");
            maxField = new JComboBox(choicesString);
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

    public String getString() {
        return choicesString[current];
    }
}
