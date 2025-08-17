public class Main {
    public static final Object objectOne = new Object();
    public static final Object objectTwo = new Object();

    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            synchronized (objectOne) {
                System.out.println("Thread 1 took the first object");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
                System.out.println("Thread 1 tries to pick up the second object");
                synchronized (objectTwo) {
                    System.out.println("Thread 1 took both objects");
                }
            }
        }
        );
        Thread thread2 = new Thread(() -> {
            synchronized (objectTwo) {
                System.out.println("Thread 2 took the second object");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
                System.out.println("Thread 2 tries to pick up the first object");
                synchronized (objectOne) {
                    System.out.println("Thread 2 took both objects");
                }
            }
        });

        thread1.start();
        thread2.start();

        //usage LiveLock
        final LiveLock lock1 = new LiveLock("Thread 1", true);
        final LiveLock lock2 = new LiveLock("Thread 2", true);

        new Thread(() -> lock1.tryToGo(lock2)).start();
        new Thread(() -> lock2.tryToGo(lock1)).start();
    }
}
