#pragma once

#include "neuron.hpp"

#include <vector>

class Layer {
	private:
		Neuron _bias;
		std::vector<Neuron> _neurons;

	public:
		Layer(int);
		Layer(int, const Layer &);

		const std::vector<Neuron> & get_neurons() const;
		const Neuron & get_bias() const;
};

