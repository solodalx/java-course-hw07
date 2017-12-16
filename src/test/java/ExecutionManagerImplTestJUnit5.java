
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.function.BiFunction;

class ExecutionManagerImplTestJUnit5 {

    @Test
    void test01() {
        ExecutionManager manager = new ExecutionManagerImpl();
        Context context = null;

        context = manager.execute(new TaskSleep("Callback", 3000),
                new TaskIter("Task1", 1_000_000, (r,i) -> (r + i)),
                new TaskSleep("Task2", 3000),
                new TaskSleep("Task3", 7000));

//        assertEquals();
    }

}

