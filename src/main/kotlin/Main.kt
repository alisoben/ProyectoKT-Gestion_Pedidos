import java.util.Scanner
import models.Cliente
import data.ListaClientes

fun main() {
    val scanner = Scanner(System.`in`)
    var continuar = true
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
            println("2. Estado de tus pedido")
            println("3. Salir")
            println("Selecciona una opción:")

            when (scanner.nextInt()) {
                1 -> {
                    println("Pedido creado para el cliente: ")
                }

                2 -> {
                    println("Mostrando el estado de los pedidos...")
                }

                3 -> {
                    sesionIniciada = false
                    println("Saliendo del sistema de gestión de pedidos.")
                }

                else -> println("Opción no válida, intenta de nuevo.")
            }
        }
    }
}