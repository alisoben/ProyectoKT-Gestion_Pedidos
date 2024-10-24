import java.time.LocalDate
import java.util.Scanner
import models.Cliente
import models.Pedido
import models.Detalle
import models.Producto
import models.Pago
import models.PagoCheque
import models.PagoTarjetaCredito
import models.PagoEfectivo
import enumerations.Tarjeta
import enumerations.Estado
import data.ListaClientes
import java.util.*


fun main() {
    val scanner = Scanner(System.`in`)
    var continuar = true
    val pedidos = mutableListOf<Pedido>()

    while (continuar) {
        var sesionIniciada = false
        println("Bienvenido a la gestión de pedidos")
        print("Ingrese su nombre:")
        val nombreInput = scanner.nextLine()
        if (nombreInput.lowercase() == "x") {
            continuar = false
        }
        print("Ingrese su código:")
        val codigoInput = scanner.nextLine()
        if (Cliente.ValidaCliente(nombreInput, codigoInput, ListaClientes)) {
            println("Inicio de sesión exitoso para el cliente: $nombreInput")
            sesionIniciada = true
        } else {
            println("Error: nombre o código incorrecto.")
        }
        while (sesionIniciada) {
            println("\n--- Menú ---")
            println("1. Realizar nuevo pedido")
            println("2. Pagar tus pedidos ")
            println("3. Estado de tus pedidos")
            println("4. Salir")
            println("Selecciona una opción:")

            val opcionMenu = try {
                scanner.nextInt()
            } catch (e: InputMismatchException) {
                println("Opción no válida, por favor ingresa un número.")
                scanner.nextLine() // Limpiar la entrada
                continue
            }

            when (opcionMenu) {
                1 -> {
                    val nuevoPedido = Pedido()
                    var agregarDetalle = true
                    println("Lista de productos disponibles:")

                    Producto.listaProductos.forEach {
                        val estadoStock = if (it.stock > 0) "${it.stock}" else "Agotado"
                        println("ID: ${it.idProducto}, Producto: ${it.nombreProducto}, Precio: ${it.precioUnitario}, Stock: $estadoStock")
                    }

                    while (agregarDetalle) {
                        print("Ingrese el ID del producto que desea comprar:")
                        val productoId = scanner.nextInt()
                        val producto = Producto.listaProductos.find { it.idProducto == productoId }

                        // Verificar si el producto existe y tiene stock disponible
                        if (producto != null) {
                            if (producto.stock > 0) {
                                print("Ingrese la cantidad:")

                                val cantidad = scanner.nextInt()

                                if (cantidad <= producto.stock) {
                                    val detalle = Detalle(producto.idProducto, cantidad)
                                    nuevoPedido.agregarDetalle(detalle)

                                    // Reducir el stock del producto
                                    producto.stock -= cantidad
                                    println("Producto ${producto.nombreProducto} agregado al pedido.")
                                } else {
                                    println("No hay suficiente stock para la cantidad solicitada.")
                                }
                            } else {
                                println("El producto está agotado.")
                            }
                        } else {
                            println("Producto no encontrado.")
                        }

                        println("¿Desea agregar otro pedido? (s/n):")
                        val respuesta = scanner.next()
                        agregarDetalle = (respuesta.lowercase() == "s")
                    }

                    pedidos.add(nuevoPedido) // Agregar pedido a la lista
                    println("Pedido creado con éxito. Coste total: ${nuevoPedido.costeTotal}")
                }

                2 -> {
                    // Pagar un pedido
                    println("Seleccione el índice del pedido a pagar (0 a ${pedidos.size - 1}):")
                    val indicePedido = scanner.nextInt()
                    if (indicePedido in pedidos.indices) {
                        val pedido = pedidos[indicePedido]

                        // Elegir método de pago
                        println("Seleccione el método de pago:")
                        println("1. Cheque")
                        println("2. Tarjeta de Crédito")
                        println("3. Efectivo")
                        when (scanner.nextInt()) {
                            1 -> {
                                println("Ingrese el nombre del titular del cheque:")
                                val nombreTitular = scanner.next()
                                println("Ingrese la entidad bancaria:")
                                val entidadBancaria = scanner.next()
                                val pago = PagoCheque(pedido.costeTotal, LocalDate.now(), nombreTitular, entidadBancaria)
                                if (pago.procesarPago()) {
                                    println("Pago con cheque procesado con éxito.")
                                    pedido.realizarPago(pago)
                                }
                            }
                            2 -> {
                                println("Ingrese el número de tarjeta:")
                                val numeroTarjeta = scanner.next()
                                println("Ingrese la fecha de caducidad (AAAA-MM-DD):")
                                val fechaCaducidad = LocalDate.parse(scanner.next())
                                println("Seleccione el tipo de tarjeta (1. VISA, 2. MASTERCARD):")
                                val tipoTarjeta = when (scanner.nextInt()) {
                                    1 -> Tarjeta.VISA
                                    2 -> Tarjeta.MASTERCARD
                                    else -> Tarjeta.VISA
                                }
                                val pago = PagoTarjetaCredito(pedido.costeTotal, LocalDate.now(), numeroTarjeta, fechaCaducidad, tipoTarjeta)
                                if (pago.procesarPago()) {
                                    println("Pago con tarjeta procesado con éxito.")
                                    pedido.realizarPago(pago)
                                }
                            }
                            3 -> {
                                println("Ingrese la moneda utilizada (USD, EUR, etc.):")
                                val moneda = scanner.next()
                                val pago = PagoEfectivo(pedido.costeTotal, LocalDate.now(), moneda)
                                if (pago.procesarPago()) {
                                    println("Pago en efectivo procesado con éxito.")
                                    pedido.realizarPago(pago)
                                }
                            }
                            else -> println("Opción no válida.")
                        }
                        println("El estado del pedido es ahora: ${pedido.estado}")
                    } else {
                        println("Índice de pedido no válido.")
                    }
                }

                3 -> {
                    println("Mostrando el estado de los pedidos...")
                    pedidos.forEachIndexed { index, pedido ->
                        println("Pedido $index: Estado: ${pedido.estado}, Coste Total: ${pedido.costeTotal}, Fecha de Pedido: ${pedido.fechaCreacion}")
                    }
                }

                4 -> {
                    sesionIniciada = false
                    continuar = false
                    println("Saliendo del sistema de gestión de pedidos.")
                }

                else -> println("Opción no válida, intenta de nuevo.")
            }
        }
    }
}