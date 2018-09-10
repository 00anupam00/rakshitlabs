package com.feature.example.featureSelection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FeatureSelectionApplication {


	public static void main(String[] args) {
		SpringApplication.run(FeatureSelectionApplication.class, args);
		ChiSquareTest chiSquareTest= new ChiSquareTest();
		chiSquareTest.loadDataset("dataset/train.csv");

	}
}
