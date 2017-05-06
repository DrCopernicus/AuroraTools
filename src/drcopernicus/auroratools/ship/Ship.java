package drcopernicus.auroratools.ship;

import drcopernicus.auroratools.parameter.VariableSetting;

import java.text.DecimalFormat;

public class Ship {
    public double mass;             // HS
    public double buildPoints;
    public double rawEP;
    public double fuelUse;           // L/hr

    public int crew;
    public double deploymentTime;

    public int gravSurveyPoints;
    public int geoSurveyPoints;
    public int armorRating;
    public int armorWeight;          // armor rating/HS of armor (tech)
    public int fuelReserves;         // kL
    public int cargoCapacity;        // HS
    public int numberOfEngines;
    public int fuelPerAnnum;         // kL per annum

    public boolean military;

    public String engineTitle;       // e.g. "Commercial Ion Drive"

    private double magazineCapacity;
    private double velocity;          // km/s
    private double distance;          // Tm (billion km)
    private double daysOfFuel;        // days
    public double mSP;
    private double annualFailureRate;
    private double buildTime;
    private double armorWidth;
	private double aHS;

	public int hangarDeckCapacity;

	public double powerRequirement;
	public double powerGenerated;

	public int numberOfEngineeringSpaces;

	private double maintLife;

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
        hangarDeckCapacity = 0;
        military = false;
	}
	
	public boolean calculate(ShipConstraint shipConstraint) {
//		//MASS, CREW, AND BUILD POINT CALCULATIONS ===========================
//		//magazine
//		mass += s.magazineSize.current*s.magazineNumber.current;
//		buildPoints += 1; //this formula is yet to be determined, and seems to be even more unpredictable than all other formulae!
//		crew += s.magazineNumber.current*(int)(0.5+0.5*s.magazineSize.current);
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

        return (calculateCrewQuarters()
                && calculateArmor()
                && calculateEngine(shipConstraint)
                && calculateMaintenance()
                && calculatePower()
                && otherConstraints(shipConstraint));
	}

    private boolean calculateCrewQuarters() {
        if (deploymentTime < 0.5)
            crew = (int)Math.ceil((double)crew / 2);
        if (deploymentTime < 0.1)
            crew = (int)Math.ceil((double)crew / 3);
        double quarters = Math.ceil((Math.pow(deploymentTime,1/3)*crew)/0.04);

        mass += quarters * 0.04;
        buildPoints += quarters * 0.4;

        return true;
    }

    private boolean calculateArmor() {
        boolean done;
        double volume, area, strength, last, size = 0.0f;

        for (int layer = 0; layer < armorRating; layer++) {
            done = false;
            last = -1;
            volume = Math.ceil(mass + size);

            while (!done) {
                area = 4.0 * Math.PI * Math.pow((3.0 * volume) / (4.0 * Math.PI), 2.0/3.0);
                area = Math.round(area * 10.0) / 10.0;
                area *= layer + 1;
                strength = area / 4.0;

                size = Math.ceil((strength / (double)armorWeight * 10.0)) / 10.0f;
                volume = Math.ceil(mass + size);

                if (size == last)
                    done = true;

                last = size;
            }
        }

        mass += size;

        return true;
    }

    private boolean calculateEngine(ShipConstraint sc) {
        if (numberOfEngines == 0) {
            velocity = 1;
            if (!insideRange(velocity, sc.velocity)) return false;
        } else {
            velocity = 1000.0 * (rawEP/mass);
            if (!insideRange(velocity, sc.velocity)) return false;

            distance = velocity * fuelReserves * 3.6 / (rawEP * fuelUse) / 1000;
            daysOfFuel = fuelReserves / (rawEP * fuelUse) * (125.0/3.0);
        }
        return true;
    }

    private boolean calculateMaintenance() {
        if (!military)
            return true;

		if (numberOfEngineeringSpaces == 0) {
			annualFailureRate = mass/20.0;
		} else {
			//annual fail rate = mass/200 * 4/(100*percent of hull dedicated to engineering)
			//percent of hull dedicated to engineering = engineering spaces/mass
			annualFailureRate = mass * mass / (numberOfEngineeringSpaces * 5000);
		}
		mSP += buildPoints * (numberOfEngineeringSpaces/mass)/0.08;

		//maintLife =

        return true;
    }

    private boolean calculatePower()
    {
        return powerGenerated >= powerRequirement;
    }

    private boolean otherConstraints(ShipConstraint sc) {
        return (insideRange(mass, sc.mass)
                &&insideRange(cargoCapacity, sc.cargoCapacity)
                &&insideRange(hangarDeckCapacity, sc.hangarDeckCapacity)
                &&insideRange(distance, sc.distance));
    }

    private boolean insideRange(double constrained, VariableSetting range) {
        return constrained>=range.getMin()&&constrained<=range.getMax();
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
        if (fuelPerAnnum > 0) r += "Fuel Harvester " + fuelPerAnnum + " kL per annum, 10% to full in " + (9*fuelReserves/10)/fuelPerAnnum + " years" + "\n";

		r += "\n";

		//ENGINE STATS

        if (numberOfEngines != 0) {
            r += (int)(rawEP/(double)numberOfEngines) + "EP " + engineTitle + " (" + numberOfEngines + ")\tPower " + rawEP +"\n";
        }
        r += fuelReserves + " kL" + (numberOfEngines != 0 ? " " + df.format(distance) + " billion km (" + (int)daysOfFuel + " days)\n" : "");

		r += "\n";

		//SENSOR STATS
		if (gravSurveyPoints > 0) r += "Gravitational Survey Sensors\t" + gravSurveyPoints + " Survey Points Per Hour\n";
		if (geoSurveyPoints > 0) r += "Geological Survey Sensors\t" + geoSurveyPoints + " Survey Points Per Hour\n";

        //DESIGN CLASSIFICATION
        r += "This design is classed as a " + (military ? "Military" : "Commercial") + " Vessel for maintenance purposes\n";

		return r;
	}
}
