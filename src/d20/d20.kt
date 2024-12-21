package d20

import java.io.File
import java.util.ArrayDeque
import java.util.ArrayList
import kotlin.math.abs
import kotlin.time.measureTime

val SPACE = -1; val WALL = -2

fun q1 () {
    var er = 0; var ec = 0
    val grid = File("src/d20/d20.in").readLines()
        .mapIndexed { r, row -> row.mapIndexed {c, ch -> when(ch) {
            'E' -> {er = r; ec = c; 0}
            'S' -> SPACE
            '.' -> SPACE
            else -> WALL
        }}.toMutableList()}

    fun checkDir4 (r:Int,c:Int,cost:Int,
                   fn:(Int,Int,Int)->Unit) {
        fn(r+1,c,cost+1)
        fn(r-1,c,cost+1)
        fn(r,c+1,cost+1)
        fn(r,c-1,cost+1)
    }

    val cheats = HashMap<Int,Int>()
    val cellQ = ArrayDeque <Triple<Int,Int,Int>>()
    cellQ.add(Triple(er,ec,0))

    fun search () {
        val (r,c,cost) = cellQ.removeFirst()
        grid[r][c]=cost

        checkDir4(r,c,cost) {nr,nc,nCost -> when (grid[nr][nc]) {
            SPACE -> cellQ.add(Triple(nr,nc,nCost))
            WALL  ->
                checkDir4(nr, nc, nCost) {nnr,nnc,_ ->
                    if (nnr in grid.indices && nnc in grid[0].indices) {
                        val cheatCell = grid[nnr][nnc]
                        if (cheatCell >= 0) {
                            val savings = cost-(cheatCell+2)
                            if (savings > 0)
                                cheats[savings] = cheats.getOrDefault(savings,0)+1
                        }
                    }
                }
            else  -> {}
        }}
    }

    while(cellQ.isNotEmpty()) search()

    println(cheats.entries.filter { it.key >= 100 }.sumOf { it.value })
}

val dir4 = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
fun q2 () {
    var er = 0; var ec = 0
    val grid = File("src/d20/d20.in").readLines()
        .mapIndexed { r, row -> row.mapIndexed {c, ch -> when(ch) {
            'E' -> {er = r; ec = c; 0}
            'S' -> SPACE
            '.' -> SPACE
            else -> WALL
        }}.toIntArray()}.toTypedArray()

    val totalCheats = Array<Int>(grid.lastIndex*grid[0].lastIndex) {0}
    var r = er; var c = ec; var cost = 0
    loop@ while (true) {
        grid[r][c] = cost
        for (pr in r-20 .. r+20) {
            for (pc in c-20 .. c+20) {
                val chCost = abs(r - pr) + abs(c - pc)
                if (chCost == 0 || chCost > 20) continue
                if (pr !in grid.indices || pc !in grid[0].indices) continue

                val pCost = grid[pr][pc]
                if (pCost < 0) continue

                val savings = cost - (pCost + chCost)
                if (savings >= 100)
                    totalCheats[savings] = 1 + totalCheats[savings]
            }
        }
        dir4.forEach {(dr, dc) ->
            val nr=r+dr
            val nc=c+dc
            if (grid[nr][nc] == SPACE) {
                r=nr; c=nc; cost+=1
                continue@loop
            }
        }
        break
    }
    println(totalCheats.sumOf {it})
}

fun main () {
    println(measureTime { q1() })
    println(measureTime { q2() })
}

//        cheatQ.add(Triple(r,c,false))
//
//        fun doCheatCell () {
//            val (cr,cc,isCheat) = cheatQ.removeFirst()
//            println("$cr,$cc,$isCheat")
//            val cCost = abs(r-cr) + abs(c-cc)
//            val cCell = grid[cr][cc]
//            if (isCheat && cCell > 0) {
//                val savings = cost - (cCell+cCost)
//                if (savings > 0) {
//                    totalCheats[savings] = 1+totalCheats.getOrDefault(savings,0)
//                }
//            }
//            if (cCost < 20) {
//                checkDir4(cr,cc,cCost) {ncr,ncc,_ ->
//                    val ncCost = abs(r-ncr) + abs(c-ncc)
//                    if (ncr in grid.indices
//                        && ncc in grid[0].indices
//                        && ncCost > cCost) cheatQ.add(
//                        Triple(ncr, ncc, isCheat || cCell == WALL)
//                    )
//                }
//            }
//        }
//
//        while(cheatQ.isNotEmpty()) doCheatCell()

//    val pathArray = ArrayList<Triple<Int,Int,Int>>()
//fun doPathCell (r:Int,c:Int,cost:Int) {
//        val (r,c,cost) = pathQ.removeFirst()
//    grid[r][c]=cost
//    for (pr in r-20..r+20) {
//        for (pc in c-20..c+20) {
//            val chCost = abs(r - pr) + abs(c - pc)
//            if (chCost == 0 || chCost > 20) continue
//            if (pr !in grid.indices || pc !in grid[0].indices) continue
//
//            val pCost = grid[pr][pc]
//            if (pCost < 0) continue
//
//            val savings = cost - (pCost + chCost)
//            if (savings >= 100)
//                totalCheats[savings] = 1 + totalCheats.getOrDefault(savings, 0)
//        }
//    }
//    dir4.forEach { (dr,dc) ->
//        val nr=r+dr; val nc=c+dc
//        if (grid[nr][nc] == SPACE) pathQ.add(Triple(nr,nc,cost+1))
//    }
//}
//    while(pathQ.isNotEmpty()) doPathCell()
