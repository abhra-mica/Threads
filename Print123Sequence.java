package THREAD;

public class Print123Sequence {
	static Object monitor = new Object();
	static boolean one = true;
	static boolean two = false;
	static boolean three = false;

	public static void main(String[] args) {

		Thread t1 = new Thread(new Sequence(1));
		Thread t2 = new Thread(new Sequence(2));
		Thread t3 = new Thread(new Sequence(3));
		t1.start();
		t2.start();
		t3.start();
	}

	static class Sequence implements Runnable {
		int threadId;

		Sequence(int threadId) {
			this.threadId = threadId;
		}

		public void run() {
			display();
		}

		public void display() {
			try {
				while (true) {
					Thread.sleep(1000);
					synchronized (monitor) {
						if (1 == threadId) {
							if (!one) {
								monitor.wait();
							} else {
								System.out.print("1 ");
								one = false;
								two = true;
								three = false;
								monitor.notifyAll();
							}
						}

						if (2 == threadId) {
							if (!two) {
								monitor.wait();
							} else {
								System.out.print("2 ");
								one = false;
								two = false;
								three = true;
								monitor.notifyAll();
							}
						}

						if (3 == threadId) {
							if (!three) {
								monitor.wait();
							} else {
								System.out.print("3 ");
								one = true;
								two = false;
								three = false;
								monitor.notifyAll();
							}
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
