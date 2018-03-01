package model;

public class Matrix {

    /**
     * Multiplies matrices a and b. a needs to have the same number of cols as b has rows
     * Resulting matrix will have dimension [a.rows b.cols]
     *
     * @param a matrix a
     * @param b matrix b
     * @return matrix ab
     */
    public static double[][] multiplyMatrices(double[][] a, double[][] b) {
        if (a[0].length == b.length) {
            double[][] product = new double[a.length][b[0].length];
            double[][] bT = transpose(b);
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < b[0].length; j++) {
                    product[i][j] = dotProduct(a[i], bT[j]);
                }
            }
            return product;
        }
        return null; //Matrix multiplication not defined
    }

    /**
     * Normalizes the matrix such that the sum of all matrix elements equals 1
     *
     * @param matrix
     * @return the normalized matrix
     */
    public static double[][] normalize(double[][] matrix) {
        double sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                sum += matrix[i][j];
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = matrix[i][j] / sum;
            }
        }
        return matrix;
    }


    /**
     * Calculates dot product of two vectors
     *
     * @param a  first vector
     * @param bT the transpose of the second vector
     * @return dot product of a and b
     */
    public static double dotProduct(double[] a, double[] bT) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i] * bT[i];
        }
        return sum;
    }

    /**
     * Transposes a matrix
     *
     * @param t
     * @return
     */
    public static double[][] transpose(double[][] t) {
        double[][] tT = new double[t[0].length][t.length];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[0].length; j++) {
                tT[j][i] = t[i][j];
            }
        }
        return tT;
    }

    /**
     * Creates a diagonal matrix out of the diagonal vector
     *
     * @param vector
     * @return
     */
    public static double[][] createDiagonalMatrix(double[] vector) {
        double[][] matrix = new double[vector.length][vector.length];
        for (int i = 0; i < vector.length; i++) {
            matrix[i][i] = vector[i];
        }
        return matrix;
    }
}
