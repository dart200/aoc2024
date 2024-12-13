package d10

import java.io.File
import kotlin.time.measureTime

val dir4 = listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)
typealias Pairs = MutableCollection<Pair<Int,Int>>

fun solve (): Pair<Int,Int> {
    val grid = File("src/d10/d10").readLines().map { it.toCharArray()
        .map { c -> c.digitToInt() } }
    val rRange = grid.indices; val cRange = grid[0].indices

    fun recurse (r:Int,c:Int,v:Int,set: Pairs): Pairs {
        if (r in rRange && c in cRange && v == grid[r][c]) {
            if (v == 9)
                set.add(r to c)
            else
                dir4.forEach { (dr,dc) -> recurse(r+dr, c+dc, v+1, set) }
        }
        return set
    }

    fun fold (newSetFn:()-> Pairs) = rRange.sumOf { r ->
        cRange.sumOf { c -> recurse(r,c,0,newSetFn()).size }
    }
    return Pair(fold { HashSet() }, fold { ArrayList() });
}

fun main () {
    var ans: Pair<Int,Int>
    var duration = measureTime {
        ans = solve()
    }
    println(ans)
    println(duration)
}