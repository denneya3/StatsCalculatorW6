import java.util.Arrays;

public class StatsCalculator {
    private double[] values;
    private double[] sortedValues;
    private double[] outliers;

    /**
     * the default constructor
     */
    public StatsCalculator(){
        values = new double[20];
    }

    /**
     * constructor requiring a double array
     * @param values
     */
    public StatsCalculator(double[] values){
        this.values = values;
    }


    /**
     * calculates the maximum value of the values array
     * @return
     */
    public double calculateMax(){
        sortIfNotSorted();
        return sortedValues[sortedValues.length-1];
    } //- find the maximum value in the array

    public void sortData(){
        double [] tempValues = new double[values.length];
        for (int i = 0; i < values.length; i++){
            tempValues[i] = values[i];
        }
        //memory issue, values were overriden before by Arrays.sort method
        sortedValues = tempValues;
        Arrays.sort(sortedValues);
    }  //sorts the data

    public double calculateMin(){
        sortIfNotSorted();
        return sortedValues[0];
    } //- finds the minimum value in the array

    public double calculateFirstQuartile(){
        int c = 0;
        for (int x = 0; (double) x < (sortedValues.length/2); x++){
            c++;
        }
        double[] firstHalfAL = new double[c];
        c = 0;
        for (int x = 0; (double) x < (sortedValues.length/2); x++){
            firstHalfAL[c] = sortedValues[x];
            c++;
        }

        double[] firstHalf = new double[firstHalfAL.length];
        for (int i = 0; i < firstHalfAL.length; i++){
            firstHalf[i]  = (double) firstHalfAL[i];
        }
        return calculateMedianOf(firstHalf);
    } //- finds the value of the first quartile. Please note that I exclude the median for calculating the quartiles
    public double calculateThirdQuartile(){
        int c = 0;
        for (int x = (int) (sortedValues.length/2.0+0.5); x < sortedValues.length; x++){
            c++;
        }
        double[] secondHalfAL = new double[c];
        c = 0;
        for (int x = (int) (sortedValues.length/2.0+0.5); x < sortedValues.length; x++){
            secondHalfAL[c] = sortedValues[x];
            c++;
        }

        double[] secondHalf = new double[secondHalfAL.length];
        for (int i = 0; i < secondHalfAL.length; i++){
            secondHalf[i]  = (double) secondHalfAL[i];
        }
        return calculateMedianOf(secondHalf);
    } //- finds the value of the third quartile. Please note that I exclude the median for calculating the quartiles

    public double calculateMedian(){
        return calculateMedianOf(sortedValues);
    } //- finds the value of the median

    private double calculateMedianOf(double[] sortedArray){
        double median = -1; // redundant number
        if ((sortedArray.length & 2) == 0){ // its even
            median = ((sortedArray[sortedArray.length/2] + sortedArray[sortedArray.length/2-1] ) )/2;
        } else {
            median = sortedArray[sortedArray.length/2];
        }
        return median;
    }

    public double calculateSum(){
        double sum = 0;
        for (double v : values){
            sum+=v;
        }
        return sum;
    } //- finds the sum of the array

    public double calculateMean(){
        return calculateSum()/(values.length);
    } //- find the mean of the array

    public void print(){
        for (double v : values){
            System.out.print(v+", ");
        }
        System.out.println();
    } //- prints the data in a single line. Must use an enhanced for loop

    public void printSorted(){
        sortIfNotSorted();
        for (double v : sortedValues){
            System.out.print(v+", ");
        }
        System.out.println();
    } //- prints the sorted data in a single line. Must use an enhanced for loop

    public void printFiveNumberSummary(){
        System.out.println("The Five Number Summary is:");
        if (outliers == null)
            calculateOutliers();
        System.out.print("Minimum: "+calculateMin()+"\nFirst Quartile: "+calculateFirstQuartile()+"\nMedian: "+calculateMedian()+"\nThird Quartile: "+calculateThirdQuartile()+"\nMaximum: "+calculateMax()+"\nOutliers: "); //more
        for (double o : outliers){
            System.out.print(o+", ");
        }
    } // prints the five number summary as shown below.

    // Find any outliers by using the 1.5IQR rule, store them in an array called outliers, then create a print function that will print outliers and call that in your 5 number summary print method.
    private double calculateIQR(){
        return calculateThirdQuartile()-calculateFirstQuartile();
    }

    private boolean isOutlier(double value){
        double q1 = calculateFirstQuartile();
        double q3 = calculateThirdQuartile();
        double iqr = calculateIQR();
        return (value<q1-(1.5*iqr))||(value>q3+(1.5*iqr));
    }

    public void calculateOutliers(){

        int c = 0;
        for (double x : values){
            if (isOutlier(x))
                c++;
        }
        outliers = new double[c];
        c = 0;
        for (double x : values){
            if (isOutlier(x)){
                outliers[c] = x;
                c++;
            }

        }
    }

    private void sortIfNotSorted(){
        if (sortedValues == null)
            sortData();
    }


}
