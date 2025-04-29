Feature: Pedido de bebida

  Scenario: Pedido exitoso de bebida disponible
    Given la bebida "Latte" de tamaño "M" está disponible
    When el cliente realiza un pedido con nombre "Latte" y tamaño "M"
    Then el sistema debe responder con estado 201 y un pedido creado
