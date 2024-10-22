package models

import java.time.LocalDate
import enumerations.Estado

class Pedido(
    var fechaCreacion: LocalDate = LocalDate.now(),
    var estado: Estado = Estado.PENDIENTE,
    var costeTotal: Double = 0.0,
    var detalles: MutableList<Detalle> = mutableListOf(),
    var pagos: MutableList<Pago> = mutableListOf()
) {
    fun agregarDetalle(detalle: Detalle) {
        detalles.add(detalle)
        costeTotal = calcularCostoTotal() // Actualiza el coste total al agregar un detalle
    }

    fun realizarPago(pago: Pago) {
        pagos.add(pago)
        estado = Estado.PAGADO // Cambiar estado a PAGADO
    }

    fun calcularCostoTotal(): Double {
        return detalles.sumOf { it.calcularCostoConImpuesto() } // Sumar todos los costos de los detalles
    }
}