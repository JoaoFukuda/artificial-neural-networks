SOURCES=IO/DataVector.java IO/Dataset.java IO/Output.java Main.java Model/ActivationFunctions/ActivatorFunction.java Model/ActivationFunctions/ReLuFunction.java Model/ActivationFunctions/SigmoidFunction.java Model/Components/Layer.java Model/Components/Perceptron.java Model/Model.java

.PHONY: all run plot clean

all: Main.class

Main.class: $(SOURCES)
	javac $^

run: Main.class
	java Main datasets/dataset_XOR.csv 1

plot: run
	gnuplot --persist -e 'plot "outputs/errors.txt" with lines'

clean:
	find -name *.class -exec rm {} \;
