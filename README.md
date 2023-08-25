# JUGLottery Application

**Overview:**
The JUGLottery application allows for the creation of lotteries accompanied by vouchers. Participants can register for these lotteries by scanning a QR code and completing a provided form. After a specified duration, the lottery ends, and the winner is sent an email containing the voucher.

**Screenshots:**
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


**Features:**
1. **Super User Capabilities:**
    - Authentication:
        - Allows the Super User to log in and access administrative functionalities.
    - Lottery Management:
        - Creation: Set up a new lottery which can later be edited, deleted, or initiated.
        - Voucher Management: Ability to add, delete, or edit vouchers. These vouchers can then be attached to the lottery.
2. **Participant Interaction:**
    - Lottery Initiation:
        - Generates a QR code upon lottery launch.
        - Participants can scan the code QR to access a form where they input their name and email.
    - Lottery Conclusion:
        - Concludes 10 minutes after initiation.
        - A winner is randomly selected and subsequently receives an email containing the voucher.


**Technologies:**
1. Java 17
2. Spring Boot 3.1.2
3. Spring Security 6.1
4. MapStruct
5. Thymeleaf
6. ZXing - a library for generating QR codes
