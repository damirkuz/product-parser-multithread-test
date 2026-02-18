package ru.kuzdikenov.productparser.parser;

import ru.kuzdikenov.productparser.model.ParseResult;
import ru.kuzdikenov.productparser.model.Product;

public interface Parser {

    ParseResult parse(Product product);
}
