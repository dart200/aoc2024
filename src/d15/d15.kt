package d15

import java.io.File
import kotlin.math.abs

fun q1 () {
    val (grid,instr) = File("src/d15/d15.test3").readText().split("\n\n").toList()
        .let { (gridIn, instrIn) -> Pair(
            gridIn.split("\n").map { it.toCharArray().toMutableList() }.toList(),
            instrIn.filter { it != '\n' }) }
    var posR = grid.indexOfFirst { row -> row.contains('@') }
    var posC = grid[posR].indexOfFirst { it == '@' }

    fun doInstr (r:Int, c:Int, dr:Int, dc:Int): Boolean {
        if (grid[r][c] == '#') return false
        if (grid[r][c] == '.') return true
        val valid = doInstr(r+dr, c+dc, dr, dc)
        if (!valid) return false

        // move item forward
        grid[r+dr][c+dc] = grid[r][c]
        // if robot, leave empty space
        if (grid[r][c] == '@') {
            posR = r+dr
            posC = c+dc
            grid[r][c] = '.'
        }
        return true
    }

    grid.forEach { println(it.joinToString("")) }
    println(instr)

    instr.forEach { when (it) {
        '^' -> doInstr(posR,posC,-1,0)
        '>' -> doInstr(posR,posC,0,1)
        'v' -> doInstr(posR,posC,1,0)
        '<' -> doInstr(posR,posC,0,-1)
    }}

    grid.forEach { println(it.joinToString("")) }

    println(grid.indices.sumOf { r ->
        grid[r].indices.sumOf { c ->
            if (grid[r][c] == 'O') 100*r + c else 0
        }
    })
}

fun q2 () {
    val (grid,instr) = File("src/d15/d15.in").readText().split("\n\n").toList()
        .let {(gridIn, instrIn) -> Pair(
            gridIn.split("\n").map { row -> row.map{when (it) {
                '#' -> "##"
                '.' -> ".."
                'O' -> "[]"
                '@' -> "@."
                else -> "!!"
            }}.joinToString("").toCharArray().toMutableList()},
            instrIn.filter { it != '\n' }) }
    var robR = grid.indexOfFirst { row -> row.contains('@') }
    var robC = grid[robR].indexOfFirst { it == '@' }

    fun doInstr (r:Int, c:Int, dr:Int, dc:Int, searchSet:HashSet<Pair<Int,Int>>): Boolean {
        if (searchSet.contains(r to c)) return true // ignore dups
        val cell = grid[r][c]
        if (cell == '#') return false
        if (cell == '.') return true
        searchSet.add(r to c)

        var valid = doInstr(r+dr, c+dc, dr, dc, searchSet)
        if (dc == 0) {
            if (cell == '[') valid = valid && doInstr(r, c+1, dr, dc, searchSet)
            if (cell == ']') valid = valid && doInstr(r, c-1, dr, dc, searchSet)
        }

        if (cell == '@' && valid) {
            val toMove = searchSet.sortedWith(
                compareByDescending { (mr,mc) -> abs(robR-mr) + abs(robC-mc) })
            toMove.forEach { (mr,mc) ->
                grid[mr+dr][mc+dc] = grid[mr][mc]
                grid[mr][mc] = '.'
            }
            robR = r+dr; robC = c+dc
        }

        return valid
    }

    instr.forEach { when (it) {
        '^' -> doInstr(robR,robC,-1,0, hashSetOf())
        '>' -> doInstr(robR,robC,0,1, hashSetOf())
        'v' -> doInstr(robR,robC,1,0, hashSetOf())
        '<' -> doInstr(robR,robC,0,-1, hashSetOf())
    }}

    println(grid.indices.sumOf { r -> grid[r].indices.sumOf { c ->
        if (grid[r][c] == '[') 100*r + c else 0
    }})
}

fun main() {
    q2()
}