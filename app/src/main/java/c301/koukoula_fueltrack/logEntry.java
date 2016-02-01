package c301.koukoula_fueltrack;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by unkno on 2016-01-27.
 */
public class logEntry {

    private String date; //yyyy-mm-dd
    private String station; //eg costco
    private Double odometer; //entered in km, to 1 decimal place
    private String fuelGrade; //eg regular
    private Double fuelAmount; //entered in L, to 3 decimals
    private Double unitCost; //cents per L, 1 decimal place
    private Double fuelCost; //computed  in dollars, so to 2 decimal places.

    //DecimalFormat taught to me by Christopher Hegberg
    DecimalFormat oneDec = new DecimalFormat("#.#");
    DecimalFormat twoDec = new DecimalFormat("#.##");
    DecimalFormat threeDec = new DecimalFormat("#.###");
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Double getOdometer() {
        return odometer;
    }

    public void setOdometer(Double odometer) {
        this.odometer = odometer;
    }

    public String getFuelGrade() {
        return fuelGrade;
    }

    public void setFuelGrade(String fuelGrade) {
        this.fuelGrade = fuelGrade;
    }

    public Double getFuelAmount() {
        return fuelAmount;
    }

    public void setFuelAmount(Double fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public Double getFuelCost() {
        return fuelCost;
    }

    //http://stackoverflow.com/questions/11701399/round-up-to-2-decimal-places-in-java
    public void calcFuelCost() {
        //Calculates the total cost of the fuel.
        this.fuelCost = Math.round((this.fuelAmount * this.unitCost/100) * 100.0) / 100.0;
    }


    @Override
    public String toString() {
        //Allows printing of the class.
        return  "Date: "+ this.date + ", Station: "
                + this.station + ", Odometer Reading: "
                + oneDec.format(this.odometer) + "KM, Fuel Grade: "
                + this.fuelGrade + ", Fuel Amount:  "
                + threeDec.format(this.fuelAmount) + "L, Unit Cost: "
                + oneDec.format(this.unitCost) + "cents/L, Fuel Cost: $"
                + twoDec.format(this.fuelCost);

    }
}
