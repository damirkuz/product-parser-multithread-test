package ru.kuzdikenov.productparser;

import ru.kuzdikenov.productparser.model.ParseResult;
import ru.kuzdikenov.productparser.model.Product;
import ru.kuzdikenov.productparser.parser.FakeParser;
import ru.kuzdikenov.productparser.service.ParserService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main() {
        ParserService parserService = new ParserService(new FakeParser());

        List<Product> products = new ArrayList<>();

        List<ParseResult> results = parserService.parseProducts(products);

        results.forEach(System.out::println);
    }
}
