import java.io.File

fun main () {
    fun recurse (tot:Long, list:List<Long>):Boolean {
        val last = list.last()
        if (list.size == 1) return last == tot
        val rest = list.slice(0..list.size-2)
        return recurse(tot-last, rest)
                || (tot%last==0L && recurse(tot/last, rest))
                || tot.toString().let {
                       val reg = Regex("$last$")
                       it.contains(reg) && recurse(it.replace(reg,"").toLong(), rest)
                   }
    }

    println(File("input/d7").readLines().fold(0L) { sum, line ->
        val nums = Regex("[0-9]+").findAll(line).map { it.value.toLong() }.toList()
        val tot = nums[0]; val list = nums.slice(1..<nums.size);
        if (recurse(tot,list)) sum + tot else sum
    })
}

// -> 1 | 2 | 3
// -> 1 | 2 + 3
// -> 1 | 2 * 3
// -> 1 + 2 | 3
// -> 1 + 2 + 3
// -> 1 + 2 * 3
// -> 1 * 2 | 3
// -> 1 * 2 + 3
// -> 1 * 2 * 3
