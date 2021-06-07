import java.util.*;

public class Model
{
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Fez bosta"); //TODO: trocar (acho que a Sara n vai gostar)
            return;
        }
        Model m = new Model(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        m.trainModel();
        m.testModel();
    }

    private final Float alpha = 0.02F;

    private DataSet dataset;

    private Layer inputLayer;
    private Layer hiddenLayer;
    private Layer outputLayer;

    public Model(int nOfInputPerceptrons, int nOfHiddenPerceptrons, int nOfOutputPerceptrons) {
        dataset = new DataSet("arquivoimaginarioquenaoexiste");

        initializeLayersWithRandomWeights(nOfInputPerceptrons, nOfHiddenPerceptrons, nOfOutputPerceptrons);
    }

    public void trainModel() {
        for (int epoca = 0; epoca < 3000; epoca++) {
            float quad_error = 0.0F;
            for (Vector data : dataset.getTrainSet()){
                float error_sum = 0.0F;
                feedFoward(data);
                backPropagation(data);
                updateWeights();
                for (Perceptron p : outputLayer.getPerceptrons()) {
                   error_sum += p.getError() * p.getError();
                }
                quad_error += error_sum / 2.0F;
            }
            quad_error /= dataset.getTrainSet().size();
            System.out.println(quad_error);
        }
    }

    public void testModel() {
        for (Vector test : dataset.getTestSet()) {
            feedFoward(test);
        }
    }

    public void feedFoward(Vector data) {
        //setando os valores de entrada dos neuronios na camada de entrada
        this.inputLayer.setData(data.input);
        this.hiddenLayer.calculateData();
        this.outputLayer.calculateData();
    }

    public void backPropagation(Vector data) {
        this.outputLayer.calculateErrorsFromLabel(alpha, data.label);
        this.hiddenLayer.calculateErrors(alpha, outputLayer);
    }

    public void updateWeights() {
        outputLayer.updateWeights();
        hiddenLayer.updateWeights();
    }

    private void initializeLayersWithRandomWeights(int nOfInputPerceptrons, int nOfHiddenPerceptrons, int nOfOutputPerceptrons) {
        this.inputLayer = new Layer(nOfInputPerceptrons);
        this.hiddenLayer = new Layer(nOfHiddenPerceptrons, this.inputLayer, new SigmoidFunction());
        this.outputLayer = new Layer(nOfOutputPerceptrons, this.hiddenLayer, new SigmoidFunction());
    }

    private void initializeLayersWithFixedWeights(int nOfInputPerceptrons, int nOfHiddenPerceptrons, int nOfOutputPerceptrons) {
        ArrayList<ArrayList<Float>> hiddenLayerWeights = new ArrayList<>();
        ArrayList<Float> firstPerceptronOfHiddenLayer = new ArrayList<>();
        firstPerceptronOfHiddenLayer.add(0.53F);
        firstPerceptronOfHiddenLayer.add(0.97F);
        firstPerceptronOfHiddenLayer.add(0.43F);
        ArrayList<Float> secondPerceptronOfHiddenLayer = new ArrayList<>();
        secondPerceptronOfHiddenLayer.add(0.28F);
        secondPerceptronOfHiddenLayer.add(0.16F);
        secondPerceptronOfHiddenLayer.add(0.62F);
        ArrayList<Float> thirdPerceptronOfHiddenLayer = new ArrayList<>();
        firstPerceptronOfHiddenLayer.add(0.53F);
        firstPerceptronOfHiddenLayer.add(0.97F);
        firstPerceptronOfHiddenLayer.add(0.43F);
        ArrayList<Float> fourthPerceptronOfHiddenLayer = new ArrayList<>();
        secondPerceptronOfHiddenLayer.add(0.28F);
        secondPerceptronOfHiddenLayer.add(0.16F);
        secondPerceptronOfHiddenLayer.add(0.62F);
        hiddenLayerWeights.add(firstPerceptronOfHiddenLayer);
        hiddenLayerWeights.add(secondPerceptronOfHiddenLayer);
        hiddenLayerWeights.add(thirdPerceptronOfHiddenLayer);
        hiddenLayerWeights.add(fourthPerceptronOfHiddenLayer);

        ArrayList<ArrayList<Float>> outputLayerWeights = new ArrayList<>();
        ArrayList<Float> firstPerceptronOfOutputLayer = new ArrayList<>();
        firstPerceptronOfOutputLayer.add(0.17F);
        firstPerceptronOfOutputLayer.add(0.24F);
        firstPerceptronOfOutputLayer.add(0.43F);
        firstPerceptronOfOutputLayer.add(0.24F);
        firstPerceptronOfOutputLayer.add(0.43F);
        outputLayerWeights.add(firstPerceptronOfOutputLayer);

        this.inputLayer = new Layer(nOfInputPerceptrons);
        this.hiddenLayer = new Layer(nOfHiddenPerceptrons, this.inputLayer, new SigmoidFunction(), hiddenLayerWeights);
        this.outputLayer = new Layer(nOfOutputPerceptrons, this.hiddenLayer, new SigmoidFunction(), outputLayerWeights);
    }
}
