fun main () {
    val l = listOf<Int>()
    println(l.all { it == 0 })
    println(l.any { it == 0 })
}