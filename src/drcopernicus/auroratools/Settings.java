package drcopernicus.auroratools;

import drcopernicus.auroratools.parameter.VariableParameter;
import drcopernicus.auroratools.parameter.VariableSetting;
import drcopernicus.auroratools.parameter.VariableTech;

public class Settings {
	//Fancy
	public String[] shipName = {""};
	public String[] shipClass = {""};
	
	//Independent variables
	public VariableSetting techFeedEfficiency = new VariableTech(0.9, 0.9, 1, new double[]{0.75,0.8,0.85,0.9,0.92,0.94,0.96,0.98,0.99});
	public VariableSetting techMagazineEjection = new VariableTech(0.95, 0.95, 1, new double[]{0.7,0.8,0.85,0.9,0.93,0.95,0.97,0.98,0.99});
	public VariableSetting techEnginePower = new VariableTech(16, 16, 1, new double[]{5,8,12,16,20,25,32,40,50,60,80,100});
	public VariableSetting techFuelConsumption = new VariableTech(0.7, 0.7, 1, new double[]{1,0.9,0.8,0.7,0.6,0.5,0.4,0.3,0.25,0.2,0.16,0.125,0.1});
	public VariableSetting techBaseBuildRate = new VariableTech(560, 560, 1, new double[]{500,560,750,1000,1300,1600,2100,2750,3500,4600,6000,8000});
	public VariableSetting techGeoSensorRank = new VariableTech(1, 1, 1, new double[]{1,2,3,5});
	public VariableSetting techGravSensorRank = new VariableTech(1, 1, 1, new double[]{1,2,3,5});
	//conventional 2, duranium 5, high density duranium 6, composite 8, ceramic composite 10, laminate composite 12
	//compressed carbon 15, biphase carbon 18, crystalline composite 21, superdense 25, bonded superdense 30
	//coherent superdense 36, collapsium 45
	public VariableSetting techArmorWeight = new VariableTech(8, 8, 1, new double[]{2,5,6,8,10,12,15,18,21,25,30,45});

	public boolean[] commercial = new boolean[1];

	public VariableSetting deploymentTime = new VariableParameter(24, 24, 1);
	public VariableSetting armorRating = new VariableParameter(1, 100, 1);
	
	public VariableSetting gravSurveyPoints = new VariableParameter(0, 0, 1);
	public VariableSetting geoSurveyPoints = new VariableParameter(0, 0, 1);

	public VariableSetting numberOfBridges = new VariableParameter(1, 1, 1);
	
	public VariableSetting numberOfMaintStorage = new VariableParameter(0, 0, 1);
	public VariableSetting numberOfEngineerSpaces = new VariableParameter(1, 1, 1);
	
	public VariableSetting magazineNumber = new VariableParameter(0, 0, 1);
	public VariableSetting magazineSize = new VariableParameter(1, 30, 1);
	public VariableSetting magazineHTK = new VariableParameter(1, 10, 1);
	
	public VariableSetting engineSize = new VariableParameter(1, 50, 1);
	public VariableSetting numberOfEngines = new VariableParameter(1, 3, 1);
	public VariableSetting enginePowerMod = new VariableParameter(0.1, 3.0, 0.05);
	public VariableSetting fuelReserves = new VariableParameter(500, 5000, 500);
	
	public VariableSetting[] listOfSettings = new VariableSetting[]{
			techFeedEfficiency, techMagazineEjection, techEnginePower, techFuelConsumption,
			techBaseBuildRate, techGeoSensorRank, techGravSensorRank, techArmorWeight,
			armorRating, deploymentTime, gravSurveyPoints, geoSurveyPoints,
			numberOfBridges, numberOfMaintStorage, numberOfEngineerSpaces, magazineNumber,
			magazineSize, magazineHTK, engineSize, numberOfEngines,
			fuelReserves, enginePowerMod};
	
	//Dependent variables
	public double[] velocity = new double[2];
	public double[] distance = new double[2];
	public double[] ammoCapacity = new double[2];
	public double[] mass = new double[2];
	public double[] buildTime = new double[2];
	public double[] annualFailureRate = new double[2];
}
