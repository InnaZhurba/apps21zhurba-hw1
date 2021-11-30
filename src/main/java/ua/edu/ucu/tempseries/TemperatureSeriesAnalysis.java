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
        checking();

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
        checking();

        double sum =0;
        double average =average();

        for(double temp: temperatureSeries)
            sum+=Math.pow(temp - average,2);

        return sum/length;
    }
    private void checking(){
        if(temperatureSeries.length==0 || temperatureSeries==null) {
            throw new IllegalArgumentException();
        }
    }

    public double min() {
       checking();

        double min= Double.POSITIVE_INFINITY;
        for(double temp:temperatureSeries)
            if(temp<min)
                min = temp;
        return min;
    }

    public double max() {
        checking();

        double max= Double.NEGATIVE_INFINITY;
        for(double temp:temperatureSeries)
            if(temp>max)
                max = temp;
        return max;
    }

    public double findTempClosestToZero() {
        checking();

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
       checking();

        double closestValue = Double.POSITIVE_INFINITY;

        for(double temp: temperatureSeries)
            if(Math.abs(temp-0) < Math.abs(closestValue-0) || (temp == -closestValue && temp>0))
                closestValue=temp;

        return closestValue;
    }

    public double[] findTempsLessThen(double tempValue) {
        checking();

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
        checking();

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
        checking();

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
