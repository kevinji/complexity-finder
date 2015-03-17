import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ComplexityFinder {
	private Object object;
	private Method method;

	public ComplexityFinder(String className, String methodName) {
		try {
			Class<?> cls = Class.forName(className);
			Constructor<?> constructor = cls.getConstructor();
			object = constructor.newInstance();

			method = cls.getMethod(methodName, long.class);
			method.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getComplexity() {
		// Find an N where time taken >= 0.10 sec
		long dN = 1;
		long N = 2;
		double time = timeMethod(N);

		while (time < 0.10) {
			N *= 1.5;
			time = timeMethod(N);
		}

		// Find a next N to compare the original data point to
		// Times must have at least 5x larger
		// long dN = 1;
		long newN = N + dN;
		double newTime = timeMethod(newN);

		while (newTime / time < 5) {
			dN *= (long)(1.0 / (newTime - time));
			newN += dN;
			newTime = timeMethod(newN);
		}

		double exp = Math.log(newTime / time) / Math.log((double) newN / N);
		String complexity = String.format("O(N^%.2f)", exp);
		return complexity;
	}

	private double timeMethod(long N) {
		try {
			Timer timer = new Timer();
			method.invoke(object, N);
			return timer.getDuration();
		} catch (Exception e) {
			throw new RuntimeException("Method could not be run successfully.");
		}
	}

	private class Timer {
		private final long start;

		public Timer() {
			start = System.currentTimeMillis();
		}

		public double getDuration() {
			long now = System.currentTimeMillis();
			return (now - start) / 1000.0;
		}
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			printUsage();
			return;
		}

		String className = args[0];
		String methodName = args[1];

		ComplexityFinder cf = new ComplexityFinder(className, methodName);
		String complexity = cf.getComplexity();
		System.out.println(complexity);
	}

	private static void printUsage() {
		System.out.println("Usage: java ComplexityFinder <class> <method>");
		System.out.println("<method> must take in one long argument N.");
	}
}
