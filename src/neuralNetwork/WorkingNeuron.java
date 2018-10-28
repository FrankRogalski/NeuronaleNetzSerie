package neuralNetwork;

import java.util.ArrayList;

import neuralNetwork.activationFunction.ActivationFunction;

public class WorkingNeuron extends Neuron{
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	private ActivationFunction activationFunction = ActivationFunction.activationHyperbolicTangent;
	
	@Override
	public double getValue() {
		double sum = 0;
		for (Connection c: connections) {
			sum += c.getValue();
		}
		return activationFunction.activation(sum);
	}
	
	public void addConnection(Connection c) {
		connections.add(c);
	}
	
	public void deltaLearning(double epsilon, double smallDelta) {
		for (Connection connection : connections) {
			double bigDelta = epsilon * smallDelta * connection.getValue();
			connection.addWeight(bigDelta);
		}
	}
}