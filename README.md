# JUGLottery Application

**Overview:**
The JUGLottery application allows for the creation of lotteries accompanied by vouchers. Participants can register for these lotteries by scanning a QR code and completing a provided form. After a specified duration, the lottery ends, and the winner is sent an email containing the voucher.

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
