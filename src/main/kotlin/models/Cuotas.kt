import enumerations.Estado
import models.Pago
import models.Pedido
import java.time.LocalDate

class PagoCuotas(
    private val montoTotal: Double,
    private val fecha: LocalDate,
    private val numeroCuotas: Int
) : Pago(montoTotal, fecha) {

    private val montoCuota: Double = montoTotal / numeroCuotas
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
    fun pagarCuota(numeroCuota: Int, pedido: Pedido) {
        if (numeroCuota in 1..numeroCuotas && !cuotasPagadas[numeroCuota - 1]) {
            cuotasPagadas[numeroCuota - 1] = true
            println("La cuota $numeroCuota ha sido pagada con éxito.")

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
