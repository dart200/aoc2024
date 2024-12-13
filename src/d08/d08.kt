package d08

import java.io.File

private fun <T> Iterable<T>.expandPairs() = sequence {
    this@expandPairs.forEach { a -> this@expandPairs.forEach { b -> if (a!=b) yield(a to b) } }
}
typealias IntPair = Pair<Int,Int>
typealias AntinodeTest = (IntPair, IntPair, IntPair) -> Boolean
private fun IntPair.getVec(b: IntPair) = Pair(b.first-this.first, b.second-this.second)
private fun IntPair.sameDir(b: IntPair) = this.first * b.second == this.second * b.first

fun main () {
    val antennas = HashMap<Char, ArrayList<IntPair>>()
    val lines = File("src/d08/d08.in").readLines().mapIndexed {r,line -> line.mapIndexed {c,cell ->
        if (cell != '.' && cell != '#') antennas.getOrPut(cell) {arrayListOf()}.add(r to c)
        cell
    }}
    fun sumBy (tester: AntinodeTest) = lines.indices.sumOf { r -> lines[0].indices.sumOf { c ->
        if (antennas.values.any { it.expandPairs().any { (t0,t1) -> tester(r to c,t0,t1) }}) 1 else 0 as Int }
    }
    println(sumBy({cell, t0, t1 -> cell.getVec(t0) == t0.getVec(t1)}))
    println(sumBy({cell, t0, t1 -> cell.getVec(t0).sameDir(cell.getVec(t1))}))
}

