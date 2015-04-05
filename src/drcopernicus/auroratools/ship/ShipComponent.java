package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;

import javax.swing.*;

public abstract class ShipComponent {
    private JPanel jPanel;
    private JLabel namePanel;

    public final void save() {
        for (Parameter parameter : getParameters()) {
            parameter.save();
        }
    }
    public final void updateText() {
        for (Parameter parameter : getParameters()) {
            parameter.updateText();
        }
    }
    public final int getTimes() {
        int times = 1;
        for (Parameter parameter : getParameters()) {
            times *= parameter.getTimes();
        }
        return times;
    };
    public abstract Parameter[] getParameters();
    public final JPanel getPanel() {
        return jPanel;
    }
    public abstract void updateShip(Ship ship);
    protected final void makePanel(String name) {
        jPanel = new JPanel();
        namePanel = new JLabel(name);
        for (Parameter parameter : getParameters()) {
            jPanel.add(parameter.getPanel());
        }
    }
}
