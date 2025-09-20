var codigoProduto = 0

val produtos = mutableListOf<Produto>() //Lista de produtos
val pedidos = mutableListOf<Pedido>() // Lista de pedidos

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
    produtoEscolhido.quantidade -= qtd
    return valorTotal + (produtoPedido.valor * qtd)
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
