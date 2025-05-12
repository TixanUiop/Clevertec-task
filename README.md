# 🧾 Clevertec Task

🔖 **Автор:** Evgeny  
📄 [Тестовое задание (PDF)](docs/BackendDevCourse.pdf)

---

## 🛠️ Технологии

- Java 17  
- Gradle  
- Jakarta Servlet API  
- PostgreSQL  
- JUnit 5  
- Tomcat  

---

## 📌 Описание

Приложение реализовано в виде трёх модулей:

### 1. `demo`  
> Консольная версия без работы с PostgreSQL, JUnit5 и Servlets. Демонстрационная сборка с базовой логикой.

### 2. `database`  
> Консольная версия с подключением к PostgreSQL через JDBC.  
Реализовано покрытие тестами на JUnit5. Структура проекта выполнена по шаблону MVC.

### 3. `web`  
> Расширяет функциональность модуля `database`:  
- Убирает консольный вывод  
- Генерирует WAR-архив  
- Использует JSP/JSTL  
- Работает через Servlet API и разворачивается на веб-сервере (например, Tomcat)

---

## 🚀 Инструкция по запуску

### 📦 Подготовка базы данных
Для работы проекта требуется PostgreSQL с предзаполненной схемой.  
Можно:
- вручную создать базу данных, либо  
- воспользоваться SQL-скриптом:  
  `Clevertec-task/src/main/resources/sql/postgresql.sql`  
  (содержит DDL и DML команды)

---

### ▶️ Запуск модуля `database` (консольное приложение с PostgreSQL)

1. Перейти в директорию: Clevertec-task/src/main/resources/final-jars-or-wars
2. Запустить: java -jar Clevertec-1.0-FINAL_CONSOLE_APP_POSTGRES.jar 3-1 2-5 5-1 card-1234
3. Где:
- `3-1` означает ID товара = 3, количество = 1  
- `card-1234` — номер скидочной карты

⚠️ Если карта не существует — она игнорируется. Пример валидной карты: `card-1000`.

### 🌐 Запуск модуля `web` (через Tomcat)

1. Перейти в директорию: Clevertec-task/src/main/resources/final-jars-or-wars
2. Найти файл: Clevertec-2.0-FINAL_WEB_APP.war
3. Скопировать его в папку `webapps` вашего Tomcat-сервера.
4. Запустить сервер и перейти в браузере по адресу: http://localhost:8080/check?itemId=1&quantity=2&itemId=3&quantity=5&itemId=7&quantity=1&card=1000
---

## ❓ Что делать, если база данных на другой машине?

Приложение использует подключение к БД через JDBC.  
По умолчанию, настройки лежат в файле:  
src/main/resources/application.properties

### 🔧 Чтобы другой пользователь мог запустить приложение:
1. Открыть файл `application.properties`
2. Изменить параметры:

   url=jdbc:postgresql://localhost:5432/your_db_name
   username=your_username
   password=your_password
