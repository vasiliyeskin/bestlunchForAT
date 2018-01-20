[![Codacy Badge](https://api.codacy.com/project/badge/Grade/4902cf1a914d4c529c30d6cd3205e857)](https://www.codacy.com/app/vasiliyeskin/bestlunch?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=vasiliyeskin/bestlunch&amp;utm_campaign=Badge_Grade)

<a href="http://javaops.ru/reg/topjava?ch=javawebinar">TopJava</a> Graduation project - REST API
===============================

## Project description

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.

-----------------------------

Used tools, libraries and frameworks:
 <a href="http://maven.apache.org/">Maven</a>,
 <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html">Spring MVC</a>,
 <a href="http://projects.spring.io/spring-security/">Spring Security</a>,
 <a href="http://projects.spring.io/spring-data-jpa/">Spring Data JPA</a>,
 <a href="http://spring.io/blog/2014/05/07/preview-spring-security-test-method-security">Spring Security Test</a>,
 <a href="http://hibernate.org/orm/">Hibernate ORM</a>,
 <a href="http://hibernate.org/validator/">Hibernate Validator</a>,
 <a href="http://www.slf4j.org/">SLF4J</a>,
 <a href="https://github.com/FasterXML/jackson">Json Jackson</a>,
 <a href="http://ehcache.org">Ehcache</a>,
 <a href="http://hsqldb.org//">HSQLDB</a>,
 <a href="http://junit.org/">JUnit</a>.

## <a href="http://bestlunch.herokuapp.com"> Deployed version of the graduation project </a>

## REST API Developer Endpoint Reference
| Endpoint      |     Base route          | Consumer | Functions      | Description       |
|:--------------|:----------------------- |:-------- |:-------------- |:------------------|
| Dishes        | /rest/admin/dishes      | admin    | CRUD           |                   |
| Restaurants   | /rest/admin/restaurants | admin    | CRUD           |                   |     
| Dishes Of Day | /rest/admin/dishesofday | admin    | CRUD           |                   |
| Votes         | /rest/vote              | user     | create, delete |                   |
| DishesofDay   | /rest/dishesofday       | user     | get            | get dishes of day |
| RAD           | /rest/rad               | user     | get            | get full description of restaurants and today dishes              |
| Today         | /rest/vote/today        | user     | get            | get result of the today vote              |
| Currentuser   | /rest/vote/currentuser  | user     | get            | get vote of current user              |

## Curl commands to test API
> For windows use `Git Bash`

> For deployed version change "http://localhost:8080/bestlunch" to "http://bestlunch.herokuapp.com" in the curl tests

Admin
-----------
### Restaurants
#### get All Restaurants
`curl -s http://localhost:8080/bestlunch/rest/admin/restaurants --user admin@gmail.com:admin`

#### get Restaurant 200001
`curl -s http://localhost:8080/bestlunch/rest/admin/restaurants/200001 --user admin@gmail.com:admin`

#### delete Restaurant
`curl -s -X DELETE http://localhost:8080/bestlunch/rest/admin/restaurants/200001 --user admin@gmail.com:admin`

#### create Restaurant
`curl -s -X POST -d '{"title":"gell-mann'\''s restaurant","address":"Nizhny Novgorod, 13 Gagarina ave.","email":"gell-mann@mail.ru","site":"gell-mann.ru"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/bestlunch/rest/admin/restaurants --user admin@gmail.com:admin`

#### update Restaurant
`curl -s -X PUT -d '{"title":"gell","address":"Nizhny Novgorod, 13 Gagarina ave.","email":"gell-mann@mail.ru","site":"gell.ru"}' -H 'Content-Type: application/json' http://localhost:8080/bestlunch/rest/admin/restaurants/200003 --user admin@gmail.com:admin`#### update Restaurant




### Dishes

#### get All Dishes
`curl -s http://localhost:8080/bestlunch/rest/admin/dishes --user admin@gmail.com:admin`

#### get Dish 300001
`curl -s http://localhost:8080/bestlunch/rest/admin/dishes/300001 --user admin@gmail.com:admin`

#### delete Dish
`curl -s -X DELETE http://localhost:8080/bestlunch/rest/admin/dishes/300001 --user admin@gmail.com:admin`

#### create Dish
`curl -s -X POST -d '{"name":"lobster2","price":12000,"restaurant_id":200001}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/bestlunch/rest/admin/dishes --user admin@gmail.com:admin`

#### update Dish
`curl -s -X PUT -d '{"id":300006,"name":"lobbbbf","price":13000,"registered":"2017-10-01T07:57:48.776+0000","restaurant_id":200001}' -H 'Content-Type: application/json' http://localhost:8080/bestlunch/rest/admin/dishes/300006 --user admin@gmail.com:admin`#### update Dish




### table Dishes Of Day

#### get All DishesOfDay
`curl -s http://localhost:8080/bestlunch/rest/admin/dishesofday --user admin@gmail.com:admin`

#### get DishesOfDay 1
`curl -s http://localhost:8080/bestlunch/rest/admin/dishesofday/1 --user admin@gmail.com:admin`

#### delete DishesOfDay
`curl -s -X DELETE http://localhost:8080/bestlunch/rest/admin/dishesofday/1 --user admin@gmail.com:admin`

#### create Dish
`curl -s -X POST -d '{"datelunch":"2017-10-01T06:00:00.000+0000","dish_id":300000}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/bestlunch/rest/admin/dishesofday --user admin@gmail.com:admin`

User
-----------
### Dishes of Day
#### get All Dishes of Day
`curl -s http://localhost:8080/bestlunch/rest/dishesofday --user user@yandex.ru:password`

### Today Restaurant and Dishes
#### get Restaurant and Dishes
`curl -s http://localhost:8080/bestlunch/rest/rad --user user@yandex.ru:password`

### Vote
#### get today result of vote
`curl -s http://localhost:8080/bestlunch/rest/vote/today --user user@yandex.ru:password`

#### get Current User Today Vote
`curl -s http://localhost:8080/bestlunch/rest/vote/currentuser --user user@yandex.ru:password`

#### vote for Restaurant with id=200001
`curl -s -X POST http://localhost:8080/bestlunch/rest/vote/200001 --user user@yandex.ru:password`

#### revote the Restaurant with id=200002 by user@yandex.ru
`curl -s -X POST http://localhost:8080/bestlunch/rest/vote/200002 --user user@yandex.ru:password`

#### delete Vote
`curl -s -X DELETE http://localhost:8080/bestlunch/rest/vote/0 --user user@yandex.ru:password`


