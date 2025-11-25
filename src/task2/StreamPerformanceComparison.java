package task2;

import java.util.*;
import java.util.stream.*;
import java.time.*;

public class StreamPerformanceComparison {
    private static final int DATA_SIZE = 1_000_000; // можно изменить на 10_000_000 при достаточной памяти

    public static void main(String[] args) {
        List<Integer> numbers = IntStream.rangeClosed(1, DATA_SIZE)
            .boxed()
            .collect(Collectors.toList());

        System.out.println("Количество процессоров: " + Runtime.getRuntime().availableProcessors());
        testFiltering(numbers);
        testSquareAndSum(numbers);
        testPrimeNumbers();
    }

    private static void testFiltering(List<Integer> numbers) {
        System.out.println("\n--- Операция 1: Фильтрация чётных чисел ---");
        long startSeq = System.nanoTime();
        long countSeq = numbers.stream().filter(n -> n % 2 == 0).count();
        long timeSeq = Duration.ofNanos(System.nanoTime() - startSeq).toMillis();

        long startPar = System.nanoTime();
        long countPar = numbers.parallelStream().filter(n -> n % 2 == 0).count();
        long timePar = Duration.ofNanos(System.nanoTime() - startPar).toMillis();

        printResults("Последовательный", countSeq, timeSeq);
        printResults("Параллельный", countPar, timePar);
        printSpeedup(timeSeq, timePar);
    }

    private static void testSquareAndSum(List<Integer> numbers) {
        System.out.println("\n--- Операция 2: Возведение в квадрат и суммирование ---");
        long startSeq = System.nanoTime();
        long sumSeq = numbers.stream().mapToLong(n -> (long)n * n).sum();
        long timeSeq = Duration.ofNanos(System.nanoTime() - startSeq).toMillis();

        long startPar = System.nanoTime();
        long sumPar = numbers.parallelStream().mapToLong(n -> (long)n * n).sum();
        long timePar = Duration.ofNanos(System.nanoTime() - startPar).toMillis();

        printResults("Последовательный", sumSeq, timeSeq);
        printResults("Параллельный", sumPar, timePar);
        printSpeedup(timeSeq, timePar);
    }

    private static void testPrimeNumbers() {
        System.out.println("\n--- Операция 3: Поиск простых чисел (1-100000) ---");
        int limit = 100000;
        long startSeq = System.nanoTime();
        long primesSeq = IntStream.rangeClosed(1, limit).filter(StreamPerformanceComparison::isPrime).count();
        long timeSeq = Duration.ofNanos(System.nanoTime() - startSeq).toMillis();

        long startPar = System.nanoTime();
        long primesPar = IntStream.rangeClosed(1, limit).parallel().filter(StreamPerformanceComparison::isPrime).count();
        long timePar = Duration.ofNanos(System.nanoTime() - startPar).toMillis();

        printResults("Последовательный", primesSeq, timeSeq);
        printResults("Параллельный", primesPar, timePar);
        printSpeedup(timeSeq, timePar);
    }

    private static boolean isPrime(int n) {
        if (n <= 1) return false;
        return IntStream.rangeClosed(2, (int)Math.sqrt(n)).noneMatch(i -> n % i == 0);
    }

    private static void printResults(String type, long result, long time) {
        System.out.println(type + " поток:");
        System.out.println("Результат: " + result);
        System.out.println("Время: " + time + " мс");
    }

    private static void printSpeedup(long seqTime, long parTime) {
        if (parTime == 0) parTime = 1;
        double speedup = (double) seqTime / parTime;
        System.out.printf("Ускорение: %.2fx%n", speedup);
    }
}
