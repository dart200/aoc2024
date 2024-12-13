package d11

import java.io.File
import java.util.*

//fun main () {
//    val list = LinkedList(File("input/d11.in").readText().split(" "))
//    val doItr = {
//        val itr = list.listIterator()
//        while (itr.hasNext()) {
//            val num = itr.next()
//            if (num == "0") {
//                itr.set("1")
//            } else if (num.length % 2 == 0) {
//                itr.set(num.substring(0..<num.length / 2))
//                itr.add(num.substring(num.length / 2..<num.length).toLong().toString())
//            } else {
//                itr.set((num.toLong() * 2024L).toString())
//            }
//        }
//    }
//    for(i in 0..74) {
//        var dur = measureTime { doItr() }
//        println("$i - $dur")
//    }
//    println(list.size)
//}

val dict = HashMap<String,Long>()
fun recurse (num:String,itrs:Int):Long =
    dict.getOrPut("$num-$itrs") { when {
        itrs == 0           -> 1
        num == "0"          -> recurse("1", itrs-1)
        num.length % 2 == 0 ->
            recurse(num.substring(0..<num.length / 2), itrs-1) +
                recurse(num.substring(num.length / 2..<num.length).toLong().toString(), itrs-1)
        else                -> recurse((num.toLong()*2024L).toString(), itrs-1)
    }}

fun main () {
    val list = File("src/d11/d11.in").readText().split(" ")
    println(list.sumOf { s -> recurse(s,75) })
}



