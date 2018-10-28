package neuralNetwork.activationFunction;

public class Identity implements ActivationFunction {
	@Override
	public double activation(double input) {
		return input;
	}
}