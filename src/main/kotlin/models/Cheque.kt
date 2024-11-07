package models
import java.time.LocalDate

class PagoCheque(
    montoPagado: Double,
    fechaPagado: LocalDate,
    numeroCuotas: Int,
    var nombreTitular: String,
    var numeroCheque: String,
    var entidadBancaria: String
) : Pago(montoPagado, fechaPagado, numeroCuotas) {
    override fun procesarPago(): Boolean {
        println("Procesando pago con cheque N°$numeroCheque de $nombreTitular mediante $entidadBancaria por un monto de $montoPagado el día $fechaPagado")
        return true
    }
}