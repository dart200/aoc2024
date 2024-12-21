package d18

import java.io.File
import kotlin.time.measureTime

const val SPACE = -1
const val BYTE = -2
fun processGrid (
    grid:List<MutableList<Int>>
):Int {
    val cellQ = ArrayDeque<Triple<Int,Int,Int>>().also {
        it.add(Triple(0,0,0))
        grid[0][0] = 0
    }

    fun doNextCell (r:Int, c:Int, nt:Int) {
        if (r !in grid.indices
            || c !in grid[0].indices
            || grid[r][c] != SPACE) return
        cellQ.add(Triple(r,c,nt))
        grid[r][c]=nt
    }

    fun doCell () {
        val (r,c,t) = cellQ.removeFirst()
        doNextCell(r+1,c, t+1)
        doNextCell(r-1,c, t+1)
        doNextCell(r,c+1, t+1)
        doNextCell(r,c-1, t+1)
    }

    while(cellQ.isNotEmpty()) doCell()
    return grid[grid.lastIndex][grid[0].lastIndex]
}

fun q1() {
    val size = 71
    val grid = List(size) {MutableList(size) { SPACE }}
    File("src/d18/d18.in").readLines().forEachIndexed { i, l ->
        if (i < 1024) {
            val (c,r) = l.split(',').map { it.toInt() }
            grid[r][c] = BYTE
        }
    }
    println(processGrid(grid))
}

fun q2() {
    val size = 71
    val grid = List(size) {MutableList(size) { SPACE }}
    File("src/d18/d18.in").readLines().forEach { l ->
        val (c,r) = l.split(',').map { it.toInt() }
        grid[r][c] = BYTE
        val nGrid = grid.map { it.toMutableList() }
        val ret = processGrid(nGrid)
        if (ret < 0) {
            println("$c,$r"); return
        }
    }
}

fun main () {
    println( measureTime { q1() } )
    println( measureTime { q2() } )
}