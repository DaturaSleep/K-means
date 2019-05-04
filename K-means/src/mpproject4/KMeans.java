package mpproject4;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class KMeans {

	private double[][] data;

	private double[][] centroids;
	private int nRows, nColumns;
	private int numClusters;
	private ArrayList<String> values;
	private int[] classes;

	public KMeans(String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));

		values = new ArrayList<String>();// АррейЛист Данных записаных пока что стрингом

		nColumns = reader.readLine().split(",").length - 2; // Отнимаем номер и название класса и получаем количество
															// колонок
		nRows = 0;

		String wrt;
		while ((wrt = reader.readLine()) != null) {
			nRows++; // Проходимся циклом, чтобы узнать кол-во рядов
			int firsIndex = wrt.indexOf(",");
			int lastIndex = wrt.lastIndexOf(",");

			values.add(wrt.substring(firsIndex + 1, lastIndex)); // Собсна тут мы добавляем пока что стринговые данные

		}

		data = new double[nRows][nColumns]; // Инициализируем матрицу с числами

		double min = Double.parseDouble(values.get(0).split(",")[0]); // Нужны для рандома
		double max = Double.parseDouble(values.get(0).split(",")[0]);

		for (int i = 0; i < values.size(); i++) {
			String[] val = values.get(i).split(",");
			for (int j = 0; j < val.length; j++) {
				data[i][j] = Double.parseDouble(val[j]); // Переписываем со стрингов в Дабл матрицу

				if (data[i][j] > max) {
					max = data[i][j];
				}

				if (data[i][j] < min) {
					min = data[i][j];
				}
				System.out.print(data[i][j] + " ");
			}
			System.out.println(" ");
		}

		System.out.println("Rows: " + nRows + " Columns: " + nColumns);
		System.out.println("Max val: " + max + " Min val: " + min);
		System.out.println("Podaj liczbe centroidow:");
		Scanner sc = new Scanner(System.in);
		int liczbaCentroidow = sc.nextInt();

		centroids = new double[liczbaCentroidow][nColumns];

		// Записываем рандомные значения в центроиды
		System.out.println("Generated centroids");
		for (int i = 0; i < centroids.length; i++) {
			for (int j = 0; j < centroids[i].length; j++) {
				centroids[i][j] = ThreadLocalRandom.current().nextDouble(min, max);
				System.out.print(centroids[i][j] + " ");
			}
			System.out.print(" with index: " + i + "\n");
		}
		System.out.println();

		System.out.println("Podaj liczbe iteracji: ");
		int liczbaIteracji = sc.nextInt();

		for (int i = 0; i < liczbaIteracji; i++) {

			assighnCentroids(data, centroids);
			System.out.println();
		}

		double[] percents = new double[centroids.length];

		for (int i = 0; i < classes.length; i++) {
			percents[classes[i]]++;
		}

		for (int i = 0; i < percents.length; i++) {
			percents[i] = percents[i] / classes.length * 100;
			System.out.println("Centroid " + i + " have " + Math.round(percents[i]) + "% of all objects");
		}

	}

	public void assighnCentroids(double[][] data, double[][] centroids) {
		classes = new int[data.length];
		double[] distance = new double[centroids.length];

		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < distance.length; j++) {
				distance[j] = findDistance(data[i], centroids[j]);
			}
			int indexOdCentroid = findClassByMin(distance);
			classes[i] = indexOdCentroid;
			System.out.println("Data " + values.get(i) + " now has centroid with index " + indexOdCentroid
					+ " and it's distance " + distance[indexOdCentroid]);
		}
		int divider = 0;
		double sum = 0;

		for (int i = 0; i < centroids.length; i++) {
			for (int j = 0; j < centroids[i].length; j++) {

				for (int f = 0; f < data.length; f++) {

					if (classes[f] == i) {
						sum = sum + data[f][j];
						divider++;
					}
				}

				if (divider != 0)
					centroids[i][j] = sum / divider;

				sum = 0;
				divider = 0;

			}

		}
	}

	public int findClassByMin(double[] distance) {
		// Тоже норм работает
		double min = distance[0];
		int minIndex = 0;
		for (int i = 1; i < distance.length; i++) {
			if (distance[i] < min) {
				min = distance[i];
				minIndex = i;
			}
		}
		return minIndex;
	}

	public double findDistance(double[] data, double[] centroid) {
		// Этот метод работает правильно, я проверил
		double distance = 0.0;

		for (int i = 0; i < data.length; i++) {
			distance = distance + Math.pow(data[i] - centroid[i], 2);
		}

		return Math.sqrt(distance);
	}

}
