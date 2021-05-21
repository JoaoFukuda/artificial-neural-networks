cmake_minimum_required(VERSION 3.5)

project(ArtificialInteligence)

set(CMAKE_CXX_STANDARD 20)

set(CORE_SOURCES
	src/main.cpp
	src/perceptron.cpp
	src/layer.cpp
	src/neuron.cpp)

add_executable(perceptron ${CORE_SOURCES})

