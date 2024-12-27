package d09

import java.io.File
import java.util.PriorityQueue
import kotlin.time.measureTime

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
fun q1 () {
    val nums = File("src/d09/d09.in").readText().toCharArray().map { it.digitToInt() }.toMutableList()

    var diskIndex = 0L
    var checkSum = 0L
    fun writeBit(fileID:Int) {
        checkSum += fileID*diskIndex
        diskIndex += 1
    }

    var writePos = 0
    var readPos = nums.lastIndex
    var writeSz = nums[writePos]
    var readSz = nums[readPos]

    while (true) {
        if (writePos > readPos) break

        if (writePos%2 == 0) {
            (0..<writeSz).forEach { writeBit(writePos/2) }

            writePos += 1
            writeSz = nums[writePos]
        } else if (readPos%2 == 1) {
            readPos -= 1
            readSz = nums[readPos]
        } else if (readSz <= writeSz) {
            (0..<readSz).forEach {writeBit(readPos/2)}
            writeSz -= readSz
            nums[readPos]=0

            readPos -= 2
            readSz = nums[readPos]
            if (writeSz == 0) {
                writePos += 1
                writeSz = nums[writePos]
            }
        } else {
            (0..<writeSz).forEach {writeBit(readPos/2)}
            readSz -= writeSz
            nums[readPos]=readSz

            writePos += 1
            writeSz = nums[writePos]
        }
    }

    println(checkSum)
}

fun q2 () {
    val nums = File("src/d09/d09.in").readText().toCharArray().map { it.digitToInt() }.toMutableList()
    // add files to set of 10 prio queues to figure what can be moved as iterate
    // over spaces
    val spaceQ = Array(10) {PriorityQueue<Int>(1000)}
    for (i in nums.indices.reversed()) {
        // add spaces to prio qs
        if (i%2 == 1) {
            var sz = nums[i]
            spaceQ[sz].add(i)
        }
    }

    // maps space idx -> files moves there
    val moves = HashMap<Int, ArrayList<Int>>()
    // contains idx of files that have been moved
    val filesMoved = HashSet<Int>()
    for (i in nums.indices.reversed()) {
        if (i%2 == 0) {
            val moveSz = nums[i]
            val spaceSz = spaceQ.indices
                .filter {spaceQ[it].isNotEmpty()}
                .filter {it >= moveSz}
                .minByOrNull {spaceQ[it].peek()}
            if (spaceSz == null) continue

            val toIdx = spaceQ[spaceSz].remove()
            moves.getOrPut(toIdx) {arrayListOf()}.addLast(i)
            filesMoved.add(i)

            // reinsert leftover space
            val spaceLeft = spaceSz-moveSz
            if (spaceLeft > 0)
                spaceQ[spaceLeft].add(toIdx)
        }
    }

    var diskIndex = 0L
    var checkSum = 0L
    fun doCheckSum(fileID:Int, sz:Int) =
        (0..<sz).forEach {checkSum += fileID.toLong()*diskIndex; diskIndex += 1}
    fun doSpace(sz:Int) =
        (0..<sz).forEach {diskIndex += 1}

    for (i in nums.indices) {
        // files
        if (i%2 == 0) {
            if (filesMoved.contains(i)) {
                doSpace(nums[i])
            } else {
                doCheckSum(i/2,nums[i])
            }
        // spaces
        } else {
            var spaceSz = nums[i]
            val movedArr = moves[i]
            movedArr?.forEach {fromIdx ->
                val moveSz = nums[fromIdx]
                doCheckSum(fromIdx/2,moveSz)
                spaceSz -= moveSz
            }
            doSpace(spaceSz)
        }
    }

    println()
    println(checkSum)
}

// s: 00...111...2...333.44.5555.6666.777.888899
// e: 00992111777.44.333....5555.6666.....8888..

// m: 00992111777.44.333....5555.6666.....8888..


fun main () {
    println(measureTime {
        q1()
        q2()
    })
}