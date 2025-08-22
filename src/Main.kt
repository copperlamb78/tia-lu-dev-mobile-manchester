/*
1. Cadastrar Item: Permite adicionar novos produtos ao menu do restaurante.
2. Atualizar Item: Possibilita modificar informações de itens já cadastrados.
3. Criar Pedido: Inicia o processo de montagem de um novo pedido.
4. Atualizar Pedido: Permite alterar o status de um pedido existente.
5. Consultar Pedidos: Exibe informações sobre os pedidos, com a opção de filtrar por status.
*/

fun main() {
    var codigoProduto = 0

    data class Produto(
        var nome: String,
        var descricao: String,
        var valor: Double,
        var quantidade: Int,
        var codigo: Int = codigoProduto
    )

    var produtos = mutableListOf<Produto>()
    enum class StatusPedido {
        ACEITO, FAZENDO, FEITO, ESPERANDO_ENTREGADOR, SAIU_PARA_ENTREGA, ENTREGUE
    }
    data class Pedido(
        val id: Int,
        val itens: MutableList<Produto>,
        var valorTotal: Double,
        var status: StatusPedido = StatusPedido.ACEITO
    )
    val pedidos = mutableListOf<Pedido>()
    var contadorPedidos = 0
   


    while(true) {
        for (produto in produtos) {
            println(produto)
        }
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
                var produto = Produto(nome, descricao, preco, quantidade)
                produtos.add(produto)
            }
            2 -> {
                if (produtos.size != 0) {
                    println("Qual produto você deseja atualizar?")
                    var i = 1
                    for (item in produtos) {
                        println("[$i] -" + item.nome)
                        i += 1
                    }
                    val userIndexProduto = readln().toInt() - 1
                    var produtoAlterado = produtos[userIndexProduto]
                    println("[1]" + produtoAlterado.nome)
                    println("[2]" + produtoAlterado.descricao)
                    println("[3]" + produtoAlterado.valor)
                    println("[4]" + produtoAlterado.quantidade)
                    print("Digite qual opção desejam mudar: ")

                    val userIndexItem = readln().toInt()
                    var novoValor: Any = ""
                    print("Digite o novo valor: ")
                    novoValor = readln()
                    when (userIndexItem) {
                        1 -> {
                            produtoAlterado.nome = novoValor
                        }
                        2 -> {
                            produtoAlterado.descricao = novoValor
                        }
                        3 -> {
                            produtoAlterado.valor = novoValor.toDouble()
                        }
                        4 -> {
                            produtoAlterado.quantidade = novoValor.toInt()
                        }
                    }
                    produtos[userIndexProduto] = produtoAlterado
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
                 if (pedidos.isEmpty()) {
                    println("Nenhum pedido disponível!")
                } else {
                    var continuar = true
                    while (continuar) {
                        println("----- Submenu de Consulta de Pedidos -----")
                        println("[1] Mostrar todos os pedidos")
                        println("[2] Filtrar pedidos por status")
                        println("[3] Voltar ao menu principal")
                        print("Escolha uma opção: ")
                        when (readln().toInt()) {
                            1 -> pedidos.forEach { println(it) }
                            2 -> {
                                println("Escolha o status para filtrar:")
                                StatusPedido.values().forEachIndexed { index, status -> println("[$index] $status") }
                                val escolha = readln().toInt()
                                val statusFiltro = StatusPedido.values()[escolha]
                                val pedidosFiltrados = pedidos.filter { it.status == statusFiltro }
                                if (pedidosFiltrados.isEmpty()) {
                                    println("Nenhum pedido com status $statusFiltro")
                                } else {
                                    pedidosFiltrados.forEach { println(it) }
                                }
                            }
                            3 -> continuar = false
                            else -> println("Opção inválida! Tente novamente.")
                        }
                    }
                }
            }
        }
    }
}