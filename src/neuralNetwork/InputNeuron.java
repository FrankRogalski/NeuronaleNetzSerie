package neuralNetwork;

public class InputNeuron extends Neuron{
	private double value = 0;
	
	@Override
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}