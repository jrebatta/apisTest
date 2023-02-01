#language: en

@TESTS
  Feature:Dummy Rest API Functionality Scenarios

  @TEST1
  Scenario Outline: Obtener token
    Given Obtener llamada a "<url>" con "<user>" y "<pass>"
    When se valida el codigo de respuesta"<responseMessage>"
    Then se obtiene el token
    Examples:
      | url   | responseMessage | user  | pass        |
      | /auth | 200             | admin | password123 |

  @TEST2
  Scenario Outline: Obtener IDs
    Given Obtener llamada a "<url>"
    When se valida el codigo de respuesta"<responseMessage>"
    Then se obtiene respuesta de los IDs
    Examples:
      | url      | responseMessage |
      | /booking | 200             |

  @TEST3
  Scenario Outline: Crear Booking
    Given crear bookin "<url>" con datos "<firstname>" "<lastname>" "<totalprice>" "<depositpaid>" "<checkin>" "<checkout>" "<additionalneeds>"
    When se valida el codigo de respuesta"<responseMessage>"
    Then se obtiene el id del booking creado y se guarda el nombre y apellido del booking creado
    Examples:
      | url      | responseMessage | firstname | lastname | totalprice | depositpaid | checkin     | checkout   | additionalneeds |
      | /booking | 200             | Jim       | Brown    | 111        | true        | 2023-01-02  | 2023-01-05 | Breakfast       |

    @TEST4
    Scenario Outline: Obtener booking por ID
      Given Obtener llamada a "<url>" con id
      When se valida el codigo de respuesta"<responseMessage>"
      Then se obtiene los datos del id y se almacena el nombre y el apellido
      Examples:
        | url      | responseMessage |
        | /booking | 200             |

    @TEST5
    Scenario Outline: Obtener id booking por nombre y apellido
      Given Obtener id booking "<url>" por nombre y apellido
      When se valida el codigo de respuesta"<responseMessage>"
      Then se obtiene el id y se valida con el obtenido previamente
      Examples:
        | url      | responseMessage |
        | /booking | 200             |

    @TEST5
    Scenario Outline: Obtener id booking por nombre y apellido
      Given Obtener id booking "<url>" por nombre y apellido
      When se valida el codigo de respuesta"<responseMessage>"
      Then se obtiene el id y se valida con el obtenido previamente
      Examples:
        | url      | responseMessage |
        | /booking | 200             |