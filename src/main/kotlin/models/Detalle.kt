package models

class Detalle(producto: Producto, cantidad: Int, impuesto: Double = 0.18) {
    val producto: Producto //Referencia directa a una instancia de Producto-Cumpliendo con la relacion de asociación
    var cantidad: Int = cantidad
        get() = field
        set(value) {
            if (value >= 0) {
                field = value
            } else {
                println("Error, valor de cantidad invalida")
                field = 0
            }
        }
    val impuesto: Double

    init {
        this.producto = producto
        this.cantidad = cantidad
        this.impuesto = impuesto
    }

    fun calcularCostoConImpuesto(): Double {
        return cantidad * producto.precioUnitario * (1 + impuesto)
    }
}