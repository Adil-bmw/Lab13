package task3;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SalesAnalyzer {
    public static void main(String[] args) {
        List<Sale> sales = generateSales(150);

        double totalRevenue = sales.stream().mapToDouble(Sale::getTotalAmount).sum();
        System.out.printf("Общая сумма продаж: %.2f%n", totalRevenue);

        System.out.println("\nПродажи по категориям (сумма):");
        Map<String, Double> revenueByCategory = sales.stream()
            .collect(Collectors.groupingBy(Sale::getCategory, Collectors.summingDouble(Sale::getTotalAmount)));
        revenueByCategory.forEach((k,v) -> System.out.printf("%s: %.2f%n", k, v));

        String topProduct = sales.stream()
            .collect(Collectors.groupingBy(Sale::getProduct, Collectors.summingInt(Sale::getQuantity)))
            .entrySet().stream().max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey).orElse("Не найдено");
        System.out.println("\nСамый продаваемый товар: " + topProduct);

        System.out.println("\nСредняя цена товара в каждой категории:");
        Map<String, Double> avgPriceByCategory = sales.stream()
            .collect(Collectors.groupingBy(Sale::getCategory, Collectors.averagingDouble(Sale::getPrice)));
        avgPriceByCategory.forEach((k,v) -> System.out.printf("%s: %.2f%n", k, v));

        System.out.println("\nТоп-5 самых дорогих продаж:");
        sales.stream()
            .sorted(Comparator.comparingDouble(Sale::getTotalAmount).reversed())
            .limit(5)
            .forEach(System.out::println);

        System.out.println("\nКоличество продаж по категориям:");
        Map<String, Long> countByCategory = sales.stream()
            .collect(Collectors.groupingBy(Sale::getCategory, Collectors.counting()));
        countByCategory.forEach((k,v) -> System.out.println(k + ": " + v));

        DoubleSummaryStatistics priceStats = sales.stream().mapToDouble(Sale::getPrice).summaryStatistics();
        System.out.printf("\nСтатистика по ценам: мин=%.2f макс=%.2f среднее=%.2f%n",
            priceStats.getMin(), priceStats.getMax(), priceStats.getAverage());
    }

    private static List<Sale> generateSales(int n) {
        List<Sale> sales = new ArrayList<>();
        Random rand = new Random();
        String[] categories = {"Электроника", "Одежда", "Продукты", "Книги"};
        String[][] products = {
            {"Ноутбук","Смартфон","Планшет","Наушники","Телевизор"},
            {"Футболка","Джинсы","Куртка","Кроссовки","Рубашка"},
            {"Хлеб","Молоко","Яйца","Сыр","Мясо"},
            {"Роман","Учебник","Комикс","Энциклопедия","Детектив"}
        };
        double[][] priceRanges = {{25000,120000},{2000,15000},{350,5000},{1500,8000}};
        for (int i=0;i<n;i++) {
            int idx = rand.nextInt(categories.length);
            String category = categories[idx];
            String product = products[idx][rand.nextInt(products[idx].length)];
            double min = priceRanges[idx][0], max = priceRanges[idx][1];
            double price = min + (max - min) * rand.nextDouble();
            int qty = rand.nextInt(5) + 1;
            LocalDate date = LocalDate.now().minusDays(rand.nextInt(30));
            sales.add(new Sale(product, category, price, qty, date));
        }
        return sales;
    }
}
