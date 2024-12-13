import java.io.File
import kotlin.math.round

fun main () {
    data class Prob(val ax:Double, val ay:Double, val bx:Double, val by:Double, val dstX:Double, val dstY:Double)
    val inc = 10000000000000
    println(File("input/d13.txt").readText().split("\n\n").map { gr ->
        val lines = gr.split('\n').map { Regex("[0-9]+")
            .findAll(it).map { match -> match.value.toDouble() }.toList() }.flatten()
        Prob(lines[0], lines[1], lines[2], lines[3], lines[4]+inc, lines[5]+inc)
    }.sumOf { p ->
        // a * ax + b * bx = cx
        // a + ay + b * by = cy
        val (ax,ay,bx,by,dstX,dstY) = p
        val a = round((dstX - bx*dstY/by) / (ax - ay*bx/by))
        val b = round((dstY - a*ay) / by)
        val t = a*ax + b*bx == dstX
                && a*ay + b*by == dstY
        if (t) a.toLong() * 3 + b.toLong() else 0
    })
}