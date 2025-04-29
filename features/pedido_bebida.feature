Feature: Pedido de bebida desde frontend

  Como cliente
  Quiero pedir una bebida específica desde el frontend
  Para verificar si fue aceptada correctamente por el sistema

  Scenario: Pedido exitoso de bebida disponible
    Given el menú está vacío
    When agrego la bebida "Latte" de tamaño "M" con precio 3.5
    Then el sistema debe devolver un mensaje de éxito

  Scenario: Pedido fallido por bebida sin nombre
    Given el menú está vacío
    When intento agregar una bebida sin nombre con tamaño "M" y precio 3.5
    Then el sistema debe responder con error 400

