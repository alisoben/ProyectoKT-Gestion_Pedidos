package models

class Producto(idProducto: Int, nombreProducto: String, precioUnitario: Double, stock: Int ){
    var idProducto: Int = 0 //val
    var nombreProducto: String = ""
    var precioUnitario: Double = 0.0
    var stock: Int = 0

    init{
        this.idProducto = idProducto
        this.nombreProducto = nombreProducto
        this.precioUnitario = precioUnitario
        this.stock = stock
    }

    companion object {
        fun ObtenerProducto(idProducto: Int, nombreProducto: String, precioUnitario: Double, stock: Int,
                            listaProductos: List<Producto>): Boolean {
            return listaProductos.any { it.nombreProducto == nombreProducto && it.idProducto == idProducto }
        }
    }
}