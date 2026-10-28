\# Evenza 🎉



Evenza is a microservices-based Event Management System that allows users to browse events, register for them, make payments, and receive notifications — all through a clean, modular architecture built with Spring Boot and a lightweight HTML/CSS/JS frontend.



\---



\## Overview



Evenza is designed to simulate a real-world event management platform, split into independent microservices. Each service is responsible for a single business capability, making the system scalable, maintainable, and easy to extend.



\---



\## Architecture



Evenza follows a microservices architecture with 4 independent Spring Boot services, each running on its own port:



| Microservice | Port | Responsibility |

|---|---|---|

| event-service | 8081 | Create, update, and manage events (details, schedules, venues, etc.) |

| registration-service | 8082 | Handle attendee registrations for events |

| payment-service | 8083 | Process and manage payments for registrations |

| notification-service | 8084 | Send notifications/alerts to users (email/SMS/in-app) |



A separate frontend (evenza-frontend) built with HTML, CSS, and JavaScript consumes these backend APIs to provide the user interface.



\---



\## Tech Stack



Backend: Java, Spring Boot, MySQL

Frontend: HTML, CSS, JavaScript

Architecture Style: Microservices



\---



\## Project Structure



Evenza/

\- event-service/            (Event management microservice - Port 8081)

\- registration-service/     (Registration microservice - Port 8082)

\- payment-service/          (Payment microservice - Port 8083)

\- notification-service/     (Notification microservice - Port 8084)

\- evenza-frontend/          (HTML/CSS/JS frontend)

\- README.md



\---



\## Getting Started



Prerequisites: Java 17+, Maven, MySQL Server, a web browser



Setup:

1\. Clone the repository: git clone https://github.com/harrywithcodes/Evenza.git

2\. Configure the database for each microservice by creating your own application.properties inside each service's src/main/resources folder (these are gitignored for security).

3\. Run each microservice separately using: mvn spring-boot:run

4\. Open the frontend by navigating to evenza-frontend/ and opening index.html in your browser.



\---



\## Service Ports Summary



Event Service: http://localhost:8081

Registration Service: http://localhost:8082

Payment Service: http://localhost:8083

Notification Service: http://localhost:8084



\---



\## Security Note



application.properties files (which contain DB credentials and other secrets) are excluded from version control via .gitignore. Make sure to configure your own local copies before running the services.




\---



\## Author



harrywithcodes

