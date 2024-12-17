package d14

import java.io.File

data class XY (var x:Int, var y:Int)
data class Robot (var pos:XY, val vel:XY)
private const val xSize = 101
private const val ySize = 103
fun getRobots() = File("src/d14/d14.in").readLines()
    .map { l -> Regex("-*[0-9]+").findAll(l).map { it.value.toInt() }.toList()}
    .map { Robot(pos=XY(it[0],it[1]), vel=XY(it[2],it[3])) }

fun q1 () {
    val robots = getRobots()
    repeat(100) {
        robots.forEach {
            it.pos.x = (it.pos.x + it.vel.x).mod(xSize)
            it.pos.y = (it.pos.y + it.vel.y).mod(ySize)
        }
    }
    val sums = mutableListOf(0,0,0,0)
    robots.forEach{
        if (it.pos.x < xSize/2) {
            if (it.pos.y < ySize/2)  sums[0] += 1
            if (it.pos.y > ySize/2)  sums[1] += 1
        }
        if (it.pos.x > xSize/2) {
            if (it.pos.y < ySize/2)  sums[2] += 1
            if (it.pos.y > ySize/2)  sums[3] += 1
        }
    }
    println(sums.reduce {a,b -> a*b})
}

fun q2 () {
    val robots = getRobots()
    val grid = List(ySize) { MutableList(xSize) { 0 } }
    robots.forEach { grid[it.pos.y][it.pos.x] += 1 }

    fun find (): Int {
        var time = 0
        repeat(10000) {
            robots.forEach {
                grid[it.pos.y][it.pos.x] -= 1
                it.pos.x = (it.pos.x + it.vel.x).mod(xSize)
                it.pos.y = (it.pos.y + it.vel.y).mod(ySize)
                grid[it.pos.y][it.pos.x] += 1
            }
            time += 1
            (grid[0].indices).forEach { x ->
                var cnt = 0
                (grid.indices).forEach { y ->
                    if (grid[y][x] > 0) cnt++ else cnt=0
                    if (cnt > 26) return time
                }
            }
        }
        return 0
    }
    val t = find()
    if (t > 0)
        grid.forEach { row ->
            println(row.map { if (it==0) '.' else '#' }.joinToString(""))
        }
    println(t)
}

fun main () {
    q1()
    q2()
}
