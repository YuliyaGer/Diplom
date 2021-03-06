## План автоматизации тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.
Провести исследовательское тестирование и тестирование на основе опыта, так как нет спецификации и определенных требований к работе приложения и вводимым данным.

### Перечень автоматизированных сценариев покупки тура для обоих видов оплаты:
1. Провести положительные тест кейсы при заполнении валидными данными всех полей
Ожидаемый результат: после ввода валидных данных платеж успешно проходит, нет сообщений об ошибках
2. Провести негативные тест кейсы при заполнении не валидными данными каждого поля в отдельности, в том числе оставить поля пустыми
Ожидаемый результат: возникает сообщение об ошибке в каждом поле, куда введены не валидные данные, платеж не происходит
3. Проверить сохраняет ли приложение информацию в таблицах о том, каким способом был совершен платеж и успешно ли он был совершен.
Ожидаемый результат: в таблицах сохранена информация о способе и успешности платежа.

### Тест кейсы:
#### Положительные
#### 1.Заполнить все поля валидными данными для покупки 
1. Открыть страницу localhost:8080
2. На странице нажать кнопку "Купить"
3. На появившейся странице выбрать "Оплата по карте"
4. Заполнить поле номер карты: 4444 4444 4444 4441
5. Заполнить поля месяц, год, владелец, cvc/cvv
6. Нажать кнопку "продолжить"
7. Ожидаемый результат: увидеть сообщение: "Успешно! Операция одобрена банком"
8. Проверить, что платеж был успешен и запись Approved 
появилась в столбце "status" в таблице "payment_entity"
#### 2.Заполнить все поля валидными данными для покупки в кредит
1. Открыть страницу localhost:8080
2. На странице нажать кнопку "Купить в кредит"
3. На появившейся странице выбрать "Оплата по карте"
4. Заполнить поле номер карты: 4444 4444 4444 4441
5. Заполнить поля месяц, год, владелец, cvc/cvv
6. Нажать кнопку "продолжить"
7. Ожидаемый результат: увидеть сообщение: "Успешно! Операция одобрена банком"
8. Проверить, что платеж был успешен и запись Approved 
появилась в столбце "status" в таблице "credit_request_entity" 

### Негативные 
#### 3.Заполнить все поля данными для покупки в кредит
1. Открыть страницу localhost:8080
2. На странице нажать кнопку "Купить в кредит"
3. На появившейся странице выбрать "Оплата по карте"
4. Заполнить поле номер карты: 4444 4444 4444 4442
5. Заполнить поля месяц, год, владелец, cvc/cvv
6. Нажать кнопку "продолжить"
7. Ожидаемый результат: увидеть сообщение: "Неуспешно! Операция не одобрена банком"
8. Проверить, что в таблице появилась запись Decline 
в столбце "status" в таблице "credit_request_entity" 
#### 4.Заполнить все поля данными для покупки 
1. Открыть страницу localhost:8080
2. На странице нажать кнопку "Купить"
3. На появившейся странице выбрать "Оплата по карте"
4. Заполнить поле номер карты: 4444 4444 4444 4442
5. Заполнить поля месяц, год, владелец, cvc/cvv
6. Нажать кнопку "продолжить"
7. Ожидаемый результат: увидеть сообщение: "Неуспешно! Операция не одобрена банком"
8. Проверить, что в таблице появилась запись Decline 
   в столбце "status" в таблице "payment_entity"  

#### 5.Не полностью заполнить номер карты при покупке
1. Открыть страницу localhost:8080
2. На странице нажать кнопку "Купить"
3. На появившейся странице выбрать "Оплата по карте"
4. Заполнить поле номер карты: 4444 4444 4444 
5. Заполнить поля месяц, год, владелец, cvc/cvv
6. Нажать кнопку "продолжить"
7. Ожидаемый результат: увидеть ошибку в поле "номер карты" и отказ совершить платеж

#### 6.Не заполнять поле месяц при покупке
1. Открыть страницу localhost:8080
2. На странице нажать кнопку "Купить"
3. На появившейся странице выбрать "Оплата по карте"
4. Заполнить поле номер карты: 4444 4444 4444 4441
5. Не заполнять поле месяц 
6. Заполнить поля год, владелец, cvc/cvv
7. Нажать кнопку "продолжить"
8. Ожидаемый результат: увидеть ошибку в поле "месяц" и отказ совершить платеж

#### 7.Заполнить номер карты единицами при покупке в кредит
1. Открыть страницу localhost:8080
2. На странице нажать кнопку "Купить в кредит"
3. На появившейся странице выбрать "Оплата по карте"
4. Заполнить поле номер карты: 1111 1111 1111 1111
5. Заполнить поля месяц, год, владелец, cvc/cvv
6. Нажать кнопку "продолжить"
7. Ожидаемый результат: увидеть ошибку в поле "номер карты" 
и отказ совершить платеж

