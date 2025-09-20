/*
1. Cadastrar Item: Permite adicionar novos produtos ao menu do restaurante.
2. Atualizar Item: Possibilita modificar informações de itens já cadastrados.
3. Criar Pedido: Inicia o processo de montagem de um novo pedido.
4. Atualizar Pedido: Permite alterar o status de um pedido existente.
5. Consultar Pedidos: Exibe informações sobre os pedidos, com a opção de filtrar por status.
*/
var codigoProduto = 0
data class Produto( //Classe do produto
    var nome: String,
    var descricao: String,
    var valor: Double,
    var quantidade: Int,
    val codigo: Int = codigoProduto
)
enum class StatusPedido { // Clase para status do pedido
    ACEITO, FAZENDO, FEITO, ESPERANDO_ENTREGADOR, SAIU_PARA_ENTREGA, ENTREGUE
}
data class Pedido( // Classe para pedidos
    val id: Int,
    val itens: MutableList<Produto>,
    var valorTotal: Double,
    var status: StatusPedido = StatusPedido.ACEITO
)

fun main() {

    val desconto = 0.12 // 12% de desconto


    var contadorPedidos = 0

    var continuarMenu = true //Variável para finalizar loop

    do {
        println("[1] Cadastrar Item: ")
        println("[2] Atualizar Item: ")
        println("[3] Consultar Itens: ")
        println("[4] Criar Pedido: ")
        println("[5] Atualizar Pedido: ")
        println("[6] Consultar Pedidos: ")
        println("[7] Sair")
        print("Qual opção deseja escolher: ")
        val entrada = readln().toInt()

        when (entrada) {
            1 -> { // Cadastrar Item
                print("Qual nome do produto: ")
                val nome = readln()
                print("Descrição do produto: ")
                val descricao = readln()
                print("Qual o preço do produto: ")
                val preco = readln().toDouble()
                print("Quantos há em estoque: ")
                val quantidade = readln().toInt()
                cadastrarItem(nome, descricao, preco, quantidade)
                println("Esse é o código do produto: $codigoProduto")
            }

            2 -> { // Atualizar item
                if (produtos.isNotEmpty()) {
                    println("Qual produto você deseja atualizar?")
                    produtos.forEachIndexed { index, produto ->
                        println("[$index] - ${produto.nome}")
                    }
                    val userIndexProduto = readln().toInt() - 1
                    val produtoAlterado = produtos[userIndexProduto] //Aqui atribuimos o produto que queremos alterar a uma variavel
                    println("[1] Nome - " + produtoAlterado.nome)
                    println("[2] Descrição - " + produtoAlterado.descricao)
                    println("[3] Valor - " + "R$" + produtoAlterado.valor)
                    println("[4] Quantidade - " + produtoAlterado.quantidade)
                    println("Digite qual opção desejam mudar: ")

                    val userIndexItem = readln().toInt()
                    println("Digite o novo valor: ")
                    val novoValor = readln()
                    atualizarItem(userIndexItem, produtoAlterado, novoValor, userIndexProduto)
                } else {
                    println("Nenhum produto cadastrado!")
                }

            }

            3 -> { // Consultar pedido
                consultarItem()
            }

            4 -> { // CRIAR PEDIDO
                if (produtos.isEmpty()) {
                    println("Nenhum produto disponível para pedido!")
                } else {
                    println("Criar Pedido ")
                    val itensPedido = mutableListOf<Produto>()
                    var valorTotal = 0.0
                    var continuarPedido = true

                    do {
                        println("\nProdutos disponíveis:")
                        produtos.forEachIndexed {
                            index,
                            produto -> println("[${index + 1}] ${produto.nome} - R$${produto.valor} (Estoque: ${produto.quantidade})")
                        }
                        println("[0] Finalizar pedido")
                        print("Escolha um produto pelo número: ")
                        when (val escolha = readln().toInt()) {
                            0 -> { // finalizar
                                continuarPedido = false
                            }
                            in 1..produtos.size -> { // adicionar produto
                                val produtoEscolhido = produtos[escolha - 1]
                                print("Quantos deseja adicionar ao pedido? ")
                                val qtd = readln().toInt()

                                if (qtd <= produtoEscolhido.quantidade) {
                                    valorTotal = adicionarProdutoAoPedido(produtoEscolhido, itensPedido, valorTotal, qtd)
                                    println("${qtd}x ${produtoEscolhido.nome} adicionado ao pedido!")
                                } else {
                                    println("Estoque insuficiente!")
                                }
                            }
                            else -> println("Opção inválida!")
                        }
                    } while (continuarPedido)

                    if (itensPedido.isNotEmpty()) {
                        contadorPedidos++
                        if (valorTotal >= 150) { //Perguntar a usuário se quer cupom!
                            println("Parabéns! Você ganhou 12% de desconto em cupom!")
                            do {
                                println("Deseja adicionar o cupom? [1] Sim [2] Não")
                                val entradaDesconto = readln().toInt()
                                when (entradaDesconto) {
                                    1 -> { //Aqui ele vai alterar o valortotal e mostrará o valor antigo e o alterado
                                        print("Valor anterior: ${valorTotal}, Valor com desconto: ")
                                        valorTotal -= (valorTotal * desconto)
                                        println(valorTotal)
                                    }

                                    2 -> println("Tudo bem!")

                                    else -> println("Nenhum valor escolhido")
                                }
                            } while (entradaDesconto != 1 && entradaDesconto != 2)

                        }
                        val pedido = criarPedido(contadorPedidos, itensPedido, valorTotal)
                        println("Pedido criado com sucesso! ID: ${pedido.id}, Valor total pago: R$${pedido.valorTotal}")
                    } else {
                        println("Nenhum item foi adicionado ao pedido. Para criar pedido é necessário adicionar um item pelo menos")
                    }
                }
            }

            5 -> { //ATUALIZAR PEDIDO
                if (pedidos.isEmpty()) {
                    println("Nenhum pedido para atualizar!")
                } else {
                    println("Atualizar Pedido")
                    for (pedido in pedidos) {
                        println("Pedido ${pedido.id} - Status: ${pedido.status}")
                    }
                    print("Digite o ID do pedido que deseja atualizar: ")
                    val idPedido = readln().toInt()
                    val pedidoSelecionado = pedidos.find { it.id == idPedido }

                    if (pedidoSelecionado != null) {
                        println("Escolha o novo status:")
                        StatusPedido.entries.forEachIndexed {
                            index,
                            status -> println("[${index + 1}] $status")
                        }


                        when (val novoStatus = readln().toInt()) {
                            in 1..StatusPedido.entries.size -> {
                                atualizarPedido(pedidoSelecionado, novoStatus)
                                println("Status atualizado com sucesso para ${pedidoSelecionado.status}")
                        }

                            else -> println("Opção inválida!")
                        }
                    } else {
                        println("Pedido não encontrado!")
                    }
                }
            }

            6 -> { //Consultar pedido
                if (pedidos.isEmpty()) {
                    println("Nenhum pedido cadastrado!")
                } else {
                    var continuarConsulta = true
                    do {
                        println("----- Submenu de Consulta de Pedidos -----")
                        println("[1] Mostrar todos os pedidos")
                        println("[2] Mostrar apenas pedidos ACEITO")
                        println("[3] Mostrar apenas pedidos FAZENDO")
                        println("[4] Mostrar apenas pedidos FEITO")
                        println("[5] Mostrar apenas pedidos ESPERANDO_ENTREGADOR")
                        println("[6] Mostrar apenas pedidos SAIU_PARA_ENTREGA")
                        println("[7] Mostrar apenas pedidos ENTREGUE")
                        println("[8] Voltar ao menu principal")
                        print("Escolha uma opção: ")
                        val opcao = readln().toInt()
                        when (opcao) {
                            1 -> {
                                println("Lista de pedidos:")
                                pedidos.forEach { pedido -> println(pedido) }
                            }

                            2 -> {
                                println("Pedidos ACEITO:")
                                pedidos.filter { it.status == StatusPedido.ACEITO }
                                    .forEach { println(it) }
                            }

                            3 -> {
                                println("Pedidos FAZENDO:")
                                pedidos.filter { it.status == StatusPedido.FAZENDO }
                                    .forEach { println(it) }
                            }

                            4 -> {
                                println("Pedidos FEITO:")
                                pedidos.filter { it.status == StatusPedido.FEITO }
                                    .forEach { println(it) }
                            }

                            5 -> {
                                println("Pedidos ESPERANDO_ENTREGADOR:")
                                    pedidos.filter { it.status == StatusPedido.ESPERANDO_ENTREGADOR }
                                        .forEach { println(it) }
                            }

                            6 -> {
                                println("Pedidos SAIU_PARA_ENTREGA:")
                                pedidos.filter { it.status == StatusPedido.SAIU_PARA_ENTREGA }
                                    .forEach { println(it) }
                            }

                            7 -> {
                                println("Pedidos ENTREGUE:")
                                pedidos.filter { it.status == StatusPedido.ENTREGUE }
                                    .forEach { println(it) }
                            }

                            8 -> {
                                continuarConsulta = false // Sai do submenu
                            }

                            else -> {
                                println("Opção inválida!")
                            }
                        }
                    } while (continuarConsulta)
                }
            }

            7 -> continuarMenu = false //fechar menu
        }
    } while (continuarMenu)
}
