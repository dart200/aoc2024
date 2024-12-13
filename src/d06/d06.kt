package d06

import java.io.File

data class State(val r: Int, val c: Int, val dir: Int)

fun main () {
    val grid = File("src/d06/d06.in").readLines().map {
        line -> line.toCharArray().toList()
    }
    var rStart=0; var cStart=0
    grid.forEachIndexed { ri, row -> row.forEachIndexed { ci, cell ->
        if (cell == '^') { rStart = ri; cStart = ci }
    }}
    val objs = List(grid.size) { MutableList(grid[0].size) { 0 } }

    fun simulate (simGrid: List<MutableList<Char>>,
                  stateIn: State,
                  findLoop:Boolean): Pair<Int,Boolean> {
        var steps = 0
        val seen = HashSet<String>()
        var (r,c,dir) = stateIn
        while(true) {
            val k = "$r-$c-$dir"
            if (seen.contains(k)) return Pair(steps, true)
            else seen.add(k)

            // next step
            val (rn, cn) = when (dir) {
                0 -> Pair(r - 1, c)
                1 -> Pair(r, c + 1)
                2 -> Pair(r + 1, c)
                else -> Pair(r, c - 1)
            }

            if (simGrid[r][c] != '+') {
                steps += 1
                simGrid[r][c] = '+'
            }

            // exiting grid
            if (rn !in simGrid.indices || cn !in simGrid[0].indices) {
                return Pair(steps, false)
            }

            // if hit object, rotate
            if (simGrid[rn][cn] == '#') {
                dir = (dir+1)%4
                continue
            }

            // try placing object and checking for loop
            if (!findLoop) {
                if (simGrid[rn][cn] != '+') {
                    val loopGrid = grid.map { it.toMutableList() }
                    loopGrid[rn][cn] = '#'
                    val (_, foundLoop) = simulate(loopGrid, State(r, c, dir), true)
                    if (foundLoop) objs[rn][cn] = 1
                }
            }
            r = rn; c = cn
        }
    }
    val resGrid = grid.map { it.toMutableList() }
    val (steps,_) = simulate(resGrid, State(rStart, cStart, 0), false)
    val loops = objs.flatten().fold(0) {sum, cell -> sum+cell}
    println("$steps,${loops}")

    resGrid.forEachIndexed() { r, row ->
        println(row.mapIndexed{c, cell ->
            if (cell == '^') cell
            else if (objs[r][c] == 1) '0'
            else cell
        }.joinToString(""))
    }
}

