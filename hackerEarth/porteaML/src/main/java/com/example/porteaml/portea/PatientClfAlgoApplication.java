package com.example.porteaml.portea;

import com.example.porteaml.portea.cluster.ClusterManager;
import com.example.porteaml.portea.regression.MultinomialLogisticRegr;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PatientClfAlgoApplication {

	public static void main(String[] args) {

		SpringApplication.run(PatientClfAlgoApplication.class, args);
		SparkSession spark=  ClusterManager.getSparkSession();

		//load the training datasets dropping the null rows
		Dataset<Row> dtRevnTrain= spark.read().option("header", "true").option("inferSchema", "true").csv("D:\\study\\rakshitlabs\\hackerEarth\\porteaML\\src\\main\\resources\\share\\patient_monthwise_revenue_train.csv").na().drop();
		Dataset<Row> dtPhyDiagTrain= spark.read().option("header", "true").option("inferSchema", "true").csv("D:\\study\\rakshitlabs\\hackerEarth\\porteaML\\src\\main\\resources\\share\\physio_diagnosis_train.csv").na().drop();

		//load the test datasets dropping the null rows
		Dataset<Row> dtRevnTest= spark.read().option("header", "true").option("inferSchema", "true").csv("D:\\study\\rakshitlabs\\hackerEarth\\porteaML\\src\\main\\resources\\share\\patient_monthwise_revenue_test.csv").na().drop();
		Dataset<Row> dtPhyDiagTest= spark.read().option("header", "true").option("inferSchema", "true").csv("D:\\study\\rakshitlabs\\hackerEarth\\porteaML\\src\\main\\resources\\share\\physio_diagnosis_test.csv").na().drop();

		MultinomialLogisticRegr regr= new MultinomialLogisticRegr();
		regr.prepareModel(dtRevnTrain);
	}
}
