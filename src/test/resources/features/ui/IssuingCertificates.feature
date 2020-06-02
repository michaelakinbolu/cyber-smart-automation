@ui
Feature: Issuing Certificates

  Scenario: Test Filling in responses (leave some answers are empty) on CE Questionnaire

    Given I am signed in to the CyberSmart Web App as a company user

    And I am on the dashboard page for an Organisation

    And I fill the Cyber Essentials Questionnaire

    And I leave a few answer fields empty

    When I click the Submit answers button

    Then The Empty answer are highlighted with red color with message, Answers are not submitted


  Scenario: Test the "Finish later" button on CE Questionnaire

    Given I am signed in to the CyberSmart Web App as a company user

    And I am on the dashboard page for an Organisation

    And I fill the Cyber Essentials Questionnaire

    And I leave a few answer fields empty

    When I click the Finish later button

    Then All entered answers are saved and I am redirected to the dashboard page


  Scenario: Test Filling in responses (all answers are filled out) on CE Questionnaire

    Given I am signed in to the CyberSmart Web App as a company user

    And I am on the dashboard page for an Organisation

    And I fill the Cyber Essentials Questionnaire

    When I click the Submit answers button

    Then All entered answers are saved and I am redirected to the Declaration page


  Scenario: Test Signing the declaration by user with empty fields

    Given I am signed in to the CyberSmart Web App as a company user

    And I am on the dashboard page for an Organisation

    And I fill the Cyber Essentials Questionnaire

    And I click the Submit answers button

    And All entered answers are saved and I am redirected to the Declaration page

    And I click the I will sign button

    When I click the Submit button

    Then Empty pages are highlighted with red color. Declaration is not signed.


  Scenario: Test the "Clear" button

    Given I am signed in to the CyberSmart Web App as a company user

    And I am on the dashboard page for an Organisation

    And I fill the Cyber Essentials Questionnaire

    And I click the Submit answers button

    And All entered answers are saved and I am redirected to the Declaration page

    And I click the I will sign button

    And I enter my job title

    And I draw the signature with a mouse

    When I click the clear button

    Then Signature field is cleared


  Scenario: Test Signing the declaration by user with all valid fields

    Given I am signed in to the CyberSmart Web App as a company user

    And I am on the dashboard page for an Organisation

    And I fill the Cyber Essentials Questionnaire

    And I click the Submit answers button

    And All entered answers are saved and I am redirected to the Declaration page

    When I click the I will sign button

    And I enter my job title

    And I draw the signature with a mouse

    And I click the Submit button

    Then Declaration is signed. The "You have successfully signed your companies Cyber Essentials 2019 declaration." message is displayed


  Scenario: Test Signing the declaration by external via email

    Given I am signed in to the CyberSmart Web App as a company user

    And I am on the dashboard page for an Organisation

    And I fill the Cyber Essentials Questionnaire

    And I click the Submit answers button

    And All entered answers are saved and I am redirected to the Declaration page

    And I click the Email for signing button

    And I Fill all required fields with valid information - "Name", "Email" and "Title"

    And Click the ""Send"" button (Successful message is displayed)

    And Open the mailbox of the external user

    And Open the ""Declaration signing required"" email

    And Click the ""Review and approve submission"" button

    And Check all fields

    And I draw the signature with a mouse

    And I click the Submit button

    Then Declaration is signed. The "You have successfully signed your companies Cyber Essentials 2019 declaration." message is displayed


  Scenario: Test Requesting the clarification by Partner

    Given I am signed in to the CyberSmart Web App as a company user

    And CE Survey was filled out and sent

    And Partner user is logged into another browser

    And CE Survey is on the ""Ready for assessment"" column

    And Open the ""Assessment dashboard"" as Partner

    And I Click the survey

    And Open the required list

    And Choose the required question and click the ""Add"" button

    And Enter the clarification request and click the ""Save"" button

    And Scroll the page down and click the ""Send"" button

    And Open the Dashboard as Company user

    And Click the ""Required attention"" link in the dashboard

    And Make changes according to the request from the partner

    And Click ""Submit Answers"" button

    And Open the ""CertOS dashboard"" as Partner

    And Click the survey

    And Open the list with answer

    When Click the ""Accept answer"" button"

    Then The "Answer accepted" message is displayed.


  Scenario: Test Issuing the CE Certificate

    Given I am signed in to the CyberSmart Web App as a Partner user

    And CE Survey is on the ""Ready for assessment"" column
    And Company user is logged into another browser

    And Click the survey

    And Click the ""Verify"" button

    And The 'Verification Successful. We've checked the number of non-conformities against the allowed certificate threshold and it's acceptable. You can issue the certificate' message is displayed

    And Click the ""Preview certificate"" button

    And Click the ""OK"" button on the ""Generating preview"" popup window

    And Pay attention that preview.pdf file is downloaded

    And Click the ""Issue certificate"" button

    And Click the ""OK"" button on the ""Generating certificate"" popup window

    And Open downloaded certificate.pdf file - it must contain certificate and survey

    And Check the status of the ""Cyber Essentials"" as Company user - it must be ""Certified""

    And As Company user click the ""Cyber Essentials"" tab on the company dashboard -

    Then the ""Congratulations, your organisation has successfully achieved Cyber Essentials certification!"" inscription

    And badge is displayed on the page. ""Download Certificate"", ""Download Report"", ""Download Badges"" and ""View answers"" should be available to the user."


  Scenario: Test Filling in responses (leave some answers are empty) on IASME & GDPR Questionnaire

    Given I am signed in to the CyberSmart Web App as a company user

    And I am on the dashboard page for an Organisation

    And I have a Cyber Essentials Certificate

    And I Click the ""IASME & GDPR"" icon in the top menu

    And I Click the ""Start Questionnaiare"" button

    And I Fill all answers with valid information

    And I leave a few answer fields empty

    When I click the Submit answers button

    Then The Empty answer should be highlighted with red color and Answers are not submitted


  Scenario: Test the "Finish later" button on IASME & GDPR Questionnaire

    Given I am signed in to the CyberSmart Web App as a company user

    And I am on the dashboard page for an Organisation

    And I have a Cyber Essentials Certificate

    And I Click the ""IASME & GDPR"" icon in the top menu

    And I Click the ""Start Questionnaiare"" button

    And I Fill all answers with valid information

    And I leave a few answer fields empty

    When I click the Finish later button

    Then All entered answers are saved and I am redirected to the dashboard page


  Scenario: Test Filling in responses (all answers are filled out) on IASME & GDPR Questionnaire

    Given I am signed in to the CyberSmart Web App as a company user

    And I am on the dashboard page for an Organisation

    And I have a Cyber Essentials Certificate

    And I Click the ""IASME & GDPR"" icon in the top menu

    And I Click the ""Start Questionnaiare"" button

    And I Fill all answers with valid information

    When I click the Submit answers button

    Then All entered answers are saved and I am redirected to the Declaration page