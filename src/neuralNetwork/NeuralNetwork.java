package neuralNetwork;

import java.util.ArrayList;

public class NeuralNetwork {
	private ArrayList<InputNeuron> inputNeurons = new ArrayList<InputNeuron>();
	private ArrayList<WorkingNeuron> outputNeurons = new ArrayList<WorkingNeuron>();
	private ArrayList<WorkingNeuron> hiddenNeurons = new ArrayList<WorkingNeuron>();

	public InputNeuron createNewInput() {
		InputNeuron in = new InputNeuron();
		inputNeurons.add(in);
		return in;
	}

	public WorkingNeuron createNewOutput() {
		WorkingNeuron wn = new WorkingNeuron();
		outputNeurons.add(wn);
		return wn;
	}

	public void createHiddenNeurons(int amount) {
		for (int i = 0; i < amount; i++) {
			hiddenNeurons.add(new WorkingNeuron());
		}
	}
	
	public void deltaLearning(double[] shoulds, double epsilon) {
		if (shoulds.length != outputNeurons.size()) {
			throw new IllegalArgumentException();
		}
		
		if (hiddenNeurons.size() != 0) {
			throw new IllegalStateException();
		}
		
		for (int i = 0; i <shoulds.length; i++) {
			double smallDelta = shoulds[i] - outputNeurons.get(i).getValue();
			outputNeurons.get(i).deltaLearning(epsilon, smallDelta);
		}
	}

	public void createFullMesh() {
		if (hiddenNeurons.size() == 0) {
			for (WorkingNeuron on : outputNeurons) {
				for (InputNeuron in : inputNeurons) {
					on.addConnection(new Connection(in, 1));
				}
			}
		} else {
			for (WorkingNeuron on : outputNeurons) {
				for (WorkingNeuron hn : hiddenNeurons) {
					on.addConnection(new Connection(hn, 1));
				}
			}
			
			for (WorkingNeuron hn : hiddenNeurons) {
				for (InputNeuron in : inputNeurons) {
					hn.addConnection(new Connection(in, 1));
				}
			}
		}
	}

	public void createFullMesh(double... weights) {
		if (hiddenNeurons.size() == 0) {
			if (weights.length != inputNeurons.size() * outputNeurons.size()) {
				throw new RuntimeException();
			}
			int index = 0;
			for (WorkingNeuron on : outputNeurons) {
				for (InputNeuron in : inputNeurons) {
					on.addConnection(new Connection(in, weights[index++]));
				}
			}
		} else {
			if (weights.length != inputNeurons.size() * hiddenNeurons.size()
					+ hiddenNeurons.size() * outputNeurons.size()) {
				throw new RuntimeException();
			}
			
			int index = 0;
			for (WorkingNeuron hn : hiddenNeurons) {
				for (InputNeuron in : inputNeurons) {
					hn.addConnection(new Connection(in, weights[index++]));
				}
			}
			
			for (WorkingNeuron on : outputNeurons) {
				for (WorkingNeuron hn : hiddenNeurons) {
					on.addConnection(new Connection(hn, weights[index++]));
				}
			}
		}
	}
}