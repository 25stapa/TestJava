import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.PriorityQueue;

public class ToyStore {
	private static final double[] WEIGHTS = {0.2, 0.2, 0.6};
	private static final int NUM_GETS = 10;

	private record Toy(int id, String name, int frequency) {


	}

	public static void main(String[] args) {
		PriorityQueue<Toy> toyQueue = new PriorityQueue<>((t1, t2) ->
				Integer.compare(t2.frequency(), t1.frequency()));

		addToy(toyQueue, "1", "робот", 2);
		addToy(toyQueue, "2", "конструктор", 2);
		addToy(toyQueue, "3", "кукла", 6);

		try {
			FileWriter writer = new FileWriter("output.txt");
			for (int i = 0; i < NUM_GETS; i++) {
				String toyId = getRandomToyId(toyQueue);
				writer.write(toyId + "\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void addToy(PriorityQueue<Toy> toyQueue,
	                           String id, String name, int frequency) {
		Toy toy = new Toy(Integer.parseInt(id), name, frequency);
		toyQueue.offer(toy);
	}

	private static String getRandomToyId(PriorityQueue<Toy> toyQueue) {
		SecureRandom random = new SecureRandom();
		double randomValue = random.nextDouble();

		double sum = 0.0;
		for (Toy toy : toyQueue) {
			sum += toy.frequency() / 10.0;
			if (randomValue <= sum) {
				return String.valueOf(toy.id());
			}
		}
// В случае, если случайное число не попало в указанные диапазоны
		assert toyQueue.peek() != null;
		return String.valueOf(toyQueue.peek().id());
	}
}