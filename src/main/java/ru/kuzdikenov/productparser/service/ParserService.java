package ru.kuzdikenov.productparser.service;

import ru.kuzdikenov.productparser.model.ParseResult;
import ru.kuzdikenov.productparser.model.Product;
import ru.kuzdikenov.productparser.parser.Parser;

import java.util.List;

public abstract class ParserService {
    protected final Parser parser;

    public ParserService(Parser parser) {
        this.parser = parser;
    }

    public abstract List<ParseResult> parseProducts(List<Product> products);
}
