package patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class StrategyPattern {
    public static void main(String[] args) {
        List<Stock> stockList = new ArrayList<>();
        stockList.add(new Stock("Apple", 100, 10));
        stockList.add(new Stock("Google", 200, 20));
        stockList.add(new Stock("Microsoft", 300, 30));
        stockList.add(new Stock("Amazon", 400, 40));
        stockList.add(new Stock("Facebook", 500, 50));



        StrategyPattern.filter(stockList, (Stock stock) -> stock.price > 200).forEach(System.out::println);
    }

    public static List<Stock> filter(List<Stock> stockList, Predicate<Stock> predicate) {
        /**
          This method filters the stockList based on the predicate passed. Better to be declared in separete class
         */

        List<Stock> filteredList = new ArrayList<>();
        for (Stock stock : stockList) {
            if (predicate.test(stock)) {
                filteredList.add(stock);
            }
        }
        return filteredList;
    }
}
