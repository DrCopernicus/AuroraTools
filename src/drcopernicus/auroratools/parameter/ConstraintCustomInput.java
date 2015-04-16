package drcopernicus.auroratools.parameter;

import javax.swing.*;
import java.awt.*;

public class ConstraintCustomInput extends VariableSetting {
    protected double minVal;
    protected double maxVal;

    protected JTextField minField;
    protected JTextField maxField;

    public ConstraintCustomInput(String name, double minVal, double maxVal) {
        super(name, 1);
        this.minVal = minVal;
        this.maxVal = maxVal;
    }

    public void save() {
        minVal = Double.parseDouble(minField.getText());
        maxVal = Double.parseDouble(maxField.getText());
    }

    @Override
    public double getValue() {
        return 0;
    }

    @Override
    public double getMin() {
        return minVal;
    }

    @Override
    public double getMax() {
        return maxVal;
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

            minField = new JTextField(10);
            minField.setText(minVal+"");
            maxField = new JTextField(10);
            maxField.setText(maxVal + "");

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

    @Override
    public void updateText() {

    }
}
