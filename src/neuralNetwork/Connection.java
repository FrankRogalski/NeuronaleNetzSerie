package neuralNetwork;

public class Connection {
	private Neuron neuron;
	private double weight;
	
	public Connection(Neuron neuron, double weight) {
		this.neuron = neuron;
		this.weight = weight;
	}
	
	public double getValue() {
		return weight * neuron.getValue();
	}
	
	public void addWeight(double weightDelta) {
		weight += weightDelta;
	}
}