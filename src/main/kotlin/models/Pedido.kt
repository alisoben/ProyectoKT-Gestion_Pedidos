package models

class Pedido (
    val fechaCreacion: Int,
    var estado: Int,
    val costeTotal: Int,
    val detalles: List<Detalle>,
    val pagos: List<Pago>
)