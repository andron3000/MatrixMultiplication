package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Collections;

public class Controller {

    @FXML
    private TextField sz;

    @FXML
    private TextArea txtA;

    @FXML
    private TextArea txtB;

    @FXML
    private TextArea txtC;

    private static int M;
    private static int K;
    private static int N;

    private static int[][] A;
    private static int[][] B;
    private static int[][] C;

    private static WorkerThread[][] threads;

    @FXML
    private void buttonClickAction() throws Exception {

        if (!sz.getText().equals("")) {
            removeRed(sz);
            String[] words = sz.getText().split("\\s+");

            M = Integer.parseInt(words[0]);  // rows A
            K = Integer.parseInt(words[1]);  // columns A === rows B
            N = Integer.parseInt(words[2]);  // columns B

            threads = new WorkerThread[M][N]; // (M * N) -- total count of threads

            A = new int[M][K];
            B = new int[K][N];
            C = new int[M][N];

            A = readMatrix(txtA, A);
            B = readMatrix(txtB, B);

            matrixMultiplication();
        } else {
            setRed(sz);
        }
    }

    private int[][] readMatrix(TextArea textArea, int[][] matrix) throws Exception {
        int k = 0;
        for (String line : textArea.getText().split("\\n")) {
            String[] words = line.split("\\s+");
            if (words.length != matrix[0].length) {
                throw new Exception("invalid row length");
            }
            for (int i = 0; i < matrix[0].length; i++) {
                matrix[k][i] = Integer.parseInt(words[i]);
            }
            k++;
        }

        return matrix;
    }

    private void matrixMultiplication() {

        //creates 9 Worker threads. Each thread Calculates a Matrix Value and sets it to C matrix
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                threads[i][j] = new WorkerThread(i, j, A, B, C);
                threads[i][j].start();
            }
        }

        // DISPLAY RESULT

        String result = "";
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                result += String.format("%8d", C[i][j]);
            }
            result += '\n';
        }

        txtC.setDisable(false);
        txtC.setText(result);
    }




    // STYLE

    private void setRed(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();

        if (!styleClass.contains("tferror")) {
            styleClass.add("tferror");
        }
    }

    private void removeRed(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        styleClass.removeAll(Collections.singleton("tferror"));
    }
}
