package com.feature.example.featureSelection;

import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class FeatureSelectionApplication {

	private static String path= "E:\\labs\\rakshitlabs\\featureSelection\\src\\main\\resources\\dataset\\wdbc.csv";

	public static void main(String[] args) {
		SpringApplication.run(FeatureSelectionApplication.class, args);
		ChiSquareTest chiSquareTest= new ChiSquareTest();

		//Load Dataset
		RDD<String> lines= chiSquareTest.loadDataset(path);
		//df.printSchema();

        try {
            chiSquareTest.getGoodnessOfTheFeatures(lines, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
