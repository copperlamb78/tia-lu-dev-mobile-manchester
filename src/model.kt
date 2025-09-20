val produtos = mutableListOf<Produto>() //Lista de produtos
val pedidos = mutableListOf<Pedido>() // Lista de pedidos

fun cadastrarItem (nome: String, descricao: String, preco: Double, quantidade: Int) {
    codigoProduto += 1
    val produto = Produto(nome, descricao, preco, quantidade) //adiciona O produto a lista de produtos
    produtos.add(produto)
}

fun atualizarItem (userIndexItem: Int, produtoAlterado: Produto, novoValor: String, userIndexProduto: Int) {
    when (userIndexItem) { // Aqui alteramos o valor da caracteristica do produto com seus respectivos tipos
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
    produtos[userIndexProduto] = produtoAlterado //trocamos o produto antigo pelo o produto alterado
}

fun adicionarProdutoAoPedido(produtoEscolhido: Produto, itensPedido: MutableList<Produto>, valorTotal: Double, qtd: Int): Double {
    val produtoPedido = produtoEscolhido.copy(quantidade = qtd)
    itensPedido.add(produtoPedido)
    return valorTotal + (produtoPedido.valor * qtd)
    produtoEscolhido.quantidade -= qtd
}

fun criarPedido (contadorPedidos: Int, itensPedido: MutableList<Produto>, valorTotal: Double) : Pedido {
    val pedido = Pedido(contadorPedidos, itensPedido, valorTotal)
    pedidos.add(pedido)
    return pedido
}

fun atualizarPedido (pedidoSelecionado: Pedido, novoStatus: Int) {
        pedidoSelecionado.status = StatusPedido.entries[novoStatus - 1]
}

fun consultarPedidosPorStatus(status: StatusPedido): List<Pedido> =
    pedidos.filter { it.status == status }