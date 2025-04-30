Feature: Pedido de bebida desde frontend

  Como cliente
  Quiero pedir una bebida específica desde el frontend
  Para verificar si fue aceptada correctamente por el sistema

  Scenario: Pedido exitoso de bebida disponible
    Given la bebida "Latte" de tamaño "M" está disponible
    When el cliente realiza un pedido con nombre "Latte" y tamaño "M"
    Then el sistema debe responder con estado 201 y un pedido creado

  Scenario: Pedido fallido de bebida no disponible
    Given la bebida "Inexistente" de tamaño "XL" no está disponible
    When el cliente realiza un pedido con nombre "Inexistente" y tamaño "XL"
    Then el sistema debe responder con estado 400 o 404
