import java.util.Arrays;

public class StatsCalculator {
    private double[] values;
    private double[] sortedValues;
    private double[] outliers;

    /**
     * the default constructor
     * initializes values as an array of 20 values that are 0
     */
    public StatsCalculator(){
        values = new double[20];
    }

    /**
     * constructor requiring a double array of values
     * @param values values that will be set to values
     */
    public StatsCalculator(double[] values){
        this.values = values;
    }

    /**
     * calculates the maximum value of values and returns it
     * @return double maximum value
     */
    public double calculateMax(){
        sortIfNotSorted();
        return sortedValues[sortedValues.length-1];
    } //- find the maximum value in the array

    /**
     * calculates the minimum value of values and returns it
     * @return double minimum value
     */
    public double calculateMin(){
        sortIfNotSorted();
        return sortedValues[0];
    } //- finds the minimum value in the array

    /**
     * sorts the data (values) and sets it to a different array sortedValues
     */
    public void sortData(){
        double [] tempValues = new double[values.length];
        for (int i = 0; i < values.length; i++){
            tempValues[i] = values[i];
        }
        //memory issue, values were overriden before by Arrays.sort method
        sortedValues = tempValues;
        Arrays.sort(sortedValues);
    }  //sorts the data

    /**
     * calculates the first Quartile of values and returns it
     * the median is excluded in calculations
     * @return double the first quartile
     */
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

    /**
     * calculates the third quartile of values and returns it
     * the median is excluded in calculations
     * @return double the third quartile
     */
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
    }

    /**
     * calculates the median of values and returns it
     * @return double median
     */
    public double calculateMedian(){
        return calculateMedianOf(sortedValues);
    } //- finds the value of the median

    /**
     * calculates the median of the provided sortedArray and returns it
     * @param sortedArray - the sorted array where the median will be found
     * @return double median of the provided values
     */
    private double calculateMedianOf(double[] sortedArray){
        double median = -1; // redundant number
        if ((sortedArray.length & 2) == 0){ // its even
            median = ((sortedArray[sortedArray.length/2] + sortedArray[sortedArray.length/2-1] ) )/2;
        } else {
            median = sortedArray[sortedArray.length/2];
        }
        return median;
    }

    /**
     * calculates the sum of values and returns it
     * @return double sum
     */
    public double calculateSum(){
        double sum = 0;
        for (double v : values){
            sum+=v;
        }
        return sum;
    } //- finds the sum of the array

    /**
     * calculates the mean (average) of values and returns it
     * @return double mean
     */
    public double calculateMean(){
        return calculateSum()/(values.length);
    } //- find the mean of the array

    /**
     * prints all the values
     */
    public void print(){
        for (double v : values){
            System.out.print(v+", ");
        }
        System.out.println();
    } //- prints the data in a single line. Must use an enhanced for loop

    /**
     * prints all the sorted values (in sorted format)
     */
    public void printSorted(){
        sortIfNotSorted();
        for (double v : sortedValues){
            System.out.print(v+", ");
        }
        System.out.println();
    } //- prints the sorted data in a single line. Must use an enhanced for loop

    /**
     * prints the five number summary and outliers of values
     */
    public void printFiveNumberSummary(){
        System.out.println("The Five Number Summary is:");
        if (outliers == null) {
            calculateOutliers();
        }
        System.out.print("Minimum: "+calculateMin()+"\nFirst Quartile: "+calculateFirstQuartile()+"\nMedian: "+calculateMedian()+"\nThird Quartile: "+calculateThirdQuartile()+"\nMaximum: "+calculateMax()+"\nOutliers: "); //more
        for (double o : outliers){
            System.out.print(o+", ");
        }
    } // prints the five number summary as shown below.

    // Find any outliers by using the 1.5IQR rule, store them in an array called outliers, then create a print function that will print outliers and call that in your 5 number summary print method.

    /**
     * calculates the Interquartile Range
     * @return double the Interquartile Range
     */
    private double calculateIQR(){
        return calculateThirdQuartile()-calculateFirstQuartile();
    }

    /**
     * determines if the provided value is an outlier
     * @param value a double
     * @return boolean true if the value is an outlier
     */
    private boolean isOutlier(double value){
        double q1 = calculateFirstQuartile();
        double q3 = calculateThirdQuartile();
        double iqr = calculateIQR();
        return (value<q1-(1.5*iqr))||(value>q3+(1.5*iqr));
    }

    /**
     * calculates all of the outliers of values and stores it in the outliers array
     */
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

    /**
     * checks whether the data has been sorted already and sorts the data if this hasn't occured
     * it calls the sortData method to sort
     */
    private void sortIfNotSorted(){
        if (sortedValues == null) {
            sortData();
        }
    }


}
