package models

class Cliente (nombre: String, codigo: String, telefono: Int,direccion: String ){
    var nombre: String = ""
    var codigo: String = ""
    var telefono: Int = 0
    var direccion: String = ""

    init{
        this.nombre = nombre
        this.codigo = codigo
        this.telefono = telefono
        this.direccion = direccion
    }

    companion object {
        fun ValidaCliente(nombre: String, codigo: String, listaClientes: List<Cliente>): Boolean {
            return listaClientes.any { it.nombre == nombre && it.codigo == codigo }
        }fun ObtenerCliente(codigo: String, listaClientes: List<Cliente>): Cliente? {
            return listaClientes.find { it.codigo == codigo }
        }
    }
}
