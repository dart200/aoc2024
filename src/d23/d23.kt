package d23

import java.io.File
import kotlin.time.measureTime

val connMap = HashMap<String, HashSet<String>>()
fun q1 () {
    File("src/d23/d23.test").readLines().forEach {
        var (cl,cr) = it.split("-")
        connMap.getOrPut(cl) {hashSetOf()}.add(cr)
        connMap.getOrPut(cr) {hashSetOf()}.add(cl)
    }

    val foundGroups = HashSet<String>()
    val seenComps = HashSet<String>()
    connMap.entries.forEach {(c0, conns) ->
        val connList = conns.toList()
        for (c1i in connList.indices) {
            val c1 = connList[c1i]
            if (seenComps.contains(c1)) continue
            // check rest to see if they contain c1, forming a loop
            for (c2i in c1i+1..connList.lastIndex) {
                val c2 = connList[c2i]
                if (seenComps.contains(c2)) continue

                if (connMap[c2]!!.contains(c1)) {
                    foundGroups.add(listOf(c0,c1,c2).sorted().joinToString(","))
                }
            }
        }
        seenComps.add(c0)
    }

    val filtered = foundGroups.filter {Regex("t[a-z]").containsMatchIn(it)}
    println(filtered)
    println(filtered.size)
}

fun q2 () {
    File("src/d23/d23.in").readLines().forEach {
        var (cl,cr) = it.split("-")
        connMap.getOrPut(cl) {hashSetOf()}.add(cr)
        connMap.getOrPut(cr) {hashSetOf()}.add(cl)
    }
    val seenComps = HashSet<String>()
    val maxClique = connMap.entries
        .map {(c0, conns) ->
            val cliques = ArrayList<ArrayList<String>>()
            conns.filter {!seenComps.contains(it)}
                .forEach {c ->
                    val cli = cliques.find {cl -> cl.all{connMap[it]!!.contains(c)}}
                        ?.add(c)
                    if (cli == null)
                        cliques.add(arrayListOf(c))
                }
            seenComps.add(c0)
            cliques.maxByOrNull {it.size}?.also {it.add(c0)}
        }.maxByOrNull { it?.size ?: 0 }

    println(maxClique?.sorted()?.joinToString(","))
}

fun main () {
    println(measureTime { q2() })
}