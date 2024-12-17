package d16

import java.io.File
import java.util.ArrayDeque
import kotlin.time.measureTime


private fun moveKey (r:Int, c:Int, dirIdx:Int)
    = dirIdx + (r shl 2) + (c shl 18)

private fun moveKey (move: Move) =
    moveKey(move.r, move.c, move.dirIdx)

private data class Move (
    val r:Int, val c:Int, val dirIdx:Int, var cost:Int, var prevKey:Int, var prevKey2:Int
)

private val dir4 = listOf(0 to 1, -1 to 0, 0 to -1, 1 to 0)

fun solve () {
    val grid = File("src/d16/d16.in").readLines().map { it.toCharArray() }
    val seenMap = HashMap<Int,Move>(1000)
    val moveQueue = ArrayDeque<Move>(1000)

    grid.forEachIndexed {r, row -> row.forEachIndexed {c, cell ->
        if (cell == 'S') {
            val sMove = Move(r, c, 0, 0, 0,0);
            moveQueue.add(sMove)
            seenMap[moveKey(sMove)] = sMove
        }
    }}

    var minCost: Int? = null
    var minMove: Move? = null

    fun checkDir (move: Move, dirMod:Int) {
        val (r,c, dirIdx, cost, _) = move
        val nDir = (dirIdx+dirMod).mod(4)
        val (dr, dc) = dir4[nDir]
        val nr = r+dr; val nc = c+dc
        if (grid[nr][nc] != '#') {
            val nCost = cost + (if (dirMod == 0) 1 else 1001)
            val seen = seenMap[moveKey(nr,nc,nDir)]
            if (seen == null) {
                val nMove = Move(nr,nc,nDir,nCost, moveKey(move), 0)
                moveQueue.add(nMove)
                seenMap[moveKey(nMove)] = nMove
            } else {
                if (seen.r != nr || seen.c != nc || seen.dirIdx != nDir)
                    throw Exception("$nr,$nc,$nDir-$seen")
                else if (seen.cost > nCost) {
                    seen.cost = nCost
                    seen.prevKey = moveKey(move)
                    seen.prevKey2 = 0
                    moveQueue.add(seen)
                } else if (seen.cost == nCost) {
                    seen.prevKey2 = moveKey(move)
                }
            }
        }
    }

    fun nextMove () {
        val move = moveQueue.remove()
        val (r,c, _, cost, _) = move
        if (grid[r][c] == 'E') {
            if (minCost == null || cost < minCost!!) {
                minCost = cost
                minMove = move
            } else if (cost == minCost) {
                throw Exception("FUCK YOU")
            }
            return
        }

        checkDir(move, -1)
        checkDir(move, 0)
        checkDir(move, 1)
    }

    while(moveQueue.isNotEmpty()) nextMove()

    val pathSet = HashSet<Pair<Int, Int>>(1000)
    val pathQueue = ArrayDeque<Int>(1000)
    // count cells in path graph
    minMove?.let { m -> pathQueue.add(moveKey(minMove!!)) }
    while (pathQueue.isNotEmpty()) {
        val moveKey = pathQueue.remove()
        seenMap[moveKey]?.let { move ->
            pathSet.add(move.r to move.c)
//  wish kotlin was smart enough to make this efficient:
//                listOf(move.prevKey, move.prevKey2).forEach {
//                    if (it != 0) pathQueue.add(it)
//                }
            if (move.prevKey != 0)
                pathQueue.add(move.prevKey)
            if (move.prevKey2 != 0)
                pathQueue.add(move.prevKey2)
        }
    }
    println("$minCost, ${pathSet.size}")
}

fun main () {
    println(measureTime { solve() })
}