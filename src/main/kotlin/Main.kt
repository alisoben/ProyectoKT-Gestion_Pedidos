import data.ListaClientes
import data.ListaProductos
import enumerations.Tarjeta
import enumerations.Estado
import models.*
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
        print("Ingrese su código:")
        val codigoInput = scanner.nextLine()

        if (Cliente.validaCliente(ListaClientes, nombreInput, codigoInput)) {
            println("Inicio de sesión exitoso para el cliente: $nombreInput")
            sesionIniciada = true
        } else {
            println("Error: nombre o código incorrecto.")
        }

        while (sesionIniciada) {
            val clienteActual: Cliente? = Cliente.obtenerCliente(ListaClientes, codigoInput)
            println("\n--- Menú ---")
            println("1. Realizar nuevo pedido")
            println("2. Pagar tus pedidos ")
            println("3. Estado de tus pedidos")
            println("4. Eliminar pedidos")
            println("5. Salir")
            print("Selecciona una opción:")
            val opcion = try {
                scanner.nextInt()
            } catch (e: InputMismatchException) {
                println("Opción no válida, por favor ingresa un numero correcto.")
                scanner.nextLine()
                continue
            }
            val nuevoPedido = Pedido(clienteActual)
            when (opcion) {
                1 -> {
                    var agregarDetalle = true
                    Producto.mostrarProducto(ListaProductos)
                    while (agregarDetalle) {
                        print("Ingrese el ID del producto que desea comprar:")
                        val productoId = try {
                            scanner.nextInt()
                        } catch (e: InputMismatchException) {
                            println("Opción no válida, por favor ingresa un número valido.")
                            scanner.nextLine()
                            continue
                        }
                        val producto = Producto.buscarProducto(ListaProductos, productoId)
                        if (producto != null) {
                            if (producto.stock > 0) {
                                print("Ingrese la cantidad:")
                                var cantidadNoPermitida = true
                                while(cantidadNoPermitida){
                                    val cantidad = try {
                                        scanner.nextInt()
                                    } catch (e: InputMismatchException) {
                                        print("Opción no válida, por favor ingrese una cantidad válida: ")
                                        scanner.nextLine()
                                        continue
                                    }
                                    if (cantidad <= producto.stock) {
                                        val detalle = Detalle(producto, cantidad)
                                        nuevoPedido.agregarDetalle(detalle)
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

                        print("¿Desea agregar otro pedido? (s/n): ")
                        val respuesta = scanner.next()
                        agregarDetalle = (respuesta.lowercase() == "s")
                    }
                    pedidos.add(nuevoPedido)
                    println("Pedido creado con éxito. Coste total: ${nuevoPedido.costeTotal}")
                }

                2 -> {
                    if (pedidos.isEmpty()) {
                        println("No hay pedidos para pagar.")
                        continue
                    }else{
                        nuevoPedido.mostrarDetallePedido(pedidos)
                        print("Seleccione el índice del pedido a pagar (1 a ${pedidos.size}): ")
                        val indicePedido = try {
                            scanner.nextInt() - 1
                        } catch (e: InputMismatchException) {
                            println("Opción no válida, ingresa un número valido.")
                            scanner.nextLine()
                            continue
                        }
                        if (indicePedido in pedidos.indices) {
                            val pedido = pedidos[indicePedido]
                            if (pedido.estado == Estado.PAGADO) {
                                println("El pedido seleccionado ya está pagado.")
                                continue
                            }
                            pedido.mostrarResumen()
                            println("Metodos de pago disponibles")
                            println("1. Tarjeta de crédito")
                            println("2. Cheque")
                            println("3. Efectivo")
                            println("4. Cuotas")
                            print("Seleccione una opción:")
                            val metodoPago = try {
                                scanner.nextInt()
                            } catch (e: InputMismatchException) {
                                println("Opción no válida, por favor ingresa un número valido.")
                                scanner.nextLine()
                                continue
                            }


                            val pago: Pago = when (metodoPago) {
                                1 -> {
                                    print("Ingrese el número de tarjeta de crédito: ")
                                    val numeroTarjeta = scanner.next()
                                    print("Ingrese la fecha de caducidad (YYYY-MM-DD): ")
                                    val fechaCaducidad = (LocalDate.parse(scanner.next()))
                                    println("Ingrese el tipo de tarjeta (1.VISA,2.MASTERCARD): ")
                                    val tipoTarjeta = if (scanner.nextInt() == 1) Tarjeta.VISA else Tarjeta.MASTERCARD
                                    PagoTarjetaCredito(pedido.costeTotal, LocalDate.now(), numeroTarjeta, fechaCaducidad, tipoTarjeta)
                                }

                                2 -> {
                                    print("Ingrese el nombre del titular(cheque): ")
                                    val nombreTitular = scanner.next()
                                    print("Ingrese el número(cheque): ")
                                    val numeroCheque = scanner.next()
                                    print("Ingrese el  nombre del banco:")
                                    var entidadBancaria = scanner.next()
                                    PagoCheque(pedido.costeTotal, LocalDate.now(), nombreTitular, numeroCheque, entidadBancaria)
                                }

                                3 -> {
                                    print("Ingrese el tipo de moneda(USD, EUR, PEN): ")
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
                    if (pedidos.isEmpty()) {
                        println("No hay pedidos para mostrar.")
                        continue
                    } else {
                        println("Mostrando el estado de los pedidos...")
                        nuevoPedido.mostrarDetallePedido(pedidos)
                    }
                }

                4 -> {
                    if (pedidos.isEmpty()) {
                        println("No hay pedidos para eliminar.")
                        continue
                    } else {
                        nuevoPedido.mostrarDetallePedido(pedidos)
                        print("Seleccione el índice del pedido a eliminar (1 a ${pedidos.size}):")
                        val indicePedido = try {
                            scanner.nextInt() - 1
                        } catch (e: InputMismatchException) {
                            println("Opción no válida, ingresa un número válido.")
                            scanner.nextLine()
                            continue
                        }
                        if (indicePedido in pedidos.indices) {
                            val pedido = pedidos[indicePedido]
                            if (pedido.estado == Estado.PENDIENTE) {
                                pedido.eliminarPedido()
                                pedidos.removeAt(indicePedido)
                            } else {
                                println("Solo se pueden eliminar pedidos en estado pendiente.")
                            }
                        } else {
                            println("Índice fuera de rango. Selecciona un número válido.")
                        }
                    }
                }

                5 -> {
                    sesionIniciada = false
                    continuar = false
                    println("Saliendo del sistema de gestión de pedidos.")
                }

                else -> println("Opción no válida, intenta de nuevo.")
            }
        }
    }
}