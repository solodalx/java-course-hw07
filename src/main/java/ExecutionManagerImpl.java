import java.util.ArrayList;
import java.util.List;

public class ExecutionManagerImpl implements ExecutionManager {

    public Context execute(Runnable callback, Runnable... tasks) {
        List<Thread> threads = new ArrayList<>();
        Thread callbackThread = new Thread(
                () -> {
                    for (Thread t: threads) {
                        try {
                            t.join();
                        } catch (InterruptedException e) {
                            // Debug
                            System.out.println("TASK " + t.getName() + " TERMINATED");
                            e.printStackTrace();
                        }
                    }
//                    Thread cb = new Thread(callback, "Callback");
//                    cb.start();
                    callback.run();
                },
                "JoinAndCallback");
        Context context = new ContextImpl(callbackThread, threads);

        int countTasks = 0;
        Thread thread;
        for (Runnable task: tasks) {
            thread = new Thread(task, "Task #" + countTasks++);
            threads.add(thread);
            thread.start();
        }
        callbackThread.start();

        return context;
    }
}
