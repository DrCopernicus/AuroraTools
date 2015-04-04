package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;

import javax.swing.*;

public abstract class ShipComponent {
    protected JPanel jPanel;
    protected JLabel jLabel;

    public abstract void save();
    public abstract void updateText();
    public abstract int getTimes();
    public abstract Parameter[] getParameters();
    public abstract JPanel getPanel();
}
