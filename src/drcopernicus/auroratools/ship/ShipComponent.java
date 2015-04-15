package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;

import javax.swing.*;
import java.awt.*;

public abstract class ShipComponent {
    private String name;
    private JPanel jPanel;
    private JPanel componentPanel;
    private JLabel nameLabel;

    protected ShipComponent(String name) {
        this.name = name;
    }

    public abstract ShipComponent makeNew();

    public final void save() {
        for (Parameter parameter : getParameters()) {
            parameter.save();
        }
        updateText();
    }

    public final void updateText() {
        nameLabel.setText(name+" ("+getTimes()+"x)");
    }

    public final int getTimes() {
        int times = 1;
        for (Parameter parameter : getParameters()) {
            times *= parameter.getTimes();
        }
        return times;
    }

    public abstract Parameter[] getParameters();

    public final JPanel getPanel() {
        if (jPanel == null) {
            GridBagConstraints gbc = new GridBagConstraints();

            jPanel = new JPanel();
            jPanel.setLayout(new GridBagLayout());

            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.anchor = GridBagConstraints.WEST;
            nameLabel = new JLabel(name);
            jPanel.add(nameLabel, gbc);

            componentPanel = new JPanel();
            componentPanel.setLayout(new GridBagLayout());
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            for (Parameter parameter : getParameters()) {
                gbc.gridy++;
                componentPanel.add(parameter.getPanel(), gbc);
            }
            jPanel.add(componentPanel);

            componentPanel.setBorder(BorderFactory.createEtchedBorder());
        }
        return jPanel;
    }

    public abstract void updateShip(Ship ship);

    public String toString() {
        return name;
    }
}
