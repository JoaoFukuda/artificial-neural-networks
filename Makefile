SRC=ActivatorFunction.java DataSet.java Layer.java Model.java Perceptron.java ReLuFunction.java SigmoidFunction.java
OUTPUTS=ActivatorFunction.class DataSet.class Layer.class Model.class Perceptron.class ReLuFunction.class SigmoidFunction.class

.PHONY: all run clean

all: $(OUTPUTS)

%.class: %.java
	javac $<

run: $(OUTPUTS)
	java Model 2 4 1

plot: $(OUTPUTS)
	java Model 2 4 1 | tail -n +1 > out.txt
	gnuplot --persist -e 'plot "out.txt" with lines'
	rm out.txt

clean:
	rm -f *.class
