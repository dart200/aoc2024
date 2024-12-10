import java.io.File

private operator fun Pair<Int, Int>.plus(b: Pair<Int,Int>): Pair<Int, Int> {
    val (a0, a1) = this
    val (b0, b1) = b
    return Pair(a0 + b0, a1 + b1)
}

fun main () {
    val rules = HashMap<Int, ArrayList<Int>>()

    fun parseRule (line:String): Pair<Int,Int> {
        val (key, excludes) = line.split("|").map { s->s.toInt() }.toList()
        rules[key] = rules[key] ?: arrayListOf()
        rules[key]?.add(excludes)
        return Pair(0,0)
    }

    fun handleUpdate (line:String): Pair<Int,Int> {
        val (update, needsFix) = line
            .split(',')
            .map { s -> s.toInt() }
            .fold(Pair(arrayListOf<Int>(), false)) { (update, accFix), num ->
                val fix = rules[num]?.let { rule -> rule.any {it in update} }
                update.add(num)
                Pair(update, accFix || fix == true)
            }

        if (needsFix) update.sortWith { a: Int, b: Int -> when {
            rules[b]?.contains(a) == true -> -1
            rules[a]?.contains(b) == true -> 1
            else -> 0
        }}

        val mid = update[update.size/2]
        return if (needsFix) Pair(0,mid) else Pair(mid,0)
    }

    val total = File("input/d5.test")
        .readLines()
        .fold(Pair(0,0)) { sum, line ->
            when {
            '|' in line -> sum + parseRule(line)
            ',' in line -> sum + handleUpdate(line)
            else        -> sum
        }}

    println(total)
}

// old manual fixup:
//        line.split(',').forEach { s ->
//            val num = s.toInt()
//            val foundIdx = rules[num]?.let {
//                    excludes -> update.indexOfFirst { it in excludes }
//            }
//            if (foundIdx != null && foundIdx > -1) {
//                fixed = true
//                val postSlice = update.slice(foundIdx..<update.size)
//                update.removeIf { it in postSlice }
//                update.add(num)
//                update.addAll(postSlice)
//            } else {
//                update.add(num)
//            }
//        }
