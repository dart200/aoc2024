package d09

import java.io.File
import kotlin.time.measureTime

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
    data class Node (var idx:Int, var sz: Byte)
    var diskIdx = 0
    val nodes = File("src/d09/d09.in").readText().trim().toCharArray().map {
        Node(diskIdx,it.digitToInt().toByte()
            .also {diskIdx += it}
        )
    }
    val freeSpaceArr = nodes.filterIndexed {i,node -> i%2 == 1 && node.sz > 0}

    var checkSum = 0L
    fun doCheckSum(fileID:Long, diskIdx:Int, sz:Byte) {
        checkSum += (0..<sz).sumOf {
            (fileID) * (diskIdx+it)
        }
    }

    for ((fileIdx,node) in nodes.withIndex().reversed()) {
        if (fileIdx.mod(2) == 0) {
            val freeSpc = freeSpaceArr.find {
                it.sz >= node.sz && it.idx < node.idx
            }
            if (freeSpc == null) {
                doCheckSum((fileIdx/2).toLong(),node.idx,node.sz)
            } else {
                doCheckSum((fileIdx/2).toLong(),freeSpc.idx,node.sz)
                freeSpc.sz = freeSpc.sz.minus(node.sz).toByte()
                freeSpc.idx += node.sz
            }
        }
    }

    println(checkSum)
}

fun main () {
    println(measureTime {
        q1()
        q2()
    })
}

//
//    fun doSpace(sz:Byte) =
//        (0..<sz).forEach {diskIdx += 1}
//
//    for ((i,nodeSz) in nodes.withIndex()) {
//        var diskIdxPrev = diskIdx
//        var szLeft = nodeSz - writes[i]?.sumOf {fromIdx ->
//            val fileSz = nodes[fromIdx]
//            doCheckSum(fromIdx.toLong(),fileSz)
//            fileSz.toInt()
//        }.let { it ?: 0 }
//        diskIdx = diskIdxPrev + nodeSz
//    }


// 10 prio qs tracks idx of spaces for each size
// val freeSpace = Array(10) {PriorityQueue<Int>()}
//         //  freeSpace[nodeSz].add(i)

//            val spaceSz = freeSpace.indices
//                .filter {sz -> freeSpace[sz].isNotEmpty()}
//                .filter {sz -> sz >= nodeSz}
//                .minByOrNull {sz -> freeSpace[sz].peek()}
//            if (spaceSz == null) {
//                writes.put(fileIdx, arrayListOf(fileIdx))
//            } else {
//                val toIdx = freeSpace[spaceSz].remove()
//                if (freeSpace.any {q -> q.contains(toIdx)}) throw Exception("FUCK")
//                writes.getOrPut(toIdx) {arrayListOf()}.addLast(fileIdx)
//
//                // reinsert leftover space
//                val szLeft = spaceSz-nodeSz
//                if (szLeft > 0) {
//                    freeSpace[szLeft].add(toIdx)
//                    if (freeSpace.count { q -> q.contains(toIdx)} != 1) throw Exception("FUCK")
//                }
//            }