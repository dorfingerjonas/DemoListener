public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter.getInstance().initialize(500);

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Counter.getInstance().increment(10);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Counter.getInstance().increment(10);
            }
        });

        Counter.getInstance().addListener(event -> {
            System.out.println("Counter was incremented from " + event.getOldValue() + " to " + event.getNewValue());
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Main finished");
    }
}
