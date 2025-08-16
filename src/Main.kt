
/*
1. Cadastrar Item: Permite adicionar novos produtos ao menu do restaurante.
2. Atualizar Item: Possibilita modificar informações de itens já cadastrados.
3. Criar Pedido: Inicia o processo de montagem de um novo pedido.
4. Atualizar Pedido: Permite alterar o status de um pedido existente.
5. Consultar Pedidos: Exibe informações sobre os pedidos, com a opção de filtrar por status.
*/

fun main() {
    var codigoProduto = 0
    var produtos = mutableListOf<MutableList<Any>>()

    while(true) {
        println(produtos)
        println("[1] Cadastrar Item: ")
        println("[2] Atualizar Item: ")
        println("[3] Criar Pedido: ")
        println("[4] Atualizar Pedido: ")
        println("[5] Consultar Pedidos: ")
        print("Qual opção deseja escolher: ")
        var entrada = readln().toInt()



        when (entrada) {
            1 -> {
                print("Qual nome do produto: ")
                var nome = readln()
                print("Descrição do produto: ")
                var descricao = readln()
                print("Qual o preço do produto: ")
                var preco = readln().toDouble()
                print("Quantos há em estoque: ")
                var quantidade = readln().toInt()
                codigoProduto += 1
                println("Esse é o código do produto: $codigoProduto")
                var produto: MutableList<Any> = mutableListOf(nome, descricao, preco, quantidade, codigoProduto)
                produtos.add(produto)
            }
            2 -> {
                if (produtos.size != 0) {
                    println("Qual produto você deseja atualizar?")
                    var i = 1
                    for (item in produtos) {
                        println("[$i] -" + item[0])
                        i += 1
                    }
                    val userIndexProduto = readln().toInt() - 1
                    var x = 1
                    for (item in produtos[userIndexProduto]) {
                        println("Digite [$x] se você deseja mudar $item :")
                        x += 1
                    }
                    val userIndexItem = readln().toInt() - 1
                    var novoValor: Any = ""
                    when (userIndexItem) {
                        1 -> novoValor = readln()
                        2 -> novoValor = readln()
                        3 -> novoValor = readln().toDouble()
                        4 -> novoValor = readln().toInt()
                        5 -> novoValor = readln().toInt()
                    }
                    var produto = produtos[userIndexProduto]
                    produto[userIndexItem] = novoValor
                    produtos[userIndexProduto] = produto
                } else  {
                    println("Nenhum produto cadastrado!")
                }

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
