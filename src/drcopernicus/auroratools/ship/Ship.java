package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.AuroraTools;
import drcopernicus.auroratools.Settings;

import java.text.DecimalFormat;

public class Ship {
    public double mass;             // HS
    public double buildPoints;

    public int crew;
    public int deploymentTime;
    public int gravSurveyPoints;
    public int geoSurveyPoints;
    public int armorRating;
    public int armorWeight;          // armor rating/HS of armor (tech)
    public int fuelReserves;         // kL
    public int cargoCapacity;        // HS
    public int numberOfEngines;

    public boolean commercial;

    public String engineTitle;       // e.g. "Commercial Ion Drive"

	public double magazineCapacity;
	public double rawEP;
	public double velocity;          // km/s
	public double fuelUse;           // L/hr
	public double distance;          // Tm (billion km)
	public double daysOfFuel;        // days
	public double mSP;
	public double annualFailureRate;
	public double buildTime;
	public double armorWidth;
	public double aHS;

	public Ship() {
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
        buildTime = 0;
        gravSurveyPoints = 0;
        geoSurveyPoints = 0;
        armorWidth = 0;
        aHS = 0;
        commercial = false;
	}
	
	public void calculate() {
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
//		//MAGAZINE ===========================================================
//		//this formula took ages to figure out, and it is sometimes off by just a little tiny bit
//		magazineCapacity = s.magazineNumber.current*(int)((s.magazineSize.current-((s.magazineHTK.current-1.0)*Math.pow(s.magazineSize.current,0.669985)*0.04))*s.techFeedEfficiency.current*20);
//
//		//BUILD TIME =========================================================
//		buildTime = buildPoints / (s.techBaseBuildRate.current * (1+(((mass/100)-1)/2)));

        calculateCrewQuarters();
        calculateArmor();
        calculateEngine();
        calculateMaintenance();
	}

    private void calculateCrewQuarters() {
        double reqCrewQuarters = Math.pow(deploymentTime,1/3)*crew/50.0;
        mass += Math.round(reqCrewQuarters*10)/10.0;
        buildPoints += Math.round(reqCrewQuarters*10);
    }

    private void calculateArmor() {
        //this algorithm is accurate up to ~20 for small ships (800 HS), and only varies from the actual value for larger ships by less than 1 HS (in most cases)
		double ar;
		double sbar;
		double asr = 0;
		int tempArmorLevels = armorRating;
		while (tempArmorLevels >= 0) {
			ar = armorRating - tempArmorLevels + 1;
			sbar = Math.round(mass+aHS*1.0461); //this constant seems to work well
			asr = Math.pow(0.75*Math.sqrt(Math.PI)*sbar, 2.0/3.0); //not the true ASR as shown on the construction screen
			aHS = Math.round((ar*asr/armorWeight)*10)/10.0;
			tempArmorLevels--;
		}
		mass += aHS;
		buildPoints += aHS * armorWeight;
		armorWidth = asr;
    }

    private void calculateEngine() {
        System.out.println(mass);

        velocity = 1000.0 * (rawEP/mass);
		distance = velocity * fuelReserves * 3.6 / (rawEP * fuelUse) / 1000;
		daysOfFuel = fuelReserves / (rawEP * fuelUse) * (125.0/3.0);
    }

    private void calculateMaintenance() {
//		//MAINTENANCE ========================================================
//		if (s.numberOfEngineerSpaces.current == 0) {
//			annualFailureRate = mass/20.0;
//		} else {
//			//annual fail rate = mass/200 * 4/(100*percent of hull dedicated to engineering)
//			//percent of hull dedicated to engineering = engineering spaces/mass
//			annualFailureRate = mass * mass / (s.numberOfEngineerSpaces.current * 5000);
//		}
//		mSP = buildPoints * (s.numberOfEngineerSpaces.current/mass)/0.08 + 1000 * s.numberOfMaintStorage.current;
    }

	public String toString() {
		DecimalFormat df = new DecimalFormat("0.0");

		//TOP BAR
		String r = "shipName class shipClass" + "\t" + ((int)Math.ceil(mass) * 50) + " tons\t" + crew + " Crew\t" + df.format(buildPoints) + " BP\n";
		//SECOND BAR
		r += (int)(velocity) + "km/s\tArmor " + armorRating + "-" + (int)armorWidth + "\n";
		//MAINT BAR
		r += "MSP " + (int)mSP + "\tAFR " + (int)(annualFailureRate*100) + "%\tIFR " + df.format(annualFailureRate/0.72) + "%\n";
		//DEPLOYMENT BAR
		r += "Intended Deployment Time: " + deploymentTime + " months\n";
		//MAGAZINE BAR
		if (magazineCapacity > 0) r += "Magazine " + (int)magazineCapacity + "\n";
        if (cargoCapacity > 0) r += "Cargo Capacity " + cargoCapacity * 50 + "\n";

		r += "\n";

		//ENGINE STATS
		r += (int)(rawEP/(double)numberOfEngines) + "EP\t" + engineTitle + "(" + numberOfEngines + ")";
		r += fuelReserves + " kL\t" + df.format(distance) + " Tm (" + (int)daysOfFuel + " days)\n";

		r += "\n";

		//SENSOR STATS
		if (gravSurveyPoints > 0) r += "Gravitational Survey Sensors\t" + gravSurveyPoints + " Survey Points Per Hour\n";
		if (geoSurveyPoints > 0) r += "Geological Survey Sensors\t" + geoSurveyPoints + " Survey Points Per Hour\n";

		return r;
	}
}
