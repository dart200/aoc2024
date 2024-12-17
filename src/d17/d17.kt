package d17

import java.io.File
import kotlin.math.pow

fun execute (_regA:Long, _regB:Long, _regC:Long, iArr:List<Int>): List<Int> {
    var regA = _regA; var regB = _regB; var regC = _regC
    var iPtr = 0
    val oArr = ArrayList<Int>()

    while (true) {
        if (iPtr !in iArr.indices) break
        val op = iArr[iPtr]
        val opr = iArr[iPtr+1]
        val combo = when(opr) {
            in 0..3 -> opr.toLong()
            4       -> regA
            5       -> regB
            6       -> regC
            else    -> throw Exception("WTF")
        }

        when (op) {
            0 -> regA = (regA / 2.0.pow(combo.toDouble())).toLong()
            1 -> regB = regB xor opr.toLong()
            2 -> regB = combo.mod(8L)
            3 -> if (regA != 0L) { iPtr=opr; continue }
            4 -> regB = regB xor regC
            5 -> oArr.add(combo.mod(8))
            6 -> regB = (regA / 2.0.pow(combo.toDouble())).toLong()
            7 -> regC = (regA / 2.0.pow(combo.toDouble())).toLong()
            else -> throw Exception("WTF")
        }
        iPtr += 2
    }
    return oArr
}

val lines = File("src/d17/d17.in").readText().split(Regex("\n+"))
val stoi = {s:String? -> s!!.trim().toInt()}
val stol = {s:String? -> s!!.trim().toLong()}

fun q1 () {
    println(execute(
        lines[0].split(":")[1].let(stol),
        lines[1].split(":")[1].let(stol),
        lines[2].split(":")[1].let(stol),
        lines[3].split(":")[1].split(",").map(stoi)
    ).joinToString(","))
}

fun q2 () {
    val iArr = lines[3].split(":")[1].split(",").map(stoi)
    val oneItr = iArr.slice(0..<iArr.size-2)
    println(iArr.reversed().fold(0L) { startA, instr ->
        var regA = startA*8
        while (execute(regA, 0,0, oneItr)[0] != instr) regA += 1
        regA
    })
}

fun main () {
    q1()
    q2()
}

// 2, 4,  1, 1,  7, 5,  0, 3,  1, 4,  4, 5,  5, 5,  3, 0

// b = a % 8
// b = b ^ 1
// c = a / 2^b (a >> b)
// a = a / 2^3 (a >> 3)
// b = b ^ 4
// b = b ^ c
// output (b % 8)
// if a!=0 az 0

// b = a % 8
// b = b ^ 1
// c = a / 2^b (a >> b)
// b = b ^ 4
// b = b ^ c
// output (b % 8)
// a = a / 2^3 (a >> 3)
// if a!=0 az 0

// output ((((a % 8) ^ 1) ^ 4) ^ (a >> ( (a % 8) ^ 1) )) % 8
// (a >> 3)