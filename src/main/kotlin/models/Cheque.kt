package models
import java.time.LocalDate

class PagoCheque(
    montoPagado: Double, // Atributos que se pasan a la clase base
    fechaPagado: LocalDate, // Atributos que se pasan a la clase base
    var nombreTitular: String, // Atributos específicos de PagoCheque
    var numeroCheque: String, // Atributos específicos de PagoCheque
    var entidadBancaria: String // Atributos específicos de PagoCheque
) : Pago(montoPagado, fechaPagado) {
    override fun procesarPago(): Boolean {
        //println("Procesando pago con cheque de $montoPagado el día $fechaPagado")
        println("Procesando pago con cheque N°$numeroCheque de $nombreTitular mediante $entidadBancaria por un monto de $montoPagado el día $fechaPagado")
        return true
    }
}