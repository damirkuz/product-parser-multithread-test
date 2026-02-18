package ru.kuzdikenov.productparser.service;

import ru.kuzdikenov.productparser.model.ParseResult;
import ru.kuzdikenov.productparser.model.Product;
import ru.kuzdikenov.productparser.parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class ParserServiceSimple extends ParserService {


    public ParserServiceSimple(Parser parser) {
        super(parser);
    }

    public List<ParseResult> parseProducts(List<Product> products) {
        List<ParseResult> results = new ArrayList<>();

        for (Product product: products) {
            results.add(parser.parse(product));
        }

        return results;
    }

}
