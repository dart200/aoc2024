import java.io.File

fun safe (arr: List<Int>): Boolean {
    val posDir = arr[1] - arr[0] > 0
    return arr.zipWithNext().fold(true) { safe, (pre, next) ->
        if (!safe) false
        else (posDir && next-pre in 1..3)
            || (!posDir && pre-next in 1..3)
    }
}

fun safe2 (line: List<Int>): Boolean{
    safe(line) && return true
    for (i in line.indices) {
        val arr = ArrayList(line);
        arr.removeAt(i);
        safe(arr) && return true
    }
    return false;
}

fun main () {
    val lines = File("input/d2").readLines().map {
        line -> line.split(' ').map { str -> str.trim().toInt() }
    }
    println(lines.fold(0) { cnt, line ->
        if (safe(line)) cnt+1 else cnt
    })
    println(lines.fold(0) { cnt, line ->
       if (safe2(line)) cnt+1 else cnt
    })
}