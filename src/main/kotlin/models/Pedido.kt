package models

import java.time.LocalDate
import enumerations.Estado

class Pedido(
    var cliente: Cliente? = null, // Referencia un objeto de tipo cliente: Cada pedido está asociado con un cliente específico.
    var fechaCreacion: LocalDate = LocalDate.now(),
    var estado: Estado = Estado.PENDIENTE,
    var costeTotal: Double = 0.0,
    var detalles: MutableList<Detalle> = mutableListOf(), // Lista de Detalle como parte de la composición: los detalles pertenecen al pedido
    var pagos: MutableList<Pago> = mutableListOf()  // Lista de Pago, Composición: permite múltiples pagos para un pedido
) {
    fun agregarDetalle(detalle: Detalle) {
        if (detalle.producto.stock >= detalle.cantidad) {
            detalles.add(detalle)
            detalle.producto.stock -= detalle.cantidad
            costeTotal = calcularCostoTotal()
        } else {
            println("No hay suficiente stock para el producto ${detalle.producto.nombreProducto}")
            return
        }
    }

    fun realizarPago(pago: Pago): Boolean {
        if(pago.procesarPago()){
            pagos.add(pago)
            if(pagos.sumOf { it.montoPagado } >= costeTotal){
                estado = Estado.PAGADO
            }
            return true
        }
        return false
    }

    fun calcularCostoTotal(): Double {
        return detalles.sumOf { it.calcularCostoConImpuesto() }
    }

    fun calcularSubtotal(): Double {
        return detalles.sumOf { it.producto.precioUnitario * it.cantidad }
    }

    fun calcularImpuesto(): Double {
        return detalles.sumOf { it.producto.precioUnitario * it.cantidad * it.impuesto }
    }

    fun mostrarResumen() {
        println("Cliente: ${cliente?.nombre}, Telefono: ${cliente?.telefono}, Dirección: ${cliente?.direccion}")
        println("Fecha de creación: $fechaCreacion")
        println("Estado: $estado")
        println("Subtotal: ${calcularSubtotal()}")
        println("Impuesto (${detalles[0].impuesto * 100}%): ${calcularImpuesto()}")
        println("Coste total: $costeTotal")
        println("Detalles de la compra:")
        detalles.forEach { detalle ->
            println("Producto: ${detalle.producto.nombreProducto}, Cantidad: ${detalle.cantidad}, Precio Unitario: ${detalle.producto.precioUnitario}, Total: ${detalle.calcularCostoConImpuesto()}")
        }
    }

    fun mostrarDetallePedido(pedidos: List<Pedido>) {
        println("Lista de pedidos:")
        pedidos.forEachIndexed { index, pedido ->
            println("Pedido ${index + 1} : Estado: ${pedido.estado}, Coste Total: ${pedido.costeTotal}, Fecha de Pedido: ${pedido.fechaCreacion}")
        }
    }

    fun eliminarPedido() {
        detalles.clear()
        pagos.clear()
        println("El pedido ha sido eliminado exitosamente.")
    }
}