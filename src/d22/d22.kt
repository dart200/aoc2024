package d22

import java.io.File
import kotlin.mod
import kotlin.time.measureTime

const val MOD = 16777216L
fun genNext (num:Long):Long {
    val s1 = ((num shl 6) xor num).mod(MOD)
    val s2 = ((s1 shr 5) xor s1).mod(MOD)
    return ((s2 shl 11) xor s2).mod(MOD)
}

fun getNth (num:Long, n:Int)
    = (0..<n).fold(num) {nth,_ -> genNext(nth)}
fun q1 () {
    println(File("src/d22/d22.in").readLines().sumOf { l ->
        getNth(l.trim().toLong(), 2000)
    })
}

val totals = HashMap<Int, Long>()
fun doBuyer (num:Long, n:Int):Long {
    val seenKeys = HashSet<Int>()
    var cur = num; var prev = num; var curKey = 0
    for (i in 0..<n) {
        prev = cur
        cur = genNext(cur)
        val prevPrice = prev.mod(10)
        val curPrice = cur.mod(10)
        val dPrice = curPrice-prevPrice
        curKey = (curKey shl 4) or (dPrice and 15) and 65535
        if ( i >= 3 && !seenKeys.contains(curKey)) {
            totals[curKey] = totals.getOrPut(curKey) {0L} + curPrice
            seenKeys.add(curKey)
        }
    }
    return cur
}
fun q2 () {
    File("src/d22/d22.in").readLines().forEach {doBuyer(it.trim().toLong(), 2000)}
    println(totals.entries.maxByOrNull { it.value })
}


//fun getKeyList (key:Int) =
//    (0..<4).reversed().map{i -> key.shr(i*8).toByte()}
//fun qtest2() {
//    totals.clear()
//    File("src/d22/d22.test2").readLines().forEach {
//        doBuyer(it.trim().toLong(), 2000)
//    }
//    val maxEnt = totals.entries.maxByOrNull { it.value }!!
//    println(getKeyList(maxEnt.key))
//    println(totals.entries.maxByOrNull { it.value })
//}
//fun qtest3() {
//    totals.clear()
//    File("src/d22/d22.test3").readLines().forEach {
//        doBuyer(it.trim().toLong(), 100)
//    }
//    val maxEnt = totals.entries.maxByOrNull { it.value }!!
//    println(getKeyList(maxEnt.key))
//    println(totals.entries.maxByOrNull { it.value })
//}

fun main () {
//    qtest2()
    q1()
    println(measureTime { q2() })
}