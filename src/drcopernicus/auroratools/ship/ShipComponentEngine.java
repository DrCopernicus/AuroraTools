package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.Parameter;

import javax.swing.*;

public class ShipComponentEngine extends ShipComponent {
    private Parameter count;
    private Parameter engineSize;

    public ShipComponentEngine() {
        count = new Parameter("Count", new Integer[]{0,1,2,3,4,5},0,5);
        engineSize = new Parameter("Size", new Integer[]{1,2,3,4,5},0,4);

        jPanel = new JPanel();
        jLabel = new JLabel("Engine");
        jPanel.add(count.getPanel());
        jPanel.add(engineSize.getPanel());
    }

    @Override
    public void save() {
        count.save();
        engineSize.save();
    }

    @Override
    public void updateText() {

    }

    @Override
    public int getTimes() {
        return count.getTimes() * engineSize.getTimes();
    }

    @Override
    public Parameter[] getParameters() {
        return new Parameter[]{count, engineSize};
    }

    @Override
    public JPanel getPanel() {
        return jPanel;
    }
}
