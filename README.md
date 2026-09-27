# product-parser-multithread-test — многопоточный парсер товаров

Учебный проект на Java: сравнение подходов к параллельному «парсингу» списка товаров — последовательно, через `Future` и через `CompletableFuture` на виртуальных потоках. Реальный парсер заменён заглушкой `FakeParser`, имитирующей медленный запрос к сайту.

## Возможности

- Три реализации сервиса парсинга с общим контрактом `ParserService`:
  - `ParserServiceSimple` — последовательный обход списка (базовая линия);
  - `ParserServiceWithFutures` — `ExecutorService.newVirtualThreadPerTaskExecutor()` + `Future.get()`;
  - `ParserServiceWithCompletableFutures` — виртуальные потоки + `CompletableFuture.supplyAsync()` с таймаутом `completeOnTimeout()` (3 секунды, дефолтный результат) и пост-обработкой `thenApply()` (наценка НДС 22% к цене).
- `FakeParser` имитирует парсинг: задержка 1–4 секунды и случайные название/цена — чтобы разница подходов была видна на 100 товарах.
- `Main` (компактная точка входа Java 25) замеряет время выполнения парсинга и печатает результаты.

## Технологии

- Java 25 (`maven.compiler.source/target = 25`), Maven
- Только стандартная библиотека: `java.util.concurrent` (виртуальные потоки, CompletableFuture), внешних зависимостей нет

## Сборка и запуск

```bash
mvn compile

# запуск
java -cp target/classes ru.kuzdikenov.productparser.Main

# либо после mvn package
java -cp target/product-parser-multithread-test-1.0-SNAPSHOT.jar ru.kuzdikenov.productparser.Main
```

Чтобы сравнить подходы, подставьте в `Main` нужную реализацию:

```java
ParserService parserService = new ParserServiceWithCompletableFutures(new FakeParser());
// new ParserServiceWithFutures(new FakeParser())
// new ParserServiceSimple(new FakeParser())
```

## Структура проекта

```
src/main/java/ru/kuzdikenov/productparser/
├── Main.java                                  # точка входа: 100 товаров, замер времени
├── parser/
│   ├── Parser.java                            # интерфейс парсера
│   ├── FakeParser.java                        # заглушка: задержка 1–4 с, случайный результат
│   └── ParseTask.java                         # Callable<ParseResult> для executor'а
├── model/
│   ├── Product.java                           # товар (код)
│   └── ParseResult.java                       # результат парсинга (название, цена)
└── service/
    ├── ParserService.java                     # абстрактный сервис
    ├── ParserServiceSimple.java               # последовательный вариант
    ├── ParserServiceWithFutures.java          # виртуальные потоки + Future
    └── ParserServiceWithCompletableFutures.java  # CompletableFuture + таймаут + НДС
```
