#include "perceptron.hpp"

#include <vector>
#include <iostream>

std::vector<std::pair<std::pair<float, float>, float>> data_set = {
	{{1.0f, 1.0f}, 1.0f},
	{{1.0f, -1.0f}, -1.0f},
	{{-1.0f, 1.0f}, -1.0f},
	{{-1.0f, -1.0f}, -1.0f}
};

int main()
{
	try {
		Perceptron::settings s;
		s.alpha = 0.3f;
		s.layers = {2, 1};

		Perceptron p = s.generate();

		p.print_info();
	}
	catch (const char* e) {
		std::cerr << e;
	}
}

