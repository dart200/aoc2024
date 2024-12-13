import java.io.File

//class INode (
//    var idx:Int,
//    var size:Int,
//    var id:Int,
//    var prev: INode?,
//    var next: INode?
//) {
//
//}
//
//fun buildList () {
//    val nums = File("input/d9.test").readText().toCharArray().map { it.digitToInt() }
//    var idx = 0; var head:INode?=null; var tail:INode?=null
//    for ((i,size) in nums.withIndex()) {
//        val node = INode(idx,size, if (i%2==0) i else -1, tail, null);
//        if (head==null) head = node;
//        if (tail!=null) tail.prev=node;
//        tail=node
//    }
//
//}
//
//fun main () {
//    val nums = File("input/d9.test").readText().toCharArray().map { it.digitToInt() }
//
//
//}