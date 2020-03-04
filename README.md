# Diplom

[План автоматизации тестирования](https://github.com/YuliyaGer/Diplom/blob/master/documents/Plan.md)

[Отчет по автоматизации](https://github.com/YuliyaGer/Diplom/blob/master/documents/Automatization%20summary.md)

[Отчет по итогам тестирования](https://github.com/YuliyaGer/Diplom/blob/master/documents/Summary.md)

Запуск в MySQL:
1.	Запустить машину: docker-machine start default
2.	Запустить контейнеры: docker-compose -f docker-compose-mysql.yml up –d
3.	Запустить SUT:java -jar artifacts/aqa-shop.jar
4.	Запустить тесты:gradlew test 
5.	Остановить контейнеры: docker-compose -f docker-compose-mysql.yml down
6.	Остановить машину: docker-machine stop default

Запуск для Postgres:
1.	Запустить машину: docker-machine start default
2.	Запустить контейнеры: docker-compose -f docker-compose-postgres.yml up -d
3.	Запустить SUT: java -Dspring.datasource.url=jdbc:postgresql://192.168.99.100:5432/app -jar artifacts/aqa-shop.jar
4.	Запустить тесты:gradlew -Ddb.url=jdbc:postgresql://192.168.99.100:5432/app test
5.	Остановить контейнеры: docker-compose -f docker-compose-postgres.yml down
6.	Остановить машину: docker-machine stop default
