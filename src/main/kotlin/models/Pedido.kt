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
        if (detalle.producto.stock >= detalle.cantidad) {
            detalles.add(detalle)
            detalle.producto.stock -= detalle.cantidad
            costeTotal = calcularCostoTotal() // Actualiza el coste total al agregar un detalle
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
        return detalles.sumOf { it.calcularCostoConImpuesto() } // Sumar todos los costos de los detalles
    }

    fun calcularSubtotal(): Double {
        return detalles.sumOf { it.producto.precioUnitario * it.cantidad } // Sumar todos los costos de los detalles
    }

    fun calcularImpuesto(): Double {
        return detalles.sumOf { it.producto.precioUnitario * it.cantidad * it.impuesto } // Sumar todos los costos de los detalles
    }

    fun mostrarResumen() {
        println("Fecha de creación: $fechaCreacion")
        println("Estado: $estado")
        println("Subtotal: ${calcularSubtotal()}")
        println("Impuesto (${detalles[0].impuesto * 100}%): ${calcularImpuesto()}")
        println("Coste total: $costeTotal")
        println("Detalles del pedido:")
        detalles.forEach { detalle ->
            println("Producto: ${detalle.producto.nombreProducto}, Cantidad: ${detalle.cantidad}, Precio Unitario: ${detalle.producto.precioUnitario}, Total: ${detalle.calcularCostoConImpuesto()}")
        }
    }
}