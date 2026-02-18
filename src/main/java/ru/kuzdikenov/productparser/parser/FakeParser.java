package ru.kuzdikenov.productparser.parser;

import ru.kuzdikenov.productparser.model.ParseResult;
import ru.kuzdikenov.productparser.model.Product;

import java.util.Random;

public class FakeParser implements Parser {

    private static final Random random = new Random();

    @Override
    public ParseResult parse(Product product) {
        try {
            Thread.sleep(1000 + random.nextInt(1000, 3000));
        } catch (InterruptedException _) {}

        return new ParseResult("name " + random.nextInt(1000),
                random.nextInt(10000));
    }
}
