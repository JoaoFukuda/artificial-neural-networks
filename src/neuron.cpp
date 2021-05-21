#include "neuron.hpp"

#include <random>
#include <numbers>
#include <cmath>

std::function<float(float)> Neuron::activation_function = [] (float energy) -> float {
	return 1 / (1 + std::pow(std::numbers::e, energy));
};

Neuron::Neuron()
{
}

Neuron::Neuron(const std::vector<Neuron> & neurons)
{
	std::random_device rd;
	std::uniform_real_distribution<float> dist(-1.0f, 1.0f);
	for (const auto & neuron : neurons) {
		connection c;
		c.connection = &neuron;
		c.weight = dist(rd);
		_connections.emplace_back(c);
	}
}

const std::vector<Neuron::connection> & Neuron::get_conns() const
{
	return _connections;
}

void Neuron::add_energy(float energy)
{
	_energy += energy;
}

void Neuron::set_energy(float energy)
{
	_energy = energy;
}

void Neuron::reset_energy()
{
	set_energy(0.0f);
}

