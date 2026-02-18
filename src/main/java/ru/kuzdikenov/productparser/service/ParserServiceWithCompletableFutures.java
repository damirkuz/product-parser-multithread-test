package ru.kuzdikenov.productparser.service;

import ru.kuzdikenov.productparser.model.ParseResult;
import ru.kuzdikenov.productparser.model.Product;
import ru.kuzdikenov.productparser.parser.ParseTask;
import ru.kuzdikenov.productparser.parser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParserServiceWithCompletableFutures extends ParserService {

    public ParserServiceWithCompletableFutures(Parser parser) {
        super(parser);
    }

    @Override
    public List<ParseResult> parseProducts(List<Product> products) {
        List<CompletableFuture<ParseResult>> futures = new ArrayList<>();

        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (Product product: products) {
                futures.add(
                        CompletableFuture.supplyAsync(
                                () -> new ParseTask(parser, product).call(),
                                executorService) // закидываем задачу
                                // ставим макс время выполнения, иначе в ответ пойдет дефолтный
                                .completeOnTimeout(new ParseResult("default", 1), 3, TimeUnit.SECONDS)
                                // аналогично стрим апи, как-то меняем результат
                                .thenApply( pr -> {
                                    pr.setPrice((int) (pr.getPrice() * 1.22)); // ндс)
                                    return pr;
                                })
                );
            }
        } // к выходу из try блока все задачи будут выполнены, тк executor не может закрыться без этого

        return futures.stream().map(CompletableFuture::join).toList();


        // альтернативный, по факту бесполезный из-за верхнего комментария
        // способ дождаться всех задач
//        // смотрим, что все задачи завершились
//        List<ParseResult> results = CompletableFuture.allOf(
//                futures.toArray(new CompletableFuture[0])) // переводит в массив
//                // new CompletableFuture[0] - это массив для примера для функции toArray
//                .thenApply(
//                        v -> futures.stream()
//                                .map(CompletableFuture::join)
//                                .toList() // тк сверху проверили, что все завершились
//                                        // то собираем результаты
//                ) // на данном этапе CompletableFuture<List<ParseResult>>
//                .join(); // вытаскиваем результат
//        return results;
    }

}
