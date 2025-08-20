# Hotel Microservices


This is a microservice-based Hotel Management System project, having different services each stored in different branches. The user service will communicate with the rating service and hotel service through the rest template and the feign client. I have also implemented resilience4j, in case of service is not available, it will show dummy data. Also, different databases are used in different services, both SQL and NoSQL. I have also created custom exceptions and used them in various parts, and a global exception handler to handle exceptions globally. spring cloud and eureka server is also used in my application.
