package mpproject4;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		KMeans k = new KMeans("src/res/iris_all.csv");
		double[][] distance = { { 5, 3, 8 }, { 1, 4, 5 } };
		// System.out.println(k.findClassByMin(distance));

	}
}
