import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;


public class ExecutionManagerImplTestJUnit4 {
    @Test
    public void test01() {
        ExecutionManager manager = new ExecutionManagerImpl();
        Context context = null;

        context = manager.execute(new TaskSleep("Callback", 3000),
                new TaskIter("Task1", 1_000_000, (r,i) -> (r + i)),
                new TaskSleep("Task2", 3000),
                new TaskSleep("Task3", 7000));

        assertEquals(0, context.getCompletedTaskCount());
        assertFalse(context.isFinished());

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(2, context.getCompletedTaskCount());

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(3, context.getCompletedTaskCount());

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(3, context.getCompletedTaskCount());

        while(!context.isFinished()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test02() {
        ExecutionManager manager = new ExecutionManagerImpl();
        Context context = null;

        context = manager.execute(new TaskSleep("Callback", 3000),
                new TaskIter("Task1", 1_000_000_000, (r,i) -> (r + i)),
                new TaskSleep("Task2", 3000),
                new TaskSleep("Task3", 7000));

        assertEquals(0, context.getCompletedTaskCount());
        assertFalse(context.isFinished());

        List<Thread> threads = context.getThreads();
        assertEquals(3, threads.size());

        threads.get(1).interrupt();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(1, context.getCompletedTaskCount());

        threads.get(0).interrupt();
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        assertEquals(2, context.getCompletedTaskCount());

        while(!context.isFinished()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


class TaskIter implements Runnable {
    private String name;
    private long iterations;
    private BiFunction<Long, Long, Long> function;

    public TaskIter(String name, long iterations, BiFunction<Long, Long, Long> function) {
        this.name = name;
        this.iterations = iterations;
        this.function = function;
    }

    @Override
    public void run() {
        long start = new Date().getTime();
        System.err.println("Поток " + name + " начал работу");

        long result = 0;
        for (long i = 0; i < iterations; i++) {
            result = function.apply(result, i);
//            if (i % 1000 == 0) {
//                try {
//                    Thread.sleep(0);
//                } catch (InterruptedException e) {
//                    long end = new Date().getTime();
//                    System.err.println("Поток " + name + " прерван через " + ((end - start)/1_000.) + " сек.");
//                    return;
//                }
//            }
        }

        long end = new Date().getTime();
        System.err.println("Поток " + name + " завершил работу за " + ((end - start)/1_000.) + " сек.");
    }
}


class TaskSleep implements Runnable {
    private String name;
    private long millisec;

    public TaskSleep(String name, long millisec) {
        this.name = name;
        this.millisec = millisec;
    }

    @Override
    public void run() {
        long start = new Date().getTime();
        System.err.println("Поток " + name + " начал работу");

        try {
            Thread.sleep(millisec);
        } catch (InterruptedException e) {
            long end = new Date().getTime();
            System.err.println("Поток " + name + " прерван через " + ((end - start)/1_000.) + " сек.");
//            e.printStackTrace();
            return;
        }

        long end = new Date().getTime();
        System.err.println("Поток " + name + " завершил работу за " + ((end - start)/1_000.) + " сек.");
    }
}

class TaskExcept implements Runnable {
    private String name;
    private long millisec;

    public TaskExcept(String name, long millisec) {
        this.name = name;
        this.millisec = millisec;
    }

    @Override
    public void run() {
        long start = new Date().getTime();
        System.err.println("Поток " + name + " начал работу");

        try {
            Thread.sleep(millisec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        throw new InterruptedException();


        long end = new Date().getTime();
        System.err.println("Поток " + name + " завершил работу за " + ((end - start)/1_000.) + " сек.");
    }
}
