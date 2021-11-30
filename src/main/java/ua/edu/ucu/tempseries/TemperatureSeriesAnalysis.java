package ua.edu.ucu.tempseries;

import lombok.Getter;

import java.util.Arrays;
import java.util.InputMismatchException;

@Getter
public class TemperatureSeriesAnalysis {
    private double[] temperatureSeries;
    private int length;

    public TemperatureSeriesAnalysis() {
        temperatureSeries = new double[]{};
        length = temperatureSeries.length;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        if(temperatureSeries == null || temperatureSeries.length==0)
            throw new IllegalArgumentException();

        checkSeriesIsValid(temperatureSeries);
        this.length = temperatureSeries.length;
        this.temperatureSeries = Arrays.copyOf(temperatureSeries, temperatureSeries.length);
    }
    public void checkSeriesIsValid(double[] temperatureSeries){
        int minTemp = -273;
        for(int i=0;i<temperatureSeries.length;i++){
            if(temperatureSeries[i]<=minTemp)
                throw new InputMismatchException();
        }
    }

    public double average() {

        double sum = Arrays.stream(getTemperatureSeries()).sum();

        return sum/length;
    }

    public double deviation() {
        if(temperatureSeries.length==0 || temperatureSeries==null)
            throw new IllegalArgumentException();

        double sum =0;
        double average =average();

        for(double temp: temperatureSeries)
            sum+=Math.pow(temp - average,2);

        return sum/length;
    }

    public double min() {
        return 0;
    }

    public double max() {
        return 0;
    }

    public double findTempClosestToZero() {
        if(temperatureSeries.length==0 || temperatureSeries==null)
            throw new IllegalArgumentException();
        //загалом цю перевірку теж можна винести у фунцію окрему

        double minDiff = Double.POSITIVE_INFINITY;
        //double closestValue;
        //можна просто викликати findTempClosestToValue(0)
        for(double temp: temperatureSeries){
            if(Math.abs(temp-0) < Math.abs(minDiff-0) || (temp == -minDiff && temp>0)){
                minDiff = temp;
            }
            /*if(temp>0 && Math.abs(temp-0)==minDiff){

            }*/
        }
        return minDiff;
    }

    public double findTempClosestToValue(double tempValue) {
        if(temperatureSeries.length==0 || temperatureSeries==null)
            throw new IllegalArgumentException();

        double closestValue = Double.POSITIVE_INFINITY;

        for(double temp: temperatureSeries)
            if(Math.abs(temp-0) < Math.abs(closestValue-0) || (temp == -closestValue && temp>0))
                closestValue=temp;

        return closestValue;
    }

    public double[] findTempsLessThen(double tempValue) {
        if(temperatureSeries.length==0 || temperatureSeries==null)
            throw new IllegalArgumentException();

        int counter=0;
        double newArray[];
        for(int i=0;i<length;i++){
            if(temperatureSeries[i]<tempValue) {
                //double template[] = new double[i+1];
                double temp = temperatureSeries[i];
                temperatureSeries[i]= temperatureSeries[counter];
                temperatureSeries[counter] = temp;
                counter++;
            }
        }
        newArray = new double[counter];
        System.arraycopy(temperatureSeries, 0, newArray, 0, counter);
        //double newArray[] = temperatureSeries[0counter];
        return newArray;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        if(temperatureSeries.length==0 || temperatureSeries==null)
            throw new IllegalArgumentException();

        int counter=0;
        double newArray[];
        for(int i=0;i<length;i++){
            if(temperatureSeries[i]>=tempValue) {
                //double template[] = new double[i+1];
                double temp = temperatureSeries[i];
                temperatureSeries[i]= temperatureSeries[counter];
                temperatureSeries[counter] = temp;
                counter++;
            }
        }
        newArray = new double[counter];
        System.arraycopy(temperatureSeries, 0, newArray, 0, counter);
        return newArray;
    }

    public TempSummaryStatistics summaryStatistics() {
        if(temperatureSeries.length==0 || temperatureSeries==null)
            throw new IllegalArgumentException();

        //if(summaryStatistics == null)
        //    throw new IllegalArgumentException();

        return new TempSummaryStatistics(average(),deviation(),min(),max());
    }

    public int addTemps(double... temps) {
        checkSeriesIsValid(temps);

        if(length + temps.length >=temperatureSeries.length)
            changeSizeArr();

        for(int i=length;i<temps.length;i++)
            temperatureSeries[i]=temps[i-length];
        length+=temps.length;

        return length;
    }
    public void changeSizeArr(){
        double[] newTemperatureSeries = new double[temperatureSeries.length*2];
        for(int i=0;i<temperatureSeries.length;i++){
            newTemperatureSeries[i]=temperatureSeries[i];
        }
        temperatureSeries=newTemperatureSeries;
    }
}
