package models

import java.time.LocalDate

class PagoEfectivo(
    montoPagado: Double,
    fechaPagado: LocalDate,
    numeroCuotas: Int,
    var moneda: String
) : Pago(montoPagado, fechaPagado, numeroCuotas) {
    override fun procesarPago(): Boolean {

        if(moneda == "USD"){
            println("Monto a pagar: $montoPagado USD")
            println("Procesando pago en efectivo en $moneda por un monto de $montoPagado USD el día $fechaPagado")
        }
        else{
            println("Monto a pagar: $montoPagado USD. Convirtiendo a $moneda")
            println("Procesando pago en efectivo en $moneda por un monto de $montoPagado USD el día $fechaPagado")
            if(moneda == "PEN"){
                println("Monto pagado: $montoSoles $moneda")
            }
            if(moneda == "EUR"){
                println("Monto pagado: $montoEuros $moneda")
            }
        }
        return true
    }
}
