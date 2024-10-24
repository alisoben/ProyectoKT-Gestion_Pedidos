package models

import java.time.LocalDate

class PagoEfectivo(
    montoPagado: Double, // Se pasa al constructor de la clase base
    fechaPagado: LocalDate, // Se pasa al constructor de la clase base
    var moneda: String // Moneda utilizada en el pago (atributo específico de esta clase)
) : Pago(montoPagado, fechaPagado) {
    override fun procesarPago(): Boolean {
        //println("Procesando pago en efectivo de $montoPagado el día $fechaPagado")
        println("Procesando pago en efectivo en $moneda por un monto de $montoPagado el día $fechaPagado")
        return true
    }
}
