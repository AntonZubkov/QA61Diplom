# Название проекта.
___
Автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

## Начало работы.
___
Для того что бы получить копию проекта себе на ПК необходимо:
* перейти по [ссылке](https://github.com/AntonZubkov/QA61Diplom)
* во вкладке "Code" копируем [ссылку](https://github.com/AntonZubkov/QA61Diplom.git) по https
* в терминале пишем команду **git clone** и вставляем скопированную ссылку
* нажимаем **enter**

### Prerequisites
___
* Google Chrome
* Git
* InetelliJ IDEA
* Docker Desktop
* DBeaver

### Установка и запуск
___
* установить [Google Chrome](https://support.google.com/chrome/answer/95346?hl=ru&co=GENIE.Platform%3DDesktop) на ПК
* установить [IntelliJ IDEA](https://itlearn.ru/how-to-install-intellij-idea-community) на ПК
* установить [Git](https://selectel.ru/blog/tutorials/how-to-install-git-to-windows/) на ПК
* установить [Docker Desktop](https://github.com/netology-code/aqa-homeworks/blob/master/docker/installation.md) на ПК
* установить [DBeaver](https://dbeaver.ru.uptodown.com/windows) на ПК
* запустить Docker Desktop
* запустить DBeaver
* запустить IntelliJ IDEA
* в IntelliJ IDEA в новом окне открыть нужный проект
* открыть терминал в IntelliJ IDEA
* в терминале ввести команду **docker compose up --build**, нажать **enter**
* дождаться появления надписи **ready for connections**
* открыть второе окно терминала
* в терминале ввести команду для запуска jar файла:
  * mysql **java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar**
  * postgresql **java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar**

### Запуск автотестов
___
* в терминале открываем новое окно и вводим команду:
  * для mysql ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"
  * для postgresql ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"
