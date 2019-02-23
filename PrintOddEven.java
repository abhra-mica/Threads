package THREAD;

public class PrintOddEven {
	public static void main(String[] args) {
		Counter count = new Counter();
		Thread odd = new Thread(new OddThread(count));
		Thread even = new Thread(new EvenThread(count));
		odd.start();
		even.start();
	}
}

class OddThread implements Runnable {
	Counter count = null;

	OddThread(Counter count) {
		this.count = count;
	}

	public void run() {
		synchronized (count) {
			for (int i = 0; i < 10; i++) {
				if (count.count % 2 != 0) {
					System.out.println("Odd Number:: " + count.count);
					count.count++;
					count.notify();
				} else {
					try {
						count.wait();
					} catch (InterruptedException e) {
					}
				}
			}
		}
	}
}

class EvenThread implements Runnable {
	Counter count = null;

	EvenThread(Counter count) {
		this.count = count;
	}

	public void run() {
		synchronized (count) {
			for (int i = 0; i < 10; i++) {
				if (count.count % 2 == 0) {
					System.out.println("Even Number:: " + count.count);
					count.count++;
					count.notify();
				} else {
					try {
						count.wait();
					} catch (InterruptedException e) {
					}
				}
			}

		}
	}
}

class Counter {
	int count;
}