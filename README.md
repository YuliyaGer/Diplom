# Diplom

[План автоматизации тестирования](https://github.com/YuliyaGer/Diplom/blob/master/Plan.md)

Запуск в MySQL:
1.	Запустить машину: docker-machine start default
2.	Запустить контейнеры: docker-compose -f docker-compose-mysql.yml up –d
3.	Запустить SUT: java -jar artifacts/aqa-shop.jar
4.	Запустить тесты: gradew test
5.	Остановить контейнеры: docker-compose -f docker-compose-mysql.yml down
6.	Остановить машину: docker-machine stop default

Запуск для Postgres:
1.	Запустить машину: docker-machine start default
2.	Запустить контейнеры: docker-compose -f docker-compose-postgres.yml up
3.	Запустить SUT: java -jar artifacts/aqa-shop.jar
4.	Запустить тесты: gradew test
5.	Остановить контейнеры: docker-compose -f docker-compose-postgres.yml down
6.	Остановить машину: docker-machine stop default
