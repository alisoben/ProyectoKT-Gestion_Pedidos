package models

class Detalle(
    val producto: Producto, // Producto del detalle
    var cantidad: Int,    // Cantidad del producto
    val impuesto: Double = 0.18, // Porcentaje del impuesto (por ejemplo, 0.18 para 18%)
    //var precioUnitario: Double = 0.0 // Precio unitario del producto
) {
    fun calcularCostoConImpuesto(): Double {
        return cantidad * producto.precioUnitario * (1 + impuesto) // Calcular el costo total con impuesto
    }
}