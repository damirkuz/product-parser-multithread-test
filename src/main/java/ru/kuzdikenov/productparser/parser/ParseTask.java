package ru.kuzdikenov.productparser.parser;

import ru.kuzdikenov.productparser.model.ParseResult;
import ru.kuzdikenov.productparser.model.Product;

import java.util.concurrent.Callable;

public class ParseTask implements Callable<ParseResult> {

    private final Parser parser;
    private final Product product;

    public ParseTask(Parser parser, Product product) {
        this.parser = parser;
        this.product = product;
    }

    @Override
    public ParseResult call() {
        return parser.parse(product);
    }
}
