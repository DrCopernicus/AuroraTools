package com.github.drcopernicus.auroratools;

public class Settings {	
	//Fancy
	public String[] shipName = {""};
	public String[] shipClass = {""};
	
	//Independent variables
	public double[] techFeedEfficiency = new double[1];
	public double[] techMagazineEjection = new double[1];
	public double[] techEnginePower = new double[1];
	public double[] techFuelConsumption = new double[1];
	public double[] techBaseBuildRate = new double[1];
	public double[] techGeoSensorRank = new double[1];
	public double[] techGravSensorRank = new double[1];
	public double[] techArmorWeight = new double[1];

	public boolean[] commercial = new boolean[1];

	public VariableSetting deploymentTime = new VariableSetting(24, 24, 1);
	public VariableSetting armorRating = new VariableSetting(1, 100, 1);
	
	public VariableSetting gravSurveyPoints = new VariableSetting(0, 0, 1);
	public VariableSetting geoSurveyPoints = new VariableSetting(0, 0, 1);

	public VariableSetting numberOfBridges = new VariableSetting(1, 1, 1);
	
	public VariableSetting numberOfMaintStorage = new VariableSetting(0, 0, 1);
	public VariableSetting numberOfEngineerSpaces = new VariableSetting(1, 1, 1);
	
	public VariableSetting magazineNumber = new VariableSetting(0, 0, 1);
	public VariableSetting magazineSize = new VariableSetting(1, 30, 1);
	public VariableSetting magazineHTK = new VariableSetting(1, 10, 1);
	
	public VariableSetting engineSize = new VariableSetting(1, 50, 1);
	public VariableSetting numberOfEngines = new VariableSetting(1, 3, 1);
	public VariableSetting enginePowerMod = new VariableSetting(0.1, 3.0, 0.05);
	public VariableSetting fuelReserves = new VariableSetting(500, 5000, 500);
	
	public VariableSetting[] listOfSettings = new VariableSetting[]{armorRating, deploymentTime, gravSurveyPoints, geoSurveyPoints, numberOfBridges, numberOfMaintStorage, numberOfEngineerSpaces, magazineNumber, magazineSize, magazineHTK, engineSize, numberOfEngines, fuelReserves, enginePowerMod};
	
	//Dependent variables
	public double[] velocity = new double[2];
	public double[] distance = new double[2];
	public double[] ammoCapacity = new double[2];
	public double[] mass = new double[2];
	public double[] buildTime = new double[2];
	public double[] annualFailureRate = new double[2];
}
