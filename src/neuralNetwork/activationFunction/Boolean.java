package neuralNetwork.activationFunction;

public class Boolean implements ActivationFunction {
	@Override
	public double activation(double input) {
		return input < 0 ? 0 : 1;
	}
}