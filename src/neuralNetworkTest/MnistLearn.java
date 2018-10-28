package neuralNetworkTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import mnistDecoder.Decoder;
import mnistDecoder.Decoder.Digit;
import neuralNetwork.InputNeuron;
import neuralNetwork.NeuralNetwork;
import neuralNetwork.WorkingNeuron;

public class MnistLearn {
	public static List<Digit> learn;
	public static List<Digit> test;
	public static NeuralNetwork nn = new NeuralNetwork();

	public static InputNeuron[][] inputs = new InputNeuron[28][28];
	public static WorkingNeuron[] outputs = new WorkingNeuron[10];

	public static void main(String[] args) {
		try {
			learn = Decoder.loadDataSet(
					"E:\\Eigene Entwicklungen\\NeuronaleNetzSerie\\src\\mnistDecoder\\MNIST\\train-images.idx3-ubyte",
					"E:\\\\Eigene Entwicklungen\\\\NeuronaleNetzSerie\\\\src\\\\mnistDecoder\\\\MNIST\\train-labels.idx1-ubyte");

			test = Decoder.loadDataSet(
					"E:\\Eigene Entwicklungen\\NeuronaleNetzSerie\\src\\mnistDecoder\\MNIST\\t10k-images.idx3-ubyte",
					"E:\\\\Eigene Entwicklungen\\\\NeuronaleNetzSerie\\\\src\\\\mnistDecoder\\\\MNIST\\t10k-labels.idx1-ubyte");
		} catch (Exception ex) {

		}

		for (int i = 0; i < inputs.length; i++) {
			for (int j = 0; j < inputs[0].length; j++) {
				inputs[i][j] = nn.createNewInput();
			}
		}

		for (int i = 0; i < outputs.length; i++) {
			outputs[i] = nn.createNewOutput();
		}

		Random r = new Random();
		double[] weights = new double[inputs.length * inputs[0].length * outputs.length];
		for (int i = 0; i < weights.length; i++) {
			weights[i] = r.nextDouble();
		}

		nn.createFullMesh(weights);

		double epsilon = 0.01;
		while (true) {
			test();
			for (Digit digit : learn) {
				for (int j = 0; j < inputs.length; j++) {
					for (int k = 0; k < inputs[0].length; k++) {
						inputs[j][k].setValue(Decoder.toUnsignedByte(digit.data[j][k]) / 255d);
					}
				}
				double[] shoulds = new double[10];
				shoulds[digit.label] = 1;
				nn.deltaLearning(shoulds, epsilon);
			}

			epsilon *= 0.9;
		}
	}

	public static void test() {
		int correct = 0;
		int incorrect = 0;

		for (Digit digit : test) {
			for (int j = 0; j < inputs.length; j++) {
				for (int k = 0; k < inputs[0].length; k++) {
					inputs[j][k].setValue(Decoder.toUnsignedByte(digit.data[j][k]) / 255d);
				}
			}
			
			ProbabilityDigit[] pd = new ProbabilityDigit[10];
			for (int i = 0; i < pd.length; i++) {
				pd[i] = new ProbabilityDigit(i, outputs[i].getValue());
			}
			
			Arrays.sort(pd, Collections.reverseOrder());
			
			boolean wasCorrect = false;
			for(int i = 0; i < 1; i++) {
				if (digit.label == pd[i].DIGIT) {
					wasCorrect = true;
					break;
				}
			}
			
			if (wasCorrect) {
				correct++;
			} else {
				incorrect++;
			}
		}
		
		double percentage = (double) correct / (double)(correct + incorrect);
		System.out.println(percentage);
	}

	public static class ProbabilityDigit implements Comparable<ProbabilityDigit> {
		public final int DIGIT;
		public double probability;

		public ProbabilityDigit(int digit, double probability) {
			this.DIGIT = digit;
			this.probability = probability;
		}

		@Override
		public int compareTo(ProbabilityDigit other) {
			if (probability == other.probability) {
				return 0;
			} else if (probability > other.probability) {
				return 1;
			}
			return -1;
		}
	}
}
