package models

import enumerations.Estado
import java.time.LocalDate

open class Pago(montoPagado: Double, fechaPagado: LocalDate, numeroCuotas: Int = 1) {
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
    var numeroCuotas: Int = numeroCuotas
        get() = field
        set(value) {
            if (value >= 0) {
                field = value
            } else {
                println("Error en la varible Cuotas, dato invalido")
                field = 1
            }
        }
    var fechaPagado: LocalDate = LocalDate.now()

    init {
        this.montoPagado = montoPagado
        this.fechaPagado = fechaPagado
        this.numeroCuotas = numeroCuotas
    }

    open fun procesarPago(): Boolean {
        println("Procesando pago de $montoPagado el día $fechaPagado")
        return true
    }

    private val montoCuota: Double = montoPagado / numeroCuotas
    private val cuotasPagadas: MutableList<Boolean> = MutableList(numeroCuotas) { false }

    // Método para mostrar el estado de todas las cuotas
    fun mostrarCuotas() {
        println("Estado de las cuotas:")
        cuotasPagadas.forEachIndexed { index, pagada ->
            println("Cuota ${index + 1}: ${if (pagada) "Pagada" else "Pendiente"} - Monto: $montoCuota")
        }
    }

    // Método para obtener el monto de cada cuota
    fun getMontoCuota(): Double {
        return montoCuota
    }

    // Método para pagar una cuota específica
    open fun pagarCuota(numeroCuota: Int, pedido: Pedido) {
        if (numeroCuota in 1..numeroCuotas && !cuotasPagadas[numeroCuota - 1]) {
            cuotasPagadas[numeroCuota - 1] = true
            println("La cuota $numeroCuota ha sido pagada con éxito.")

            // Reducir el monto de la cuota del saldo restante del pedido
            pedido.costeTotal -= montoCuota

            // Verifica si todas las cuotas han sido pagadas
            if (cuotasPagadas.all { it }) {
                pedido.estado = Estado.PAGADO
                println("¡Todas las cuotas están pagadas! El estado del pedido ahora es: ${pedido.estado}")
            } else {
                pedido.estado = Estado.PROCESANDO
                println("El estado del pedido es ahora: ${pedido.estado}")
            }
        } else {
            println("Error: La cuota ya ha sido pagada o no existe.")
        }
    }
}