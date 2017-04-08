package sample;

public class WorkerThread extends Thread{
    private int row;
    private int col;
    private int [][] A;
    private int [][] B;
    private int [][] C;

    public WorkerThread(int row, int col, int[][] A,
                        int[][] B, int[][] C) {
        this.row = row;
        this.col = col;
        this.A = A;
        this.B = B;
        this.C = C;
    }

    public void run() {
        for (int k = 0; k < A[0].length; k++) {
            C[row][col] += A[row][k] * B[k][col];
        }
    }
}