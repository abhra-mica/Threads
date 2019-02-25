package THREAD;

public class PrintOddEvenWithInterrupt {
	public static volatile int count;

	public static void main(String[] args) throws InterruptedException {
		Thread oddThread = new Thread(new OddInterruptThread(), "Odd Interrupted Thread ");
		Thread evenThread = new Thread(new EvenInterruptThread(),
				"Even Interrupted Thread ");

		oddThread.start();
		evenThread.start();
		for (int i = 0; i < 20; i++) {
			count++;
			evenThread.interrupt();
			oddThread.interrupt();
			Thread.sleep(1000L);
		}
	}

	static class OddInterruptThread implements Runnable {
		public void run() {
			try {
				Thread.sleep(Long.MAX_VALUE);
			} catch (InterruptedException e) {
			}
			int oldNum = 0;
			while (count != 20) {
				if (oldNum != count && count % 2 == 1) {
					System.out.println(Thread.currentThread().getName()
							+ " prints " + count);
					oldNum = count;
				}
			}
		}
	}

	static class EvenInterruptThread implements Runnable {
		public void run() {
			try {
				Thread.sleep(Long.MAX_VALUE);
			} catch (InterruptedException e) {
			}
			int oldNum = 0;
			do {
				if (oldNum != count && count % 2 == 0) {
					System.out.println(Thread.currentThread().getName()
							+ " prints " + count);
					oldNum = count;
				}
			} while (count != 20);
		}
	}
}
