package models

import java.time.LocalDate

class PagoEfectivo(
    montoPagado: Double,
    fechaPagado: LocalDate,
    numeroCuotas: Int,
    var moneda: String
) : Pago(montoPagado, fechaPagado, numeroCuotas) {
    override fun procesarPago(): Boolean {
        println("Procesando pago en efectivo en $moneda por un monto de $montoPagado el día $fechaPagado")
        return true
    }
}
