package THREAD;

public class PrintOddEvenWithoutSynchronize {
	public static volatile int count;

	public static void main(String[] args) throws InterruptedException {
		Thread oddThread = new Thread(new OddThreadUnSync(), "Odd Thread ");
		Thread evenThread = new Thread(new EvenThreadUnSync(), "Even Thread ");

		oddThread.start();
		evenThread.start();
		for (int i = 0; i < 20; i++) {
			count++;
			Thread.sleep(1000L);/*
								 * //Why this is required?If we don't provide
								 * then output will be "Even Thread Print 20".
								 * This because how parallel execution works, no
								 * matter if true parallel on two physical cores
								 * or pseudo parallel on one thread-scheduled
								 * core,the main reason for "Print 20" output
								 * might be that the end of the loop is passed
								 * before the other threads fully started up so
								 * our count is already 20 when print is first
								 * executed. Remember: modern cpus run at
								 * giga-hertz - thats 1.000.000.000 operations
								 * per second - your count up to 20 is done in
								 * 20 cycles - the create and start up a new
								 * thread it take couple 100 cycles so you have
								 * to slow down your main thread
								 */
		}
	}

	static class OddThreadUnSync implements Runnable {
		public void run() {
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

	static class EvenThreadUnSync implements Runnable {
		public void run() {
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
