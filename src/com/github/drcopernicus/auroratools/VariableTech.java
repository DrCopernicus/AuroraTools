package com.github.drcopernicus.auroratools;

public class VariableTech extends VariableSetting {
	private double[] choices;
	private int lowest;
	private int highest;
	private int choice;
	
	public VariableTech(double min, double max, double spacing, double[] choices) {
		this.choices = choices;
		setAll(min, max, spacing);
	}
	
	public void setAll(double min, double max, double spacing) {
		this.min = min;
		this.max = max;
		this.spacing = spacing;
		this.lowest = getCloseValue(min);
		this.highest = getCloseValue(max);
		this.choice = lowest;
		this.current = choices[choice];
		number = highest-lowest+1;
		count = 0;
		values = new int[number];
	}

	/**
	 * Returns the position of the smallest allowable value in choices[]. For example, far = 10 and choices = {9,11,13,15,17} returns 1 (because 11 is the smallest allowable value).
	 * @return
	 */
	private int getCloseValue(double far) {
		for (int i = 0; i < choices.length; i++) {
			if (far <= choices[i]) return i;
		}
		return choices.length-1;
	}
	
	public void advanceTo(int i) {
		choice = lowest + i;
		current = choices[choice];
		count = i;
	}
}
