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

    private final Float alpha = 0.3F;

    private DataSet dataset;

    private Layer inputLayer;
    private Layer hiddenLayer;
    private Layer outputLayer;

    public Model(int nOfInputPerceptrons, int nOfHiddenPerceptrons, int nOfOutputPerceptrons) {
        dataset = new DataSet("arquivoimaginarioquenaoexiste");

        initializeLayersWithRandomWeights(nOfInputPerceptrons, nOfHiddenPerceptrons, nOfOutputPerceptrons);
    }

    public void trainModel() {
        for (int epoca = 0; epoca < 100; epoca++) {
            for (Vector data : dataset.getTrainSet()){
                feedFoward(data);
                backPropagation(data);
                updateWeights();
            }
        }
    }

    public void testModel() {
        feedFoward(dataset.getTestSet().get(0));

        System.out.print("\033[1mResultados\033[m: ");
        for (Perceptron outputPerceptron : this.outputLayer.getPerceptrons()) {
            System.out.println("\033[1;92m" + outputPerceptron.getOutputSignal() + "\033[m");
        }
    }


    public void feedFoward(Vector data) {
        List<Perceptron> perceptrons = this.inputLayer.getPerceptrons();
        //setando os valores de entrada dos neuronios na camada de entrada
        for (int i = 0; i < this.inputLayer.getPerceptrons().size(); i++) {
            perceptrons.get(i).setOutputSignal(data.input[i]);
        }

        for (Perceptron hiddenPerceptron : this.hiddenLayer.getPerceptrons()) {
            hiddenPerceptron.calculateOutput();
        }

        for (Perceptron outputPerceptron : this.outputLayer.getPerceptrons()) {
            outputPerceptron.calculateOutput();
        }
    }

    public void backPropagation(Vector data) {
        for (Perceptron outputPerceptron : this.outputLayer.getPerceptrons()) {
            Float outputSignal = outputPerceptron.getOutputSignal();
            Float inputSignal = outputPerceptron.getInputSignal();
            Float error = (data.label[0] - outputSignal)  * outputLayer.derived(inputSignal);
            outputPerceptron.calculateNewWeights(error, alpha);
        }

        for (Perceptron hiddenPerceptron : this.hiddenLayer.getPerceptrons()) {
            Float errorIn = 0.0F;

            for (Perceptron outputPerceptron : this.outputLayer.getPerceptrons()) {
                Float weight = outputPerceptron.getWeights().get(hiddenPerceptron);
                errorIn += weight * outputPerceptron.getError();
            }

            Float error = errorIn * hiddenLayer.derived(hiddenPerceptron.getInputSignal());

            hiddenPerceptron.calculateNewWeights(error, alpha);
        }

    }

    public void updateWeights() {
        for (Perceptron perceptron : this.outputLayer.getPerceptrons()) {
            perceptron.updateWeights();
        }

        for (Perceptron perceptron : this.hiddenLayer.getPerceptrons()) {
            perceptron.updateWeights();
        }
    }


    private void initializeLayersWithRandomWeights(int nOfInputPerceptrons, int nOfHiddenPerceptrons, int nOfOutputPerceptrons) {
        System.out.println("Input (\033[1;93m" + nOfInputPerceptrons + "\033[m):");
        this.inputLayer = new Layer(nOfInputPerceptrons, null, null);
        System.out.println("Hidden (\033[1;93m" + nOfHiddenPerceptrons + "\033[m):");
        this.hiddenLayer = new Layer(nOfHiddenPerceptrons, this.inputLayer, new ReLuFunction());
        System.out.println("Output (\033[1;93m" + nOfOutputPerceptrons + "\033[m):");
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
        hiddenLayerWeights.add(firstPerceptronOfHiddenLayer);
        hiddenLayerWeights.add(secondPerceptronOfHiddenLayer);

        ArrayList<ArrayList<Float>> outputLayerWeights = new ArrayList<>();
        ArrayList<Float> firstPerceptronOfOutputLayer = new ArrayList<>();
        firstPerceptronOfOutputLayer.add(0.17F);
        firstPerceptronOfOutputLayer.add(0.24F);
        firstPerceptronOfOutputLayer.add(0.43F);
        outputLayerWeights.add(firstPerceptronOfOutputLayer);



        this.inputLayer = new Layer(nOfInputPerceptrons, null, new SigmoidFunction());
        this.hiddenLayer = new Layer(nOfHiddenPerceptrons, this.inputLayer, new SigmoidFunction(), hiddenLayerWeights);
        this.outputLayer = new Layer(nOfOutputPerceptrons, this.hiddenLayer, new SigmoidFunction(), outputLayerWeights);
    }
}
