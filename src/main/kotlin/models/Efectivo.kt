package models

import java.time.LocalDate

class PagoEfectivo(
    montoPagado: Double, // Se pasa al constructor de la clase base
    fechaPagado: LocalDate, // Se pasa al constructor de la clase base
    var moneda: String // Moneda utilizada en el pago (atributo específico de esta clase)
) : Pago(montoPagado, fechaPagado) {
    // Métodos específicos para PagoEfectivo serán agregados más adelante
}
