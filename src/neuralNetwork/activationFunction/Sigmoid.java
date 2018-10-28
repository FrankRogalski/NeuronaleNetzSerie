package neuralNetwork.activationFunction;

public class Sigmoid implements ActivationFunction {

	@Override
	public double activation(double input) {
		return 1d / (1d + Math.pow(Math.E, -input));
	}
}