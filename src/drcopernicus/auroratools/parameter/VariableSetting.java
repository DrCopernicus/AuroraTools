package drcopernicus.auroratools.parameter;

public class VariableSetting {
	protected double min;
	protected double max;
	protected double spacing;
	public double current;
	protected int number;
	public int count;
	
	public int[] values;
	
	public void setAll(double min, double max, double spacing) {
		
	}
	
	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public double getSpacing() {
		return spacing;
	}
	
	public double getNumber() {
		return number;
	}
	
	public void advanceTo(int i) {
		
	}

	public double getValueAt(int i) {
		return -1;
	}
}
