package models

class Detalle(
    val idProducto: Int, // ID del producto
    var cantidad: Int,    // Cantidad del producto
    val impuesto: Double = 0.18 // Porcentaje del impuesto (por ejemplo, 0.18 para 18%)
) {
    fun calcularCostoConImpuesto(): Double {
        val producto = Producto.listaProductos.find { it.idProducto == idProducto }
        val costoSinImpuesto = (producto?.precioUnitario ?: 0.0) * cantidad
        val costoConImpuesto = costoSinImpuesto * (1 + impuesto)
        return costoConImpuesto
    }
}