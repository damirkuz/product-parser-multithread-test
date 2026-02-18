package ru.kuzdikenov.productparser;

import ru.kuzdikenov.productparser.model.ParseResult;
import ru.kuzdikenov.productparser.model.Product;
import ru.kuzdikenov.productparser.parser.FakeParser;
import ru.kuzdikenov.productparser.service.ParserService;
import ru.kuzdikenov.productparser.service.ParserServiceWithCompletableFutures;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main() {
        ParserService parserService = new ParserServiceWithCompletableFutures(new FakeParser());

        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            products.add(new Product("123"));
        }

        long before = System.currentTimeMillis();
        List<ParseResult> results = parserService.parseProducts(products);
        System.out.println(System.currentTimeMillis() - before);

        results.forEach(System.out::println);
    }
}
