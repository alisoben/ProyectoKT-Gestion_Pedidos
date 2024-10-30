package models
import java.time.LocalDate

open class Pago(montoPagado: Double, fechaPagado: LocalDate) {
    var montoPagado: Double = montoPagado
        get() = field
        set(value) {
            if (value >= 0) {
                field = value
            } else {
                println("Error en la varible montoPagado, dato invalido")
                field = 0.0
            }
        }
    var fechaPagado: LocalDate=LocalDate.now()

    init{
        this.montoPagado = montoPagado
        this.fechaPagado = fechaPagado
    }

    open fun procesarPago(): Boolean {
        println("Procesando pago de $montoPagado el día $fechaPagado")
        return true
    }
}