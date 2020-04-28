# WebService

Сборка всех выполненных заданий в одном файле по курсу Mail.ru Group "Разработка веб-сервиса" на учебной платформе Stepik.

Основные особенности:
- [Maven](pom.xml) - зависимости и сборка проекта
- Jetty - сервер ([вебсокеты](src/main/java/chat), [сделан чат](src/main/java/simpleChatServer))
- [Freemaker](src/main/java/templater/PageGenerator.java) - шаблонизатор
- GSON библиотека для работы с JSON
- [Сервлеты (extends HttpServlet)](src/main/java/servlets) для получения Requests и обработки Response
- [SAXparser для работы с XML файлами](src/main/java/sax/ReadXMLFileSAX.java)
- [Reflection](src/main/java/reflection) - создание объекта заданного класса из XML файла
- Log4j2 - логгирование
- MySQL - бд
- Hibarnate - сущности и запросы к БД. [конфигурация](src/main/java/services/DBService.java)

При запуске работает чат, форма логина.
После получения POST запроса с формы логина, происходит обращение в БД в поисках профиля юзера.

Внимание! Проект учебный, нецелостный, код в комментариях-пояснениях (грязный).