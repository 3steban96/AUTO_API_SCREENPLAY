Feature: API Integration - CheckIn QR
  Como consumidor de la API de Reservas
  Quiero enviar el token QR correcto al endpoint /my-reservations/{id}/checkin
  Para confirmar la asistencia de forma segura y automatizada

  Background:
    Given un token de autorización válido para "admin@sofka.com.co"
    And una reserva creada para "Sala Zeus" en estado PENDING con ID "123"

  @api @positive
  Scenario: Check-in exitoso con QR válido dentro del grace period
    When el cliente envía una petición POST a "/bookings/reservations/123/checkin" con el "qrToken" válido
    Then el status code de la respuesta debería ser 200
    And el body de la respuesta debe contener:
      | campo         | valor                          |
      | message       | Check-in realizado exitosamente |
      | status        | CHECKED_IN                     |
      | reservationId | 123                            |

  @api @negative
  Scenario: Check-in fallido con firma QR adulterada
    When el cliente envía una petición POST a "/bookings/reservations/123/checkin" con un token adulterado
    Then el status code de la respuesta debería ser 400
    And el body de la respuesta de error debe contener:
      | campo   | valor                                            |
      | error   | INVALID_QR_TOKEN                                 |
      | message | QR invalido o no corresponde a tu reserva actual |

  @api @negative
  Scenario: Check-in fallido fuera del grace period (6 minutos)
    Given la hora de la reserva expiró hace "6" minutos
    When el cliente envía una petición POST a "/bookings/reservations/123/checkin" con el "qrToken" válido
    Then el status code de la respuesta debería ser 422
    And el body de la respuesta de error debe contener:
      | campo   | valor                                     |
      | error   | GRACE_PERIOD_EXPIRED                      |
      | message | El tiempo para hacer check-in ha expirado |
