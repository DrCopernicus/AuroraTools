package drcopernicus.auroratools.parameter;

public class VariableParameter extends VariableSetting {	
	public VariableParameter(double min, double max, double spacing) {
		setAll(min, max, spacing);
	}
	
	public void setAll(double min, double max, double spacing) {
		this.min = min;
		this.max = max;
		this.spacing = spacing;
		this.current = min;
		number = (int)((max - min)/spacing) + 1;
		count = 0;
		values = new int[number];
	}
	
	public void advanceTo(int i) {
		current = min + spacing * i;
		count = i;
	}
	
	public double getValueAt(int i) {
		return min + spacing * i;
	}
}
