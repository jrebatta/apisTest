@TESTS
Feature: Dummy Rest API Functionality Scenarios

  @TEST1
  Scenario Outline: Obtener token
    Given Obtener llamada a "<url>" con "<user>" y "<pass>"
    When se valida el codigo de respuesta"<responseMessage>"
    Then se obtiene el token
    Examples:
      | url   | responseMessage | user  | pass        |
      | /auth | 200             | admin | password123 |

  @TEST2
  Scenario Outline:  Verify Code
    Given Get Call to "<url>"
    Then Response  is array total "<total>"
    Examples:
      | url      | total |
      | /student | 18    |
