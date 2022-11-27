import java.text.NumberFormat
import java.util.Locale

private const val PAES = 1
private const val SALGADOS = 2
private const val DOCES = 3
private const val FINALIZAR = 0

//Todos os pães
const val itemPaoFrances = "Pão Francês"
const val itemPaoDeLeite = "Pão de Leite"
const val itemPaoDeMilho = "Pão de Milho"

const val precoPaoFrances = 0.60
const val precoPaoDeLeite = 0.40
const val precoPaoDeMilho = 0.50

val paes: List <Pair<String,Double>> = listOf(
    Pair(itemPaoFrances,precoPaoFrances),
    Pair(itemPaoDeLeite,precoPaoDeLeite),
    Pair(itemPaoDeMilho,precoPaoDeMilho)
)

val menuPaes = """
    1 - $itemPaoFrances...........${NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(precoPaoFrances)}
    2 - $itemPaoDeLeite..........${NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(precoPaoDeLeite)}
    3 - $itemPaoDeMilho..........${NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(precoPaoDeMilho)}
    0 - Voltar
""".trimIndent()

//Todos os salgados
const val itemCoxinha = "Coxinha"
const val itemEsfirra = "Esfirra"
const val itemPaoDeQueijo = "Pão de Queijo"

const val precoCoxinha = 5.00
const val precoEsfirra = 6.00
const val precoPaoDeQueijo = 3.00

val salgados: List <Pair<String,Double>> = listOf(
    Pair(itemCoxinha,precoCoxinha),
    Pair(itemEsfirra,precoEsfirra),
    Pair(itemPaoDeQueijo,precoPaoDeQueijo)
)

val menuSalgados = """
    1 - $itemCoxinha..........${NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(precoCoxinha)}
    2 - $itemEsfirra..........${NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(precoEsfirra)}
    3 - $itemPaoDeQueijo..........${NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(precoPaoDeQueijo)}
    0 - Voltar
""".trimIndent()

//Todos os doces

const val itemCarolina = "Carolina"
const val itemPudim = "Pudim"
const val itemBrigadeiro = "Brigadeiro"

const val precoCarolina = 1.50
const val precoPudim = 4.00
const val precoBrigadeiro = 2.00

val doces: List <Pair<String,Double>> = listOf(
    Pair(itemCarolina,precoCarolina),
    Pair(itemPudim,precoPudim),
    Pair(itemBrigadeiro,precoBrigadeiro)
)

val menuDoces = """
    1 - $itemCarolina..........${NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(precoCarolina)}
    2 - $itemPudim..........${NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(precoPudim)}
    3 - $itemBrigadeiro..........${NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(precoBrigadeiro)}
    0 - Voltar
""".trimIndent()

val menuInicial = """
    Digite a opção desejada no menu:
    1....................Pães
    2................Salgados
    3...................Doces
    0........Finalizar compra
""".trimIndent()

const val listaComanda = """
----------------Comanda E-Padoca----------------
Item....Produto..........Qtd.....Valor.....Total
"""

val comanda: MutableList<String> = mutableListOf<String>()
var contaFinal = 0.00

//Descontos
const val cincoPadoca = 0.95
const val dezPadoca = 0.90
const val descontoCinco = 5

fun main() {

    do {
        var sair = "S"
        ePadoca()

        if (comanda.isEmpty()){
            println("Deseja sair sem pedir? (S/N)")
            sair = readln().uppercase()
        } else {

            val totalDaConta = desconto(contaFinal)

            println(listaComanda)

            comanda.forEach { item ->
                println(item)
            }

            println("""
            TOTAL==================================>${NumberFormat.getCurrencyInstance(Locale("pt","BR")).format(totalDaConta)}
            """.trimIndent()
            )
            println("------------------VOLTE SEMPRE------------------")
        }
    } while (sair != "S")
}

fun ePadoca () {

    do {
        println(menuInicial)
        val opcao = readln().toInt()

        when (opcao) {
            PAES -> escolherProduto(menuPaes, paes)
            SALGADOS -> escolherProduto(menuSalgados, salgados)
            DOCES -> escolherProduto(menuDoces, doces)
            else -> Unit
        }
    } while (opcao != FINALIZAR)
}

fun escolherProduto(menuEscolhido: String, produtos: List<Pair<String, Double>>) {

    do {
        println(menuEscolhido)
        val produtoSelecionado = readln().toInt()

        for ((i, produto) in produtos.withIndex()) {
            if (i.inc() == produtoSelecionado) {
                selecionarQuantidade(produto)
                break
            }
        }

    } while (produtoSelecionado != 0)

}

fun selecionarQuantidade(produto: Pair<String, Double>) {
    println("Digite a quantidade:")
    val quantidade = readln().toInt()
    val totalDoItem = quantidade * produto.second
    val item = programaComanda(produto.first, quantidade, produto.second, totalDoItem)
    comanda.add(item)
    contaFinal += totalDoItem
}

fun programaComanda(produto: String, quantidade: Int, valorUnit: Double, totalDoItem: Double): String =
    "${comanda.size.inc()}...." +
            "$produto...." +
            "$quantidade......" +
            "${NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(valorUnit)}......" +
            "${NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(totalDoItem)}"

fun desconto(contafinal: Double): Double {

    println("Adicione um cupom de desconto ou pressione ENTER para continuar:")
    val temCupom = readln().uppercase()

    var valorComDesconto = contafinal
    if (temCupom.isNotBlank()) {
        when (temCupom) {
            "5PADOCA" -> valorComDesconto *= cincoPadoca
            "10PADOCA" -> valorComDesconto *= dezPadoca
            "5OFF" ->  valorComDesconto -= descontoCinco

            else -> {println("Cupom inválido")
                desconto(contafinal)}
        }
        return maxOf(valorComDesconto, 0.00)
    }
    return valorComDesconto
}