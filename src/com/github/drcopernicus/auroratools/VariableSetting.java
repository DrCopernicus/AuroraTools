package com.github.drcopernicus.auroratools;

public class VariableSetting {
	public double min;
	public double max;
	public double spacing;
	public double current;
	public int number;
	public int count;
	
	public int[] values;
	
	public VariableSetting(double min, double max, double spacing, double current) {
		this.min = min;
		this.max = max;
		this.spacing = spacing;
		this.current = current;
		number = (int)((max - min)/spacing) + 1;
		count = 0;
		values = new int[number];
	}
	
	public VariableSetting(double min, double max, double spacing) {
		this(min, max, spacing, min);
	}

	public VariableSetting() {
		 this(0.0, 0.0, 0.0, 0.0);
	}
	
	public void recalculate() {
		number = (int)((max - min)/spacing) + 1;
		count = 0;
		values = new int[number];
	}
}
