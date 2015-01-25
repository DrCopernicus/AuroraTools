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

	public VariableSetting deploymentTime = new VariableSetting();
	public VariableSetting armorRating = new VariableSetting();
	
	public VariableSetting gravSurveyPoints = new VariableSetting();
	public VariableSetting geoSurveyPoints = new VariableSetting();

	public VariableSetting numberOfBridges = new VariableSetting();
	
	public VariableSetting numberOfMaintStorage = new VariableSetting();
	public VariableSetting numberOfEngineerSpaces = new VariableSetting();
	
	public VariableSetting magazineNumber = new VariableSetting();
	public VariableSetting magazineSize = new VariableSetting();
	public VariableSetting magazineHTK = new VariableSetting();
	
	public VariableSetting engineSize = new VariableSetting();
	public VariableSetting numberOfEngines = new VariableSetting();
	public VariableSetting fuelReserves = new VariableSetting();
	public VariableSetting enginePowerMod = new VariableSetting();
	
	public VariableSetting[] listOfSettings = new VariableSetting[]{armorRating, deploymentTime, gravSurveyPoints, geoSurveyPoints, numberOfBridges, numberOfMaintStorage, numberOfEngineerSpaces, magazineNumber, magazineSize, magazineHTK, engineSize, numberOfEngines, fuelReserves, enginePowerMod};
	
	//Dependent variables
	public double[] velocity = new double[2];
	public double[] distance = new double[2];
	public double[] ammoCapacity = new double[2];
	public double[] mass = new double[2];
	public double[] buildTime = new double[2];
	public double[] annualFailureRate = new double[2];
}
