import java.util.Random;
import java.util.Scanner;

public class MergesortCutoff5 {

    private static int[] mergeaux;
    private static int m = 5;
    private static Random numberGenerator;

    //the test increase the cutoff by 5 until it reaches over 30 and test each case with an array of size 1million with
    //random enteries we do this five times and then calculate the average time and print it out for each value of m.
    public static void main(String[] args) {
        Scanner myScan = new Scanner(System.in);
        int[] a;
        int n = 1000000;
        a = new int[n];
        double[] outputs = new double[7];
        for(int i = 0; i<5; i++){
            for(int k = 0; k<=30 ; k+=5){
                m = k;
                numberGenerator = new Random();
                for (int j = 0; j < n; j++) {
                    int t = numberGenerator.nextInt();
                    a[j] = t;
                }
                long start = System.nanoTime();
                mergesort(a);
                long end = System.nanoTime();
                outputs[k/5] += (double)(end - start)/1000000;

            }
        }
        for(int i = 0; i<7; i++ ){
            System.out.println("Cutoff: " + (i*5) + " Time: " + outputs[i] / 5 + "ms");
        }



    }

    public static void mergesort(int[] a){
        mergeaux = new int[a.length];
        mergesort(a,0,a.length-1);
    }

    //basic implementation of top-down mergesort with the expection that the exit condition is instead if the sub array size
    //is equal to or smaller then the cutoff we then sort the subarrays with insertion sort before merging them.
    public static void mergesort(int[] a, int lo, int hi){
        if(hi <= lo+ m){insertionsort(a,lo,hi);return;}
        int mid = lo + (hi-lo)/2;
        mergesort(a,lo,mid);
        mergesort(a,mid+1,hi);
        merge(a,lo,mid,hi);
    }

    public static void insertionsort(int[] a, int lo,int hi){
        for(int i = lo + 1; i<=hi; i++ ){
            for(int j = i; j >lo && less(a[j], a[j-1]); j--){
                exech(a,j,j-1);

            }
        }
    }

    private static void exech(int[] a, int i, int j){int t = a[i]; a[i] = a[j];a[j] = t;}
    private static boolean less(int v, int w){return v < w;}
    private static void show(int[] a){
        for(int i = 0 ; i<a.length; i++){
            System.out.print(a[i] + ",");
        }
        System.out.println();
    }
    private static void merge(int[] a, int lo, int mid, int hi){
        int i = lo,j=mid+1;
        for(int k = lo; k<=hi;k++){
            mergeaux[k] = a[k];
        }
        for(int k = lo; k<= hi;k++){
            if(i > mid) {a[k] = mergeaux[j++];}
            else if(j > hi) {a[k] = mergeaux[i++];}
            else if(less(mergeaux[j],mergeaux[i] )) {a[k] = mergeaux[j++];}
            else {a[k] = mergeaux[i++];}
        }
    }
}
