package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.AuroraTools;
import drcopernicus.auroratools.Settings;

import java.text.DecimalFormat;

public class Ship {
    public double mass; // HS
    public double buildPoints;
    public int crew;
    public int deploymentTime;
    public boolean commercial;

	public double magazineCapacity;
	public double rawEP;
	public double velocity; // km/s
	public double fuelUse; // L/hr
	public double distance; // Tm (billion km)
	public double daysOfFuel; // days
	public double mSP;
	public double annualFailureRate;
	public double reqCrewQuarters;
	public double buildTime;
	public double gravSurveyPoints;
	public double geoSurveyPoints;
	public double armorWidth;
	public double aHS;
	private Settings s = AuroraTools.settings;
	
	public Ship() {
		calculate();
	}
	
	public void calculate() {
		mass = 0;
		magazineCapacity = 0;
		rawEP = 0;
		velocity = 0;
		fuelUse = 0;
		daysOfFuel = 0;
		crew = 0;
		mSP = 0;
		annualFailureRate = 0;
		buildPoints = 0;
		reqCrewQuarters = 0;
		buildTime = 0;
		gravSurveyPoints = 0;
		geoSurveyPoints = 0;
		armorWidth = 0;
		aHS = 0;
		commercial = false;
//
//		//MASS, CREW, AND BUILD POINT CALCULATIONS ===========================
//		//magazine
//		mass += s.magazineSize.current*s.magazineNumber.current;
//		buildPoints += 1; //this formula is yet to be determined, and seems to be even more unpredictable than all other formulae!
//		crew += s.magazineNumber.current*(int)(0.5+0.5*s.magazineSize.current);
//		//fuel tanks
//		mass += s.fuelReserves.current / 50.0;
//		buildPoints += s.fuelReserves.current / 25; //the cost actually changes based on the size of the component, so I based it off of ultra large tanks
//		//maintenance and engineering spaces
//		mass += s.numberOfMaintStorage.current * 5 + s.numberOfEngineerSpaces.current;
//		crew += s.numberOfMaintStorage.current * 2 + s.numberOfEngineerSpaces.current * 5;
//		buildPoints += s.numberOfMaintStorage.current * 15 + s.numberOfEngineerSpaces.current * 10;
//		//crew
//		//bridge
//		mass += s.numberOfBridges.current;
//		crew += s.numberOfBridges.current * 5;
//		buildPoints += s.numberOfBridges.current * 10;
//		//sensor
//		while (s.gravSurveyPoints.current > gravSurveyPoints) {
//			//tries to optimize the grav sensors on the ship by filling out the biggest (and therefore most cost effective) sensors first
//			if (s.techGravSensorRank.current >= 4 && s.gravSurveyPoints.current - gravSurveyPoints > 5) {
//				gravSurveyPoints += 5;
//				buildPoints += 300;
//			} else if (s.techGravSensorRank.current >= 3 && s.gravSurveyPoints.current - gravSurveyPoints > 3) {
//				gravSurveyPoints += 3;
//				buildPoints += 200;
//			} else if (s.techGravSensorRank.current >= 2 && s.gravSurveyPoints.current - gravSurveyPoints > 2) {
//				gravSurveyPoints += 2;
//				buildPoints += 150;
//			} else {
//				gravSurveyPoints += 1;
//				buildPoints += 100;
//			}
//			mass += 5;
//			crew += 10;
//		}
//		while (s.geoSurveyPoints.current > geoSurveyPoints) {
//			//tries to optimize the geo sensors on the ship by filling out the biggest (and therefore most cost effective) sensors first
//			if (s.techGeoSensorRank.current >= 4 && s.geoSurveyPoints.current - geoSurveyPoints > 5) {
//				geoSurveyPoints += 5;
//				buildPoints += 300;
//			} else if (s.techGeoSensorRank.current >= 3 && s.geoSurveyPoints.current - geoSurveyPoints > 3) {
//				geoSurveyPoints += 3;
//				buildPoints += 200;
//			} else if (s.techGeoSensorRank.current >= 2 && s.geoSurveyPoints.current - geoSurveyPoints > 2) {
//				geoSurveyPoints += 2;
//				buildPoints += 150;
//			} else {
//				geoSurveyPoints += 1;
//				buildPoints += 100;
//			}
//			mass += 5;
//			crew += 10;
//		}
//		//armor
//		//this algorithm is accurate up to ~20 for small ships (800 HS), and only varies from the actual value for larger ships by less than 1 HS (in most cases)
//		double ar = 0;
//		double sbar = 0;
//		double asr = 0;
//		int tempArmorLevels = (int)s.armorRating.current;
//		while (tempArmorLevels >= 0) {
//			ar = s.armorRating.current - tempArmorLevels + 1;
//			sbar = Math.round(mass+aHS*1.0461); //this constant seems to work well
//			asr = Math.pow(0.75*Math.sqrt(Math.PI)*sbar, 2.0/3.0); //not the true ASR as shown on the construction screen
//			aHS = Math.round((ar*asr/s.techArmorWeight.current)*10)/10.0;
//			tempArmorLevels--;
//		}
//		mass += aHS;
//		buildPoints += aHS * s.techArmorWeight.current;
//		armorWidth = asr;
//
//		//MAGAZINE ===========================================================
//		//this formula took ages to figure out, and it is sometimes off by just a little tiny bit
//		magazineCapacity = s.magazineNumber.current*(int)((s.magazineSize.current-((s.magazineHTK.current-1.0)*Math.pow(s.magazineSize.current,0.669985)*0.04))*s.techFeedEfficiency.current*20);
//
//		//ENGINE =============================================================
//		fuelUse = s.techFuelConsumption.current * (1 - s.engineSize.current * 0.01) * Math.pow(s.enginePowerMod.current, 2.5);
//
//		velocity = 1000 * (double)(rawEP)/mass;
//		distance = velocity * s.fuelReserves.current * 3.6 / (rawEP * fuelUse) / 1000;
//		daysOfFuel = s.fuelReserves.current / (rawEP * fuelUse) * (125.0/3.0);
//
//		//MAINTENANCE ========================================================
//		if (s.numberOfEngineerSpaces.current == 0) {
//			annualFailureRate = mass/20.0;
//		} else {
//			//annual fail rate = mass/200 * 4/(100*percent of hull dedicated to engineering)
//			//percent of hull dedicated to engineering = engineering spaces/mass
//			annualFailureRate = mass * mass / (s.numberOfEngineerSpaces.current * 5000);
//		}
//		mSP = buildPoints * (s.numberOfEngineerSpaces.current/mass)/0.08 + 1000 * s.numberOfMaintStorage.current;
//
//		//BUILD TIME =========================================================
//		buildTime = buildPoints / (s.techBaseBuildRate.current * (1+(((mass/100)-1)/2)));

        calculateCrewQuarters();
        calculateEngine();
	}

