package com.example.predictiveML.predictiveMl.predictiveModelling;

import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.feature.OneHotEncoder;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;

import static org.apache.spark.sql.functions.col;


public class LogisticRegressionExample {

    private PipelineModel prepareTrainingData(SparkSession spark, String trainingDataPath){

        //Load the training data
        //Removing the null rows. Ideally the values should be replaced with the mode of the column. Data treatment
        Dataset<Row> trainDtAll= spark.read().option("header", "true").option("inferSchema","true").csv(trainingDataPath).na().drop();

        //Modify the training data marking labels and dropping missing value.
        Dataset<Row> trainDt= trainDtAll.select(col("Survived").as("label"), col("Pclass"), col("Name"), col("Sex"), col("Age"), col("SibSp"), col("Parch"), col("Fare"), col("Embarked"));

        //use indexer to index a string col into binary using one-HotEncoder
        //These two columns are only choosen because these features are String values. the regression function only understands binary or integer values.
        StringIndexer genderIndexer= new StringIndexer().setInputCol("Sex").setOutputCol("SexIndex");
        StringIndexer embarkIndexer= new StringIndexer().setInputCol("Embarked").setOutputCol("EmbarkIndex");

        OneHotEncoder genderEncoder= new OneHotEncoder().setInputCol("SexIndex").setOutputCol("SexVec");
        OneHotEncoder embarkEncoder= new OneHotEncoder().setInputCol("EmbarkIndex").setOutputCol("EmbarkVec");

        //(label, features) format
        VectorAssembler assembler= new VectorAssembler().setInputCols(new String[]{"Pclass", "SexVec", "Age", "SibSp", "Parch", "Fare", "EmbarkVec"}).setOutputCol("features");

        //Randomly split the dataset for model accuracy check
        Dataset<Row>[] arrDt= trainDt.randomSplit(new double[]{0.9,0.1}, 12345L);


        //Prepare a pipeline with all the stages prepared above and fit it with the training data.
        Pipeline pipeline= new Pipeline().setStages(new PipelineStage[]{genderIndexer, embarkIndexer, genderEncoder, embarkEncoder, assembler, new LogisticRegression()});
        PipelineModel model= pipeline.fit(arrDt[0]);
       // model.save("D:\\study\\rakshitlabs\\hackerEarth\\predictiveMl\\src\\main\\java\\com\\example\\predictiveML\\predictiveMl\\model");

        //Get evaluation metrics
        MulticlassMetrics metrics= new MulticlassMetrics(model.transform(arrDt[1]).select(col("label").cast(DataTypes.DoubleType), col("Prediction").cast(DataTypes.DoubleType)));
        double accuracy= metrics.accuracy();
        System.out.println("Accuracy ====>" +accuracy);
        return model;
    }

    private Dataset<Row> loadTestData(SparkSession spark, String testDataPath){
        return spark.read().option("header", "true").option("inferSchema","true").csv(testDataPath).na().drop();
    }


    public Dataset<Row> computeRawScoresOnTestSet(SparkSession spark, String testDataPath, String trainingDataPath){
        PipelineModel model= prepareTrainingData(spark, trainingDataPath);
        return model.transform(loadTestData(spark, testDataPath));

    }
}
