package pahail.gRaph.main.core;

import org.la4j.LinearAlgebra;
import org.la4j.Matrix;
import org.la4j.inversion.MatrixInverter;

public class TestClassForMatrix {

    public static void main(String[] args) {
        Matrix a = Matrix.diagonal(3,3);
        MatrixInverter inverter = a.withInverter(LinearAlgebra.GAUSS_JORDAN);
        System.out.println(inverter.inverse());
    }
}
