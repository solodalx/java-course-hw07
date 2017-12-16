import java.util.List;

public class ContextImpl implements Context{

    private Thread callback;
    private List<Thread> threads;

    public ContextImpl(Thread callback, List<Thread> threads) {
        this.callback = callback;
        this.threads = threads;
    }

    //Метод getCompletedTaskCount() возвращает количество тасков, которые на текущий момент успешно выполнились.
    @Override
    public int getCompletedTaskCount() {
        int count = 0;
        for (Thread thread: threads) {
            if (!thread.isAlive())
                count++;
        }
        return count;
    }

    //Метод getFailedTaskCount() возвращает количество тасков, при выполнении которых произошел Exception.
    @Override
    public int getFailedTaskCount() {
        int count = 0;
        for (Thread thread: threads) {
            if (thread.isInterrupted())
                count++;
        }
        return count;
    }

    //Метод interrupt() отменяет выполнения тасков, которые еще не начали выполняться.
    @Override
    public void interrupt() {
        for (Thread thread: threads) {
            if (thread.isAlive())
                thread.interrupt();
        }
    }

    //Метод getInterruptedTaskCount() возвращает количество тасков, которые не были выполены из-за отмены (вызовом предыдущего метода).
    @Override
    public int getInterruptedTaskCount() {
        int count = 0;
        for (Thread thread: threads) {
            if (thread.isInterrupted())
                count++;
        }
        return count;
    }

    //Метод isFinished() вернет true, если все таски были выполнены или отменены, false в противном случае. 
    @Override
    public boolean isFinished() {
        if (callback.isAlive())
            return false;
        for (Thread thread: threads) {
            if (thread.isAlive())
                return false;
        }
        return true;
    }
}
