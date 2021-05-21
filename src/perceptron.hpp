#pragma once

#include "layer.hpp"

#include <vector>

class Perceptron {
	public:
		struct settings {
			float alpha = 0.3f;
			std::vector<float> layers = {2, 1};

			Perceptron generate() const;
		};

		void print_info() const;

	private:
		std::vector<Layer> _layers;
		float _alpha;

		Perceptron(const settings &);
};

