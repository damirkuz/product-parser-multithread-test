package ru.kuzdikenov.productparser.service;

import ru.kuzdikenov.productparser.model.ParseResult;
import ru.kuzdikenov.productparser.model.Product;
import ru.kuzdikenov.productparser.parser.ParseTask;
import ru.kuzdikenov.productparser.parser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParserServiceWithFutures extends ParserService {

    public ParserServiceWithFutures(Parser parser) {
        super(parser);
    }

    @Override
    public List<ParseResult> parseProducts(List<Product> products) {
        List<Future<ParseResult>> futures = new ArrayList<>();
        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            for (Product product: products) {
                futures.add(executorService.submit(new ParseTask(parser, product)));
            }
        }

        List<ParseResult> results = new ArrayList<>();

        futures.forEach(f -> {
            try {
                results.add(f.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        return results;
    }

}
