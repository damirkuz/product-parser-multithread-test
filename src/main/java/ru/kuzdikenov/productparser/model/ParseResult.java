package ru.kuzdikenov.productparser.model;

public class ParseResult {
    private String name;
    private int price;

    public ParseResult(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ParseResult{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
