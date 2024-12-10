import java.io.File

fun main () {
    println(Regex("mul\\(([0-9]{1,3}),([0-9]{1,3})\\)|don't\\(\\)|do\\(\\)")
        .findAll(File("input/d3").readText())
        .fold(Triple(0, 0, true)) { (total, sum, enabled), m ->
            val gv = m.groupValues;
            val mul = if (gv[0].contains("mul")) gv[1].toInt() * gv[2].toInt() else 0
            when {
                gv[0] == "do()"    -> Triple(mul + total, sum, true)
                gv[0] == "don't()" -> Triple(mul + total, sum, false)
                !enabled           -> Triple(mul + total, sum, false)
                else               -> Triple(mul + total, mul + sum , true)
            }
        })
}
