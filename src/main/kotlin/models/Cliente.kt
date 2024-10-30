package models

class Cliente (nombre: String, codigo: String, telefono: Int, direccion: String){
    var nombre: String
    var codigo: String
    var telefono: Int
    var direccion: String

    init {
        this.nombre = nombre
        this.codigo = codigo
        this.telefono = telefono
        this.direccion = direccion
    }

    companion object {
        fun validaCliente(listaClientes: List<Cliente>, nombreInput: String, codigoInput: String): Boolean {
            return listaClientes.any { it.nombre == nombreInput && it.codigo == codigoInput }
        }

        fun obtenerCliente(listaClientes: List<Cliente>, codigoInput: String): Cliente? {
            return listaClientes.find { it.codigo == codigoInput }
        }
    }
}
