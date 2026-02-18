package ru.kuzdikenov.productparser;

import ru.kuzdikenov.productparser.model.ParseResult;
import ru.kuzdikenov.productparser.model.Product;
import ru.kuzdikenov.productparser.parser.FakeParser;
import ru.kuzdikenov.productparser.service.ParserService;
import ru.kuzdikenov.productparser.service.ParserServiceWithFutures;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main() {
        ParserService parserService = new ParserServiceWithFutures(new FakeParser());

        List<Product> products = new ArrayList<>();

        products.add(new Product("123"));
        products.add(new Product("123"));
        products.add(new Product("123"));

        long before = System.currentTimeMillis();
        List<ParseResult> results = parserService.parseProducts(products);
        System.out.println(System.currentTimeMillis() - before);

        results.forEach(System.out::println);
    }
}
