# WebService

Collection of all completed tasks in one file for the Mail.ru Group course "Web service development" on the Stepik training platform.

Key Features:
- [Maven](pom.xml) - dependencies and project build
- Jetty - server ([websockets](src/main/java/chat), [chat](src/main/java/simpleChatServer))
- [Freemaker](src/main/java/templater/PageGenerator.java) - template engine
- GSON library for working with JSON
- [Servlets (extends HttpServlet)](src/main/java/servlets) for receiving Requests and processing Response
- [SAXparser for working with XML files](src/main/java/sax/ReadXMLFileSAX.java)
- [Reflection](src/main/java/reflection) - creating an object of a given class from an XML file
- Log4j2 - logging
- MySQL - database
- Hibernate - entities and queries to the database. [configuration](src/main/java/services/DBService.java)
When you start, the chat and login form work.
After receiving a POST request from the login form, the database is contacted in search of the user profile.