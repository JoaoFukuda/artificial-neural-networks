#pragma once

#include <vector>
#include <functional>

class Neuron {
	private:
		struct connection {
			float weight;
			const Neuron* connection;
		};

		float _energy = 0.0f;
		std::vector<connection> _connections;

	public:
		static std::function<float(float)> activation_function;

		Neuron();
		Neuron(const std::vector<Neuron> & neurons);

		const std::vector<connection> & get_conns() const;

		void add_energy(float);
		void set_energy(float);
		void reset_energy();
};

