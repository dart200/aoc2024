import java.io.File
import java.util.LinkedList
import java.util.TreeSet
import kotlin.time.measureTime

//fun printList (list:LinkedList<Node>) {
//    println(list.joinToString("") { n ->
//        if (n.size == 0 || n.id < 0) ""
//        else n.id.toString().repeat(n.size)
//    })
//}
//
//fun checkSum (list:LinkedList<Node>) =list.fold(Pair(0, 0L)) { (i, sum), n ->
//    if (n.size == 0 || n.id < 0) Pair(i, sum)
//    else Pair(i + n.size, sum + (i*n.size + n.size*(n.size-1)/2) * n.id)
//}
//
//fun buildList (): LinkedList<Node> {
//    val list = LinkedList<Node>()
//    for ((i, c) in File("input/d9").readText().withIndex()) {
//        if (i % 2 == 0) list.add(Node(i / 2, c.digitToInt()))   // file type
//        else list.add(Node(-1, c.digitToInt()))                 // space type
//    }
//    return list
//}
//
//data class Node (var id: Int = -1, var size: Int)
//fun q1 () {
//    val list = buildList()
//    var to = 0; var from = list.lastIndex
//    while (to < from) {
//        if (list[to].id >= 0 || list[to].size == 0) to += 1
//        else if (list[from].id < 0 || list[from].size == 0) from -= 1
//        else if (list[to].size >= list[from].size) {
//            list[to].size -= list[from].size
//            list.add(to, Node(list[from].id, list[from].size))
//            list[from+1].size = 0
//        } else if (list[to].size < list[from].size) {
//            list[to].id = list[from].id
//            list[from].size -= list[to].size
//        }
//    }
//    println(checkSum(list))
//}
//
//fun q2 () {
//    val list = buildList()
//    val emptySpaces = List(10) { TreeSet<MutableListIterator<Node>>(
//        {o1,o2 -> o2.nextIndex()-o1.nextIndex()}
//    )}
//    for ((i,n) in list.withIndex()) {
//        if (n.id < 0) emptySpaces[n.size].add(list.listIterator(i))
//    }
//    var from = list.lastIndex
//    while (from > 0) {
//        val nf = list[from] // nodeFrom
//        if (nf.id < 0 || nf.size == 0) {
//            from -= 1; continue
//        }
//        val nti = (nf.size..9).find { i -> emptySpaces[i].size > 0 } // nodeToIterator
//            .let { toSet -> if (toSet!=null) emptySpaces[toSet].pollFirst() else null }
//        if (nti == null) {
//            from -= 1; continue
//        }
//        val nt = list[nti.nextIndex()]
//        nt.size -= nf.size
//        if (nt.size > 0)
//            emptySpaces[nt.size].add(nti)
//        nti.add(Node(nf.id, nf.size))
//    }
//    println(checkSum(list))
//}
//
//fun main () {
//    val dur1 = measureTime { q1() }
//    println(dur1)
//    val dur2 = measureTime { q2() }
//    println(dur2)
//}