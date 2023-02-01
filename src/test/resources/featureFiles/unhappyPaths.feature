#language: en

@TESTS
  Feature:unHappy Paths Booking

  @UnTEST1
  Scenario Outline: Obtener token con datos incorrectos
    Given Obtener llamada a "<url>" con "<user>" y "<pass>"
    When se valida el codigo de respuesta"<responseMessage>"
    Then se obtiene mensaje de datos incorrectos
    Examples:
      | url   | responseMessage | user  | pass        |
      | /auth | 200             | admin | password12  |

  @UnTEST2
  Scenario Outline: Obtener IDs
    Given Obtener llamada a "<url>"
    When se valida el codigo de respuesta"<responseMessage>"
    Then no se obtiene respuesta de los IDs
    Examples:
      | url       | responseMessage |
      | /bookiing | 404             |