    public void calculateCrewQuarters() {
        reqCrewQuarters = Math.pow(deploymentTime,1/3)*crew/50.0;
        mass += Math.round(reqCrewQuarters*10)/10.0;
        buildPoints += Math.round(reqCrewQuarters*10);
    }

    public void calculateEngine() {
        velocity = 1000 * rawEP/mass;
    }

	public String toString() {
		DecimalFormat df = new DecimalFormat("0.0");

		//TOP BAR
		String r = s.shipName[0] + " class " + s.shipClass[0] + "\t" + ((int)Math.ceil(mass) * 50) + " tons\t" + crew + " Crew\t" + df.format(buildPoints) + " BP\n";
		//SECOND BAR
//		r += (int)(velocity) + " km/s\tArmor " + (int)s.armorRating.current + "-" + (int)armorWidth + "\n";
//		//MAINT BAR
//		r += "MSP " + (int)mSP + "\tAFR " + (int)(annualFailureRate*100) + "%\tIFR " + df.format(annualFailureRate/0.72) + "%\n";
//		//DEPLOYMENT BAR
//		r += "Intended Deployment Time: " + s.deploymentTime.current + " months\n";
//		//MAGAZINE BAR
//		if (magazineCapacity > 0) r += "Magazine " + (int)magazineCapacity + "\n";
//
//		r += "\n";
//
//		//ENGINE STATS
//		r += (int)s.numberOfEngines.current + "x" + (int)(s.techEnginePower.current * s.engineSize.current * s.enginePowerMod.current) + "EP\t" + s.enginePowerMod.current + "x power\n";
//		r += (int)s.fuelReserves.current + " kL\t" + df.format(distance) + " Tm (" + (int)daysOfFuel + " days)\n";
//
//		r += "\n";
//
//		//SENSOR STATS
//		if (gravSurveyPoints > 0) r += "Gravitational Survey Sensors\t" + (int)gravSurveyPoints + " Survey Points Per Hour\n";
//		if (geoSurveyPoints > 0) r += "Geological Survey Sensors\t" + (int)geoSurveyPoints + " Survey Points Per Hour\n";

		return r;
	}
}
