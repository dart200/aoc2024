package d09//class INode (
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
//    val nums = File("input/d09.in.test").readText().toCharArray().map { it.digitToInt() }
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
//fun d18.main () {
//    val nums = File("input/d09.in.test").readText().toCharArray().map { it.digitToInt() }
//
//
//}