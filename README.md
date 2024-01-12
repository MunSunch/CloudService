# Облачное хранилище
## Описание задания: [ссылка](https://github.com/netology-code/jd-homeworks/blob/master/diploma/cloudservice.md)
## Описание решения
Backend сервис для хранения файлов, взаимодействует с [frontend](https://github.com/netology-code/jd-homeworks/tree/master/diploma/netology-diplom-frontend)
по принципам REST. Особенностью проекта является
применение JWT аутентификации, реализация которой выполнена внутри самого сервиса. Используемые технологии Spring Boot 3.x,
Java 17, Docker, Liquebase, PostgreSQL, JUnit, Mockito, Testcontainers.
## Перед началом работы
1) Заранее определен администратор с глобальными правами: логин=admin, пароль=admin. Механизма для регистрации новых пользователей не предусмотрено, поэтому для работы использовать его.
2) Каждый запрос клиента серверу должен содержать заголовок auth-token, в котором находится токен, получаемый при успешной аутентификации.
3) Разрешенный размер файла для хранения < 10 Mb.
4) Имя загружаемого файла должно содержать одну точку, т.е. имя.расширение
5) (*) Запрещено вставлять файлы с одинаковыми именами
## Запуск
Перейти в корень, собрать проект:
```
mvn clean package --Dskiptests
```
В корневой папке выполнить команду:
```
docker compose up
```
## Примеры запуска
Вход в систему от имени администратора

![](img/front/login.png)

Интерфейс пользователя

![](img/front/main_page.png)

Загрузка файлов в хранилище

![](img/front/load_file_to_main_page.png)

Переименование файла в хранилище

![](img/front/rename_file.png)

Выгрузка файлов 

![](img/front/download_file.png)

Удаление файла

![](img/front/remove_file.png)

Работа с несколькими файлами

![](img/front/download_files.png)

# Примеры запуска без клиента
Успешный вход от имени администратора

![](img/backend/login.png)

Получение отрицательного числа файлов из хранилища

![](img/backend/error_get_files.png)

Загрузка файла с истекшим токеном аутентификации

![](img/backend/error_load.png)

Удаление несуществующего файла

![](img/backend/remove_file_error.png)