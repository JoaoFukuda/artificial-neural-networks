import java.util.*;

public class Perceptron
{
    private Float inputSignal;
    private Float outputSignal;

    private Float biasWeight;
    private HashMap<Perceptron, Float> weights;
    private HashMap<Perceptron, Float> newWeights;

    private Layer layer;
    private Float error;

    //Pesos random
    public Perceptron(List<Perceptron> inputPerceptrons, Layer layer) {
        this.layer = layer;
        this.weights = new HashMap<>();
        this.newWeights = new HashMap<>();

        Random r = new Random();
        for (Perceptron perceptron : inputPerceptrons) {
            Float random = r.nextFloat() - 0.5F;
            this.weights.put(perceptron, random);
        }

        this.biasWeight = r.nextFloat();
    }

    //Pesos fixos
    public Perceptron(List<Perceptron> inputPerceptrons, Layer layer, List<Float> weights) {
        this.layer = layer;
        this.weights = new HashMap<>();
        this.newWeights = new HashMap<>();

        for (int i = 0; i < inputPerceptrons.size(); i++) {
            this.weights.put(inputPerceptrons.get(i), weights.get(i));
        }
        this.biasWeight = weights.get(weights.size() - 1);
    }

    public void calculateOutput() {
        this.inputSignal = biasWeight;

        for (Perceptron perceptron : weights.keySet()) {
            Float weight = weights.get(perceptron);
            this.inputSignal += perceptron.getOutputSignal() * weight;
        }

        this.outputSignal = this.layer.getFunction().activate(this.inputSignal);
    }

    public void calculateNewWeightsFromLabel(Float alpha, Float label) {
        Float error = (label - outputSignal)  * this.layer.getFunction().derived(inputSignal);
        calculateNewWeights(error, alpha);
    }

    public void calculateNewWeights(Float error, Float alpha) {
        this.error = error;
        for (Perceptron perceptron : weights.keySet()) {
            Float weight = weights.get(perceptron) + (alpha * this.error * perceptron.getOutputSignal());
            this.newWeights.put(perceptron, weight);
        }

        this.biasWeight += alpha * this.error;
    }

    public void updateWeights() {
        for (Perceptron perceptron :  weights.keySet()) {
            this.weights.put(perceptron, newWeights.get(perceptron));
        }
    }

    public Float getInputSignal() {
        return this.inputSignal;
    }

    public Float getOutputSignal() {
        return this.outputSignal;
    }

    public void setOutputSignal(Float signal) {
        this.outputSignal = signal;
    }

    public HashMap<Perceptron, Float> getWeights() {
        return this.weights;
    }

    public Float getError() {
        return this.error;
    }
}
