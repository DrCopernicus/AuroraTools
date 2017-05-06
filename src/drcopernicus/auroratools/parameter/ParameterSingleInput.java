package drcopernicus.auroratools.parameter;

import javax.swing.*;
import java.awt.*;

public class ParameterSingleInput extends VariableSetting {
    protected double current;

    protected JTextField field;

    public ParameterSingleInput(String name, double defaultValue) {
        super(name, 0);
        this.current = defaultValue;
    }

    public void save() {
        current = Double.parseDouble(field.getText());
        updateText();
    }

    @Override
    public double getValue() {
        return current;
    }

    @Override
    public double getMin() {
        return current;
    }

    @Override
    public double getMax() {
        return current;
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

            field = new JTextField(5);
            field.setText(current+"");

            gbc.gridx++;
            jPanel.add(nameLabel, gbc);
            dropDownPanel.add(field);
            gbc.anchor = GridBagConstraints.EAST;
            gbc.gridx++;
            jPanel.add(dropDownPanel, gbc);
        }
        return jPanel;
    }

}
