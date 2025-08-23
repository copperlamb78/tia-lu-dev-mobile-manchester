/*
1. Cadastrar Item: Permite adicionar novos produtos ao menu do restaurante.
2. Atualizar Item: Possibilita modificar informações de itens já cadastrados.
3. Criar Pedido: Inicia o processo de montagem de um novo pedido.
4. Atualizar Pedido: Permite alterar o status de um pedido existente.
5. Consultar Pedidos: Exibe informações sobre os pedidos, com a opção de filtrar por status.
*/
var codigoProduto = 0
data class Produto(
    var nome: String,
    var descricao: String,
    var valor: Double,
    var quantidade: Int,
    val codigo: Int = codigoProduto
)
enum class StatusPedido {
    ACEITO, FAZENDO, FEITO, ESPERANDO_ENTREGADOR, SAIU_PARA_ENTREGA, ENTREGUE
}
data class Pedido(
    val id: Int,
    val itens: MutableList<Produto>,
    var valorTotal: Double,
    var status: StatusPedido = StatusPedido.ACEITO
)

fun main() {

    var produtos = mutableListOf<Produto>()
    val pedidos = mutableListOf<Pedido>()
    var contadorPedidos = 0

    var continuarMenu = true

    do {
        for (produto in produtos) {
            println(produto)
        }
        println("[1] Cadastrar Item: ")
        println("[2] Atualizar Item: ")
        println("[3] Criar Pedido: ")
        println("[4] Atualizar Pedido: ")
        println("[5] Consultar Pedidos: ")
        println("[6] Sair")
        print("Qual opção deseja escolher: ")
        val entrada = readln().toInt()

        when (entrada) {
            1 -> {
                print("Qual nome do produto: ")
                val nome = readln()
                print("Descrição do produto: ")
                val descricao = readln()
                print("Qual o preço do produto: ")
                val preco = readln().toDouble()
                print("Quantos há em estoque: ")
                val quantidade = readln().toInt()
                codigoProduto += 1
                println("Esse é o código do produto: $codigoProduto")
                val produto = Produto(nome, descricao, preco, quantidade)
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
                } else {
                    println("Nenhum produto cadastrado!")
                }

            }

            3 -> { // CRIAR PEDIDO
                if (produtos.isEmpty()) {
                    println("Nenhum produto disponível para pedido!")
                } else {
                    println("Criar Pedido ")
                    val itensPedido = mutableListOf<Produto>()
                    var valorTotal = 0.0
                    var continuarPedido = true

                    do {
                        println("\nProdutos disponíveis:")
                        for ((index, produto) in produtos.withIndex()) {
                            println("[${index + 1}] ${produto.nome} - R$${produto.valor} (Estoque: ${produto.quantidade})")
                        }
                        println("[0] Finalizar pedido")
                        print("Escolha um produto pelo número: ")
                        val escolha = readln().toInt()

                        when {
                            escolha == 0 -> { // finalizar
                                continuarPedido = false
                            }

                            escolha in 1..produtos.size -> { // adicionar produto
                                val produtoEscolhido = produtos[escolha - 1]
                                print("Quantos deseja adicionar ao pedido? ")
                                val qtd = readln().toInt()

                                if (qtd <= produtoEscolhido.quantidade) {
                                    val produtoPedido = produtoEscolhido.copy(quantidade = qtd)
                                    itensPedido.add(produtoPedido)
                                    valorTotal += produtoPedido.valor * qtd
                                    produtoEscolhido.quantidade -= qtd
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
                        val pedido = Pedido(contadorPedidos, itensPedido, valorTotal)
                        pedidos.add(pedido)
                        println("Pedido criado com sucesso! ID: ${pedido.id}, Valor total: R$${pedido.valorTotal}")
                    } else {
                        println("Nenhum item foi adicionado ao pedido.")
                    }
                }
            }

            4 -> { // ATUALIZAR PEDIDO
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
                        for ((index, status) in StatusPedido.values().withIndex()) {
                            println("[${index + 1}] $status")
                        }
                        val novoStatus = readln().toInt()

                        when (novoStatus) {
                            in 1..StatusPedido.values().size -> {
                                pedidoSelecionado.status = StatusPedido.values()[novoStatus - 1]
                                println("Status atualizado com sucesso para ${pedidoSelecionado.status}")
                            }

                            else -> println("Opção inválida!")
                        }
                    } else {
                        println("Pedido não encontrado!")
                    }
                }
            }

            5 -> {
                if (pedidos.size == 0) {
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
                                for (pedido in pedidos) {
                                    println(pedido)
                                }
                            }

                            2 -> {
                                println("Pedidos ACEITO:")
                                for (pedido in pedidos) {
                                    if (pedido.status == StatusPedido.ACEITO) {
                                        println(pedido)
                                    }
                                }
                            }

                            3 -> {
                                println("Pedidos FAZENDO:")
                                for (pedido in pedidos) {
                                    if (pedido.status == StatusPedido.FAZENDO) {
                                        println(pedido)
                                    }
                                }
                            }

                            4 -> {
                                println("Pedidos FEITO:")
                                for (pedido in pedidos) {
                                    if (pedido.status == StatusPedido.FEITO) {
                                        println(pedido)
                                    }
                                }
                            }

                            5 -> {
                                println("Pedidos ESPERANDO_ENTREGADOR:")
                                for (pedido in pedidos) {
                                    if (pedido.status == StatusPedido.ESPERANDO_ENTREGADOR) {
                                        println(pedido)
                                    }
                                }
                            }

                            6 -> {
                                println("Pedidos SAIU_PARA_ENTREGA:")
                                for (pedido in pedidos) {
                                    if (pedido.status == StatusPedido.SAIU_PARA_ENTREGA) {
                                        println(pedido)
                                    }
                                }
                            }

                            7 -> {
                                println("Pedidos ENTREGUE:")
                                for (pedido in pedidos) {
                                    if (pedido.status == StatusPedido.ENTREGUE) {
                                        println(pedido)
                                    }
                                }
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

            6 -> {
                continuarMenu = false
            }
        }
    } while (continuarMenu)
}
