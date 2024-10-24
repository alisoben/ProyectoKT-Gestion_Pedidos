package models
import java.time.LocalDate

class PagoCheque(
    montoPagado: Double, // Atributos que se pasan a la clase base
    fechaPagado: LocalDate, // Atributos que se pasan a la clase base
    var nombreTitular: String, // Atributos específicos de PagoCheque
    var entidadBancaria: String // Atributos específicos de PagoCheque
) : Pago(montoPagado, fechaPagado) {
    // Métodos específicos de PagoCheque serán agregados más adelante
    override fun procesarPago(): Boolean {
        println("Procesando pago con cheque de $nombreTitular en el banco $entidadBancaria por un monto de $montoPagado")
        return true
    }
}