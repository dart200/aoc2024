package d12

import java.io.File
import kotlin.time.measureTime

private operator fun Pair<Int, Int>.plus(b: Pair<Int,Int>): Pair<Int, Int> {
    val (a0, a1) = this
    val (b0, b1) = b
    return Pair(a0 + b0, a1 + b1)
}

private typealias Point = Pair<Int,Int>
private typealias Dir = Pair<Int,Int>
private typealias Vec = Pair<Dir, Point>
private fun solve (): Pair<Int,Int> {
    val dir4 = listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)
    val grid = File("src/d12/d12.in").readLines().map { it.toMutableList() }

    val plotQueue = hashSetOf(0 to 0)
    fun bfsPlot (cell0: Point): Pair<Int,Int> {
        val cellQueue = hashSetOf(cell0)
        val plotChar = grid[cell0.first][cell0.second]
        val doneChar = plotChar.lowercaseChar()

        val edgeSet = HashSet<Vec>()
        fun checkEdge (dir: Dir, r:Int, c:Int): Int {
            val checkDirs =
                if (dir.first == 0) dir4.filter { it.second == 0 }
                else dir4.filter { it.first == 0 }
            val connectedEdges = checkDirs.count {
                (dr,dc) -> edgeSet.contains(Pair(dir, dr+r to dc+c))
            }
            edgeSet.add(Pair(dir,r to c))
            return when(connectedEdges) {
                0    -> 1
                1    -> 0
                else -> -1
            }
        }

        fun checkAdjacent(dir: Dir, r: Int, c: Int) = when {
            r !in grid.indices
              || c !in grid[0].indices  -> 1 to checkEdge(dir, r, c)
            grid[r][c] == doneChar      -> 0 to 0
            grid[r][c] == plotChar      -> { cellQueue.add(r to c); 0 to 0 }
            else                        -> {
                if (!grid[r][c].isLowerCase()) plotQueue.add(r to c)
                1 to checkEdge(dir, r, c)
            }
        }

        var vol = 0; var edges = Pair(0, 0)
        do {
            val cell = cellQueue.random()
            cellQueue.remove(cell)
            plotQueue.remove(cell)
            val (cr, cc) = cell
            edges += dir4.fold(0 to 0) { pairSum, dir ->
                val (dr, dc) = dir
                val res = checkAdjacent(dir, cr+dr, cc+dc)
                pairSum + res
            }
            vol += 1
            grid[cr][cc] = doneChar
        } while (cellQueue.size > 0)

        return Pair(vol*edges.first, vol*edges.second)
    }

    var tots = Pair(0,0)
    do { tots += bfsPlot(plotQueue.random()) } while (plotQueue.size > 0)
    return tots;
}

fun main () {
    var tots = Pair(0,0);
    val dur = measureTime {
        tots = solve()
        println(tots)
    }
    println(dur)
}