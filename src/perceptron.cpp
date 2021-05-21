#include "perceptron.hpp"

#include <iostream>

Perceptron::Perceptron(const Perceptron::settings & s) :
	_alpha(s.alpha),
	_layers(s.layers.size(), {0})
{
	if (s.layers.size() < 2) throw("There needs to be \e[1mat least\e[m 2 layers in your perceptron\n");

	_layers.back() = Layer(s.layers.back());
	for (int layer = s.layers.size() - 2; layer >= 0; --layer) {
		_layers[layer] = Layer(s.layers[layer], _layers[layer + 1]);
	}
}

Perceptron Perceptron::settings::generate() const
{
	return Perceptron(*this);
}

void Perceptron::print_info() const
{
	std::cout << "Perceptron (" << _layers.size() << " layers total)\n";
	int layer_num = 0;
	for (const auto & layer : _layers) {
		std::cout << "\tLayer " << layer_num
			<< ": " << layer.get_neurons().size() << " neurons"
			<< (&layer != &_layers.back() ? " + 1 bias" : "");
		int neuron_connections = layer.get_bias().get_conns().size();
		for (const auto & neuron : layer.get_neurons())
			neuron_connections += neuron.get_conns().size();
		std::cout << ", " << neuron_connections << " conns\n";
		++layer_num;
	}
}

