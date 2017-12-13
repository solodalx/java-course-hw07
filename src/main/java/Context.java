public interface Context {
    //Метод getCompletedTaskCount() возвращает количество тасков, которые на текущий момент успешно выполнились.
    int getCompletedTaskCount();

    //Метод getFailedTaskCount() возвращает количество тасков, при выполнении которых произошел Exception.
    int getFailedTaskCount();

    //Метод interrupt() отменяет выполнения тасков, которые еще не начали выполняться.
    void interrupt();

    //Метод getInterruptedTaskCount() возвращает количество тасков, которые не были выполены из-за отмены (вызовом предыдущего метода).
    int getInterruptedTaskCount();

    //Метод isFinished() вернет true, если все таски были выполнены или отменены, false в противном случае. 
    boolean isFinished();
}
