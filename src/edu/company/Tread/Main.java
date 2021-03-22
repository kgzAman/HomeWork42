package edu.company.Tread;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        EchoServer echoServer = new EchoServer(8787);
        echoServer.run();
    }

//        ExecutorService pool = Executors.newCachedThreadPool();
//
//        int taskCount = 12;
//        submitTaskInto(pool,taskCount);
//        System.out.println("-------------------------------");
//        pool.shutdown();
//        measure(pool);
//    }
//    public static void submitTaskInto(ExecutorService pool, int taskCount){
//        System.out.println("Создаем задачи");
//        IntStream.range(1,taskCount)
//                .mapToObj(i->makeTask(i))
//                .forEach(pool::submit);
//    }
//    private static Runnable makeTask(int taskID){
//        int temp = new Random().nextInt(2000)+1000;
//        int taskTime =(int) TimeUnit.MICROSECONDS.toSeconds(temp);
//        return ()->heavyTask(taskID,taskTime);
//    }
//
//    private static void heavyTask(int taskId, int taskTime ){
//        String msg = String.format("Задача %s займет %s секунд",taskId,taskTime);
//        System.out.println(msg);
//        try {
//            Thread.sleep(taskTime*1000);
//            System.out.printf("Завершилась задача %s"+
//                    "выполнилась за %s секунд%n",taskId,taskTime);
//        }catch (InterruptedException e ){
//            e.printStackTrace();
//        }
//    }
//    private static void measure(ExecutorService pool){
//        long start  = System.nanoTime();
//        try{
//            pool.awaitTermination(600, TimeUnit.SECONDS);
//        }catch (InterruptedException e ){
//            e.printStackTrace();
//        }
//        long delta = TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-start);
//        System.out.printf("Выполнение заняло: %s мсек %n",delta);
//    }
}
