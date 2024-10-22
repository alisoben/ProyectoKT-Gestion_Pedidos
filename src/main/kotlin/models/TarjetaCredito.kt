package models

import enumerations.Tarjeta
import java.time.LocalDate

class PagoTarjetaCredito(
    montoPagado: Double, // Se pasa al constructor de la clase base
    fechaPagado: LocalDate, // Se pasa al constructor de la clase base
    var numeroTarjeta: String, // Número de la tarjeta de crédito
    var fechaCaducidad: LocalDate, // Fecha de caducidad de la tarjeta
    var tipoTarjeta: Tarjeta // Tipo de tarjeta (VISA, MASTERCARD, etc.)
) : Pago(montoPagado, fechaPagado) {
    // Métodos específicos para PagoTarjetaCredito serán agregados más adelante
}
