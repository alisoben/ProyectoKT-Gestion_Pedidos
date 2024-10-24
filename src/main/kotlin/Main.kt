import models.Cliente
import models.Pedido
import models.Detalle
import models.Producto
import models.Pago
import enumerations.Estado
import data.ListaClientes
import data.ListaProductos
import enumerations.Tarjeta
import models.PagoCheque
import models.PagoCuotas
import models.PagoEfectivo
import models.PagoTarjetaCredito
import java.time.LocalDate
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

            val opcion = try {
                scanner.nextInt()
            } catch (e: InputMismatchException) {
                println("Opción no válida, por favor ingresa un número.")
                scanner.nextLine() // Limpiar la entrada
                continue
            }

            when (opcion) {
                1 -> {
                    val nuevoPedido = Pedido()
                    var agregarDetalle = true
                    println("Lista de productos disponibles:")

                    ListaProductos

                    data.ListaProductos.forEach {
                        val estadoStock = if (it.stock > 0) "${it.stock}" else "Agotado"
                        println("ID: ${it.idProducto}, Producto: ${it.nombreProducto}, Precio: ${it.precioUnitario}, Stock: $estadoStock")
                    }

                    while (agregarDetalle) {
                        print("Ingrese el ID del producto que desea comprar:")
                        //val productoId = scanner.nextInt()
                        val productoId = try {
                            scanner.nextInt()
                        } catch (e: InputMismatchException) {
                            println("Opción no válida, por favor ingresa un número valido.")
                            scanner.nextLine() // Limpiar la entrada
                            continue
                        }
                        //val producto = Producto.listaProductos.find { it.idProducto == productoId }
                        val producto = data.ListaProductos.find { it.idProducto == productoId }

                        // Verificar si el producto existe y tiene stock disponible
                        if (producto != null) {
                            if (producto.stock > 0) {
                                print("Ingrese la cantidad:")
                                //val cantidad = scanner.nextInt()
                                var cantidadNoPermitida = true
                                while(cantidadNoPermitida){
                                    val cantidad = try {
                                        scanner.nextInt()
                                    } catch (e: InputMismatchException) {
                                        print("Opción no válida, por favor ingrese una cantidad válida: ")
                                        scanner.nextLine() // Limpiar la entrada
                                        continue
                                    }

                                    if (cantidad <= producto.stock) {
                                        val detalle = Detalle(producto, cantidad)
                                        nuevoPedido.agregarDetalle(detalle)

                                        // Reducir el stock del producto
                                        //producto.stock -= cantidad
                                        println("Producto ${producto.nombreProducto} agregado al pedido.")
                                    } else {
                                        println("No hay suficiente stock para la cantidad solicitada.")
                                    }
                                    cantidadNoPermitida = false
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
                    if (pedidos.isEmpty()) {
                        println("No hay pedidos para pagar.")
                        continue
                    }else{
                        println("Lista de pedidos:")
                        pedidos.forEachIndexed { index, pedido ->
                            println("Pedido ${index + 1} : Estado: ${pedido.estado}, Coste Total: ${pedido.costeTotal}, Fecha de Pedido: ${pedido.fechaCreacion}")
                        }

                        println("Seleccione el índice del pedido a pagar (0 a ${pedidos.size - 1}):")
                        //val indicePedido = scanner.nextInt()
                        val indicePedido = try {
                            scanner.nextInt()
                        } catch (e: InputMismatchException) {
                            println("Opción no válida, por favor ingresa un número valido.")
                            scanner.nextLine() // Limpiar la entrada
                            continue
                        }
                        if (indicePedido in pedidos.indices) {
                            val pedido = pedidos[indicePedido]
                            pedido.mostrarResumen()
                            println("Seleccionar el metodo de pago: ")
                            println("1. Tarjeta de crédito")
                            println("2. Cheque")
                            println("3. Efectivo")
                            println("4. Cuotas")
                            //val metodoPago = scanner.nextInt()

                            val metodoPago = try {
                                scanner.nextInt()
                            } catch (e: InputMismatchException) {
                                println("Opción no válida, por favor ingresa un número valido.")
                                scanner.nextLine() // Limpiar la entrada
                                continue
                            }


                            val pago: Pago = when (metodoPago) {
                                1 -> {
                                    println("Ingrese el número de tarjeta de crédito:")

                                    //Incluir el pago a cuotas llamando a PagoCuotas...
                                    val numeroTarjeta = scanner.next()
                                    println("Ingrese la fecha de caducidad (YYYY-MM-DD):")
                                    val fechaCaducidad = (LocalDate.parse(scanner.next()))
                                    println("Ingrese el tipo de tarjeta (1. VISA, 2. MASTERCARD):")
                                    val tipoTarjeta = if (scanner.nextInt() == 1) Tarjeta.VISA else Tarjeta.MASTERCARD
                                    PagoTarjetaCredito(pedido.costeTotal, LocalDate.now(), numeroTarjeta, fechaCaducidad, tipoTarjeta)
                                }

                                2 -> {
                                    println("Ingrese el nombre del titular:")
                                    val nombreTitular = scanner.next()
                                    println("Ingrese el número de cheque:")
                                    val numeroCheque = scanner.next()
                                    println("Ingrese el banco:")
                                    var entidadBancaria = scanner.next()
                                    PagoCheque(pedido.costeTotal, LocalDate.now(), nombreTitular, numeroCheque, entidadBancaria)
                                }

                                3 -> {
                                    print("Ingrese la moneda:")
                                    val moneda = scanner.next()
                                    PagoEfectivo(pedido.costeTotal, LocalDate.now(), moneda)

                                }

                                4 ->{
                                    print("Ingrese el número de cuotas:")
                                    val numeroCuotas = scanner.nextInt()
                                    val montoPorCuota = pedido.costeTotal / numeroCuotas
                                    PagoCuotas(pedido.costeTotal, LocalDate.now(), numeroCuotas, montoPorCuota)
                                }

                                else -> {
                                    println("Método de pago no válido.")
                                    continue
                                }
                            }

                            if (pedido.realizarPago(pago)) {
                                println("Pago realizado. El estado del pedido es ahora: ${pedido.estado}")
                            } else {
                                println("Error al procesar el pago.")
                            }
                        } else {
                            println("Índice de pedido no válido.")
                        }
                    }
                }

                3 -> {
                    println("Mostrando el estado de los pedidos...")
                    pedidos.forEachIndexed { index, pedido ->
                        println("Pedido ${index + 1}: Estado: ${pedido.estado}, Coste Total: ${pedido.costeTotal}, Fecha de Pedido: ${pedido.fechaCreacion}")
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