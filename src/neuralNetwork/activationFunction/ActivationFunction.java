package neuralNetwork.activationFunction;

public interface ActivationFunction {
	static Boolean activationBoolean = new Boolean();
	static Identity activationIdentity = new Identity();
	static Sigmoid activationSigmoid = new Sigmoid();
	static HyperbolicTangent activationHyperbolicTangent = new HyperbolicTangent();
	double activation(double input);
}
