import java.util.ArrayList;

public class TestComplexity {
	public void function1(long n) {
		ArrayList<Integer> a = new ArrayList<Integer>();
		for (long i = 0; i < n; i++) {
			for (long j = 0; j < i; j++) {
				a.add(0);
				a.clear();
			}
		}
	}

	public void function2(long n) {
		ArrayList<Integer> a = new ArrayList<Integer>();
		long j = 0;
		for (long i = 0; i < n; i++) {
			for (; j < i; j++) {
				a.add(0);
				a.clear();
			}
		}
	}


	public void function3(long n) {
		ArrayList<Integer> a = new ArrayList<Integer>();
		for (long i = 0; i < n; i++) {
			for (long j = 0; j < n * n; j++) {
				a.add(0);
				a.clear();
			}
		}
	}

	public boolean function4(long n) {
		for (long i = 2; i <= Math.sqrt(n); ++i) {
			if (n % i == 0) {
				return false;
			}
		}

		return true;
	}
}
