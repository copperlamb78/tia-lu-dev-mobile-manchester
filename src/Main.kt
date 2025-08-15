
/*
1. Cadastrar Item: Permite adicionar novos produtos ao menu do restaurante.
2. Atualizar Item: Possibilita modificar informações de itens já cadastrados.
3. Criar Pedido: Inicia o processo de montagem de um novo pedido.
4. Atualizar Pedido: Permite alterar o status de um pedido existente.
5. Consultar Pedidos: Exibe informações sobre os pedidos, com a opção de filtrar por status.
*/

fun main() {
    while(true) {
        println("[1] Cadastrar Item: ")
        println("[2] Atualizar Item: ")
        println("[3] Criar Pedido: ")
        println("[4] Atualizar Pedido: ")
        println("[5] Consultar Pedidos: ")
        print("Qual opção deseja escolher: ")

        var entrada = readln().toInt()

        when (entrada) {
            1 -> {
                println("Cadastrar Item")
            }
            2 -> {
                println("Atualizar Item")
            }
            3 -> {
                println("Criar Pedido")
            }
            4 -> {
                println("Atualizar Pedido")
            }
            5 -> {
                println("Consultar Pedidos")
            }
        }
    }
}
