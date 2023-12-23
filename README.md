Technical requirement:
===============================
Design and implement a REST API using Hibernate/Spring/SpringMVC (Spring-Boot preferred!) without frontend.

The task is:

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users
Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote for a restaurant they want to have lunch at today
Only one vote counted per user
If user votes again the same day:
If it is before 11:00 we assume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides a new menu each day.

Техническое задание:
===============================
Спроектируйте и реализуйте REST API с использованием Hibernate/Spring/Spring MVC (предпочтительнее Spring-Boot!) без front end.

Задача заключается в следующем:

Создайте систему голосования для принятия решения о том, где пообедать.

2 типа пользователей: администратор и обычные пользователи
Администратор может ввести ресторан и его обеденное меню на день (обычно 2-5 позиций, только название блюда и цена)
Меню меняется каждый день (администраторы вносят обновления)
Пользователи могут проголосовать за ресторан, в котором они хотят пообедать сегодня
Для каждого пользователя учитывается только один голос
Если пользователь проголосует повторно в тот же день:
Если это произойдет до 11:00, мы предполагаем, что он передумал.
Если это после 11:00, значит, уже слишком поздно, голосование изменить нельзя
Каждый ресторан каждый день предоставляет новое меню.

-------------------------------------------------------------
- Stack: [JDK 20](http://jdk.java.net/20/), Spring Boot 3.x, Lombok, H2, Caffeine Cache, SpringDoc OpenApi 2.x, Mapstruct, Liquibase
- Run: `mvn spring-boot:run` in root directory.
-----------------------------------------------------
[REST API documentation](http://localhost:8080/swagger-ui/index.html)  
Креденшелы:
```
Admin: admin@gmail.com / admin
User:  user@yandex.ru / password
```
