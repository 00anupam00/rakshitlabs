package com.feature.example.featureSelection;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FeatureSelectionApplication {

	private static String path= "D:\\study\\rakshitlabs\\featureSelection\\src\\main\\resources\\dataset\\train.csv";

	public static void main(String[] args) {
		SpringApplication.run(FeatureSelectionApplication.class, args);
		ChiSquareTest chiSquareTest= new ChiSquareTest();

		//Load Dataset
		Dataset<Row> df= chiSquareTest.loadDataset(path);
	}
}
