package com.example.predictiveML.predictiveMl;

import com.example.predictiveML.predictiveMl.cluster.ClusterManager;
import com.example.predictiveML.predictiveMl.predictiveModelling.LogisticRegressionExample;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PredictiveMlApplication {

	public static void main(String[] args) {
		SpringApplication.run(PredictiveMlApplication.class, args);
		SparkSession spark= ClusterManager.getSparkSession();
		System.out.println("Cluster Manager is started..");

		LogisticRegressionExample example= new LogisticRegressionExample();
		Dataset<Row> results= example.computeRawScoresOnTestSet(spark, "D:\\study\\rakshitlabs\\hackerEarth\\predictiveMl\\src\\main\\resources\\test.csv", "D:\\study\\rakshitlabs\\hackerEarth\\predictiveMl\\src\\main\\resources\\train.csv" );
		results.select("PassengerId", "Name", "Age", "prediction").show(50);
	}
}
