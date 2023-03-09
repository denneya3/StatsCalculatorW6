public class StatsTester {
    public static void pln(Object txt){
        System.out.println(txt);
    }

    public static void main(String[] args){
        double[] testValues = {3.0,-600,0.00002,-3.0,33,1.0,2.0,3.0,8.0,9.0,3.0,2.0,59,4.0,5.0,6.0,7.0,800.0};
        StatsCalculator s = new StatsCalculator(testValues);
        s.printSorted();
        s.print();
        s.printFiveNumberSummary();
    }
}