#### 8.Заполнить поле месяц не полностью при покупке
1. Открыть страницу localhost:8080
2. На странице нажать кнопку "Купить"
3. На появившейся странице выбрать "Оплата по карте"
4. Заполнить поле номер карты: 4444 4444 4444 4441
5. Заполнить поле месяц: 1
6. Заполнить поля год, владелец, cvc/cvv
7. Нажать кнопку "продолжить"
8. Ожидаемый результат: увидеть ошибку в поле "месяц" и отказ совершить платеж
#### 9.Заполнить поле "год" на 15 лет позже от текущей даты при покупке
1. Открыть страницу localhost:8080
2. На странице нажать кнопку "Купить"
3. На появившейся странице выбрать "Оплата по карте"
4. Заполнить поле номер карты: 4444 4444 4444 4441
5. Заполнить поле месяц
6. Заполнить поле год на 15 лет позже от текущей даты
7. Заполнить поля: владелец, cvc/cvv
8. Нажать кнопку "продолжить"
9. Ожидаемый результат: увидеть ошибку в поле "год" и отказ совершить платеж

#### 10.Заполнить поле на 2 года раньше текущей даты при покупке
1. Открыть страницу localhost:8080
2. На странице нажать кнопку "Купить"
3. На появившейся странице выбрать "Оплата по карте"
4. Заполнить поле номер карты: 4444 4444 4444 4441
5. Заполнить поле месяц
6. Заполнить поле на 2 года раньше от текущей даты
7. Заполнить поля: владелец, cvc/cvv
8. Нажать кнопку "продолжить"
9. Ожидаемый результат: увидеть ошибку в поле "год" и отказ совершить платеж

#### 11.Заполнить только имя при покупке
1. Открыть страницу localhost:8080
2. На странице нажать кнопку " Купить"
3. На появившейся странице выбрать "Оплата по карте"
4. Заполнить поле номер карты: 4444 4444 4444 4441
5. Заполнить поля месяц, год
6. Заполнить в поле владелец только имя
7. Заполнить поле cvc/cvv
8. Нажать кнопку "продолжить"
9. Ожидаемый результат: увидеть ошибку в поле "владелец" и отказ совершить платеж

#### 12.Заполнить поле именем через дефис и фамилией при покупке
1. Открыть страницу localhost:8080
2. На странице нажать кнопку "Купить"
3. На появившейся странице выбрать "Оплата по карте"
4. Заполнить поле номер карты: 4444 4444 4444 4441
5. Заполнить поля месяц, год
6. Заполнить в поле Владелец: Иван-Иван Иванов
7. Заполнить поле cvc/cvv
8. Нажать кнопку "продолжить"
9. Ожидаемый результат: увидеть сообщение: "Успешно! Операция одобрена банком"
10. Проверить, что платеж был успешен и запись Approved 
    появилась в столбце "status" в таблице "payment_entity"
#### 13.Заполнить поле владелец на английском языке при покупке в кредит
1. Открыть страницу localhost:8080
2. На странице нажать кнопку "Купить в кредит"
3. На появившейся странице выбрать "Оплата по карте"
4. Заполнить поле номер карты: 4444 4444 4444 4441
5. Заполнить поля месяц, год
6. Заполнить поле владелец на английском языке: "Ivan Ivanov"
7. Заполнить поле cvc/cvv
8. Нажать кнопку "продолжить"
9. Ожидаемый результат: увидеть ошибку в поле "Владелец" и отказ совершить платеж

## Перечень используемых инструментов с обоснованием выбора
1. IDEA - среда разработки  для написания тестов, удобная и популярная.
2. JUnit - платформа для написания авто-тестов и их запуска. 
Позволяет использовать аннотации для упрощения написания и читаемости кода. jUnit 5 содержит новые аннотации в том числе позволяет использовать параметризированные тесты (запускаемые несколько раз с различными исходными данными) с помощью аннотации @ParameterizedTest.
 Работает с Java 8
3. Java 8 - язык для написания авто-тестов, наиболее распространенная версия.
4. Gradle - инструмент автоматизации сборки и управления зависимостями (будет сама выкачивать все требуемые нам библиотеки после того, как мы пропишем зависимости). 
5. Loombook - упростит написание кода, уменьшит его количество и улучшит читаемость
6. Selenide - инструмент для автоматизированного тестирования веб-приложений на базе Selenium WebDriver, дающий определенные преимущества:
- сам запускает и закрывает браузер 
- сам делает скриншоты после тестов
- поддерживает Ajax

7.Faker - для генерации "фейковых" данных

8. GitHub - хранилище кода интегрированное с Git

9. Allure - инструмент для создания визуально наглядной картины выполнения тестов. Генерирует более подробные отчеты с более конкретной визуализацией, чем Gradle. 

## Перечень и описание возможных рисков при автоматизации
1. Проблемы с установкой Docker
2. Возникнут проблемы с запуском SUT и Docker
3. Возникновение проблем с поддержкой двух СУБД
4. Возможно, понадобиться дописать какие-то тесты важные для заказчика, в связи с отсутствием документации и спецификации на приложение.

### Интервальная оценка с учетом рисков (в часах): 70 -80 часов

### План сдачи работы
1. Написание тестов до 04.02.2019

2.Предоставление отчетов до 07.02.2019


