#include "layer.hpp"

Layer::Layer(int n_of_neurons) :
	_neurons(n_of_neurons)
{
}

Layer::Layer(int n_of_neurons, const Layer & next_layer) :
	_bias(next_layer._neurons)
{
	for (int i = 0; i < n_of_neurons; ++i) {
		_neurons.emplace_back(next_layer._neurons);
	}
}

const std::vector<Neuron> & Layer::get_neurons() const
{
	return _neurons;
}

const Neuron & Layer::get_bias() const
{
	return _bias;
}

