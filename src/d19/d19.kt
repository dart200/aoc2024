package d19

import java.io.File
import kotlin.math.max
import kotlin.math.min
import kotlin.time.measureTime

fun q2 () {
    val (patterns,designs) = File("src/d19/d19.in")
        .readText().split(Regex("\n\n"))

    val wordSet = HashSet<String>()
    var maxLen = 0
    patterns.split(", ").forEach {
        wordSet.add(it)
        maxLen = max(maxLen, it.length)
    }

    val seenCache = HashMap<String, Long>()
    fun searchCached (s:String): Long {
        if (s.isEmpty()) return 1
        seenCache[s]?.let { return it }
        return (0..<min(maxLen,s.length))
            .sumOf { i ->
                val prefix = s.slice(0..i)
                val rest = s.slice(i+1..s.lastIndex)

                if (!wordSet.contains(prefix)) 0
                else searchCached(rest)
             }
            .also { seenCache[s] = it }
    }

    println(designs.split('\n').sumOf {
        searchCached(it)
    })
}

fun main () {
    println(measureTime { q2() })
}