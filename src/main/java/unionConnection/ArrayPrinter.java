package unionConnection;

public class ArrayPrinter {

    private ArrayPrinter() {
    }

    public static void print(int[][] arr){
        for(int[] row : arr) {
            printRow(row);
        }
    }
    private static void printRow(int[] row) {
        for (int i : row) {
            System.out.printf("%5d",i);
        }
        System.out.println();
    }
}
