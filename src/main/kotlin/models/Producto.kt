package models

class Producto (idProducto: Int, nombreProducto: String, precioUnitario: Double, stock: Int ){
    var idProducto: Int = 0
    var nombreProducto: String = ""
    var precioUnitario: Double = 0.0
    var stock: Int = stock
        get() = field
        set(value) {
            if (value >= 0) {
                field = value
            } else {
                println("Error en la varible stock, dato invalido")
                field = 0
            }
        }

    init{
        this.idProducto = idProducto
        this.nombreProducto = nombreProducto
        this.precioUnitario = precioUnitario
        this.stock = stock
    }

    companion object {
        fun mostrarProducto(listaProductos: List<Producto>) {
            println("Lista de productos disponibles:")
            listaProductos.forEach { producto ->
                println("ID: ${producto.idProducto}, Nombre: ${producto.nombreProducto}, Precio: ${producto.precioUnitario}, Stock: ${producto.stock}")
            }
        }

        fun buscarProducto(listaProductos: List<Producto>, productoId: Int): Producto? {
            return listaProductos.find { it.idProducto == productoId }
        }
    }
}