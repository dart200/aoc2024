package d25

import java.io.File

fun main () {
    val list = File("src/d25/d25.in").readText().split("\n\n").map {
        it.split("\n")
    }
    val keySet = HashSet<List<Int>>()
    val lockSet = HashSet<List<Int>>()
    println(list.size)
    for (g in list) {
        val dsc = (g[0].indices).map {c -> g.indices.count {r -> g[r][c] == '#'} - 1}
        if (g[0][0] == '#') {
            lockSet.add(dsc)
        } else {
            keySet.add(dsc)
        }
    }

    println(
        keySet.sumOf { key ->
            lockSet.count { lock ->
                key.zip(lock).all { (k,l) -> k+l<6 }
            }
        }
    )
}