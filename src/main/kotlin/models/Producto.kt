package models

class Producto(
    val idProducto: Int,
    var nombreProducto: String,
    var precioUnitario: Double,
    var stock: Int
) {
    // Lista de productos estática dentro de la clase
    companion object {
        var listaProductos = listOf(
            Producto(1, "Laptop", 899.99, 10),
            Producto(2, "Teléfono", 499.99, 15),
            Producto(3, "Tableta", 299.99, 20),
            Producto(4, "Auriculares", 99.99, 50),
            Producto(5, "Reloj", 199.99, 30)
        )
    }
}