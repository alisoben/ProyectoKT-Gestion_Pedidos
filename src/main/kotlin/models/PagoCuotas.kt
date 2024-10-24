package models

import java.time.LocalDate

class PagoCuotas(
    montoPagado: Double,
    fechaPagado: LocalDate,
    var numeroCuotas: Int,
    var montoPorCuota: Double
) : Pago(montoPagado, fechaPagado) {
    var cuotasPagadas: Int = 0

    override fun procesarPago(): Boolean {
        if (cuotasPagadas < numeroCuotas) {
            cuotasPagadas++
            println("Procesando pago de cuota $cuotasPagadas de $montoPorCuota el día $fechaPagado")
            return true
        } else {
            println("Ya se han pagado todas las cuotas")
            return false
        }
    }

    fun totalCuotasPagadas(): Boolean {
        return cuotasPagadas >= numeroCuotas
    }
}