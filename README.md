# JUGLottery Application

Lottery-app is a web application that enables you to create lotteries that are integrated with the **Random.org API**. This API provides **true randomness that comes from atmospheric noise**, ensuring that the lottery results are fair and unbiased. The application has implemented Spring Security 6.1 with role-based authentication, which means that a superuser can create and manage accounts of users who can create lotteries. The application has also configured JavaMailSender, which is used to reset passwords and inform users about their wins. The frontend of the application is created using Thymeleaf, a modern server-side Java template engine that allows for easy development of dynamic web applications.

# Table of Contents
  - [Technologies](#technologies)
  - [Screenshots](#screenshots)
  - [How to Run the Application](#how-to-run-the-application)


## Technologies:
1. Java 17
2. Spring Boot 3.1.2
3. Spring Security 6.1
4. Spring Events
5. External API integration
6. JUunit unit tests and Parameterized tests
7. Thymeleaf, HTML, CSS, JavaScript
8. Liquibase
9. Hibernate
10. MapStruct
11. ZXing - a library for generating QR codes

## Screenshots:
![Zrzut ekranu (2)](https://github.com/darekszyper/lottery-app/assets/114878453/c8aca382-7bcf-448b-8914-1f2aed39618b)
![Zrzut ekranu (2)](https://github.com/darekszyper/lottery-app/assets/114878453/c8aca382-7bcf-448b-8914-1f2aed39618b)
![Zrzut ekranu (7)](https://github.com/darekszyper/lottery-app/assets/114878453/e17fcdf6-ea58-42af-96f6-ac852a8aa1ec)
![Zrzut ekranu (10)](https://github.com/darekszyper/lottery-app/assets/114878453/0c87a998-585e-40b0-b5f7-0091c373f33e)
![Zrzut ekranu (12)](https://github.com/darekszyper/lottery-app/assets/114878453/2aeef13d-d1c0-483d-81a2-539e7e4bb9ee)
![Zrzut ekranu (8)](https://github.com/darekszyper/lottery-app/assets/114878453/c24eb34d-517d-420f-94cc-d51df4ac880c)
![Zrzut ekranu (9)](https://github.com/darekszyper/lottery-app/assets/114878453/c9bf85bc-1b12-4165-9254-c0a94dc5e61e)
![Zrzut ekranu (14)](https://github.com/darekszyper/lottery-app/assets/114878453/ad465038-fd4a-499e-8938-19e5b229c7a1)
![Zrzut ekranu (6)](https://github.com/darekszyper/lottery-app/assets/114878453/c5fe6e96-f399-485e-b371-c2402c306a08)
![Zrzut ekranu (15)](https://github.com/darekszyper/lottery-app/assets/114878453/1c2e20d3-3b08-40fe-a4e7-782d3e3e2476)
![Zrzut ekranu (16)](https://github.com/darekszyper/lottery-app/assets/114878453/474fcce4-81a4-4190-8f3d-f9331e3f667e)
![Zrzut ekranu (3)](https://github.com/darekszyper/lottery-app/assets/114878453/8095e901-a12f-4fc3-bde7-96559180c3cd)
![Zrzut ekranu (4)](https://github.com/darekszyper/lottery-app/assets/114878453/216ff7a7-070a-4eea-980d-6f343938f810)
![Zrzut ekranu (5)](https://github.com/darekszyper/lottery-app/assets/114878453/d15736a0-bdcf-415e-bf41-6cc8cbb5c7d4)

## How to run the application:
1. Clonde the repository to you IDE
2. Go to Random.org, and register to create your own API key and replace it in the code
3. Create Gmail account and generate App password and replace it in the code
4. Run the application and register onto SUPER_USER account login: admin@wp.pl password: password
5. Create new USER account with your e-mail address, and activate it with instruncions in you mailbox
6. Sign and enjoy trully random lotteries
