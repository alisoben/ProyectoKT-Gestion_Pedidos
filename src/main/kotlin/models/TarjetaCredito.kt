package models

import enumerations.Tarjeta
import java.time.LocalDate

class PagoTarjetaCredito(
    montoPagado: Double,
    fechaPagado: LocalDate,
    numeroCuotas: Int,
    var numeroTarjeta: String,
    var fechaCaducidad: LocalDate,
    var tipoTarjeta: Tarjeta
) : Pago(montoPagado, fechaPagado, numeroCuotas) {
    override fun procesarPago(): Boolean {
        println("Procesando pago con tarjeta $tipoTarjeta con número $numeroTarjeta por un monto de $montoPagado USD")
        return true
    }
}
