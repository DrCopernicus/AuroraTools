package drcopernicus.auroratools.parameter;

import javax.swing.*;
import java.awt.*;

public class ParameterCustomInput extends VariableSetting {
    protected double minVal;
    protected double maxVal;
    protected double spacing;

    protected JTextField minField;
    protected JTextField maxField;
    protected JTextField spacingField;

    public ParameterCustomInput(String name, double minVal, double maxVal, double spacing) {
        super(name, (int)((maxVal-minVal)/spacing));
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.spacing = spacing;
    }

    public void save() {
        minVal = Double.parseDouble(minField.getText());
        maxVal = Double.parseDouble(maxField.getText());
        spacing = Double.parseDouble(spacingField.getText());
        max = (int)((maxVal-minVal)/spacing);
        updateText();
    }

    @Override
    public double getValue() {
        return minVal + spacing * current;
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

            minField = new JTextField(20);
            minField.setText(minVal+"");
            maxField = new JTextField(20);
            maxField.setText(maxVal + "");
            spacingField = new JTextField(20);
            spacingField.setText(spacing+"");

            gbc.gridx++;
            jPanel.add(nameLabel, gbc);
            dropDownPanel.add(minField);
            dropDownPanel.add(maxField);
            dropDownPanel.add(spacingField);
            gbc.anchor = GridBagConstraints.EAST;
            gbc.gridx++;
            jPanel.add(dropDownPanel, gbc);
        }
        return jPanel;
    }

}
