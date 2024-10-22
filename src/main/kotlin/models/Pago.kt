package models
import java.time.LocalDate

open class Pago(
    var montoPagado: Double,
    var fechaPagado: LocalDate= LocalDate.now()
) {
    open fun procesarPago(): Boolean {
        println("Procesando pago de $montoPagado el día $fechaPagado")
        return true
    }
}