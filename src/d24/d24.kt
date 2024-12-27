package d24

import java.io.File
import kotlin.collections.ArrayDeque

fun q1 () {
    val wireVals = HashMap<String, Int>()
    val (wires, gates) = File("src/d24/d24.in")
        .readText().split("\n\n").map {l -> l.split("\n")}
    for (w in wires) {
        val (name,v) = w.split(": ")
        wireVals[name] = v.toInt()
    }
    val gateQ = ArrayDeque<String>(gates)
    while (gateQ.isNotEmpty()) {
        val gate = gateQ.removeFirst()
        val (in0,op,in1,_,out) = gate.split(" ")
        val vIn0 = wireVals[in0]
        val vIn1 = wireVals[in1]
        if (vIn0 == null || vIn1 == null) {
            gateQ.addLast(gate)
            continue
        }
        when (op) {
            "AND" -> wireVals[out] = vIn0 and vIn1
            "OR"  -> wireVals[out] = vIn0 or vIn1
            "XOR" -> wireVals[out] = vIn0 xor vIn1
            else  -> throw Exception("panic")
        }
    }
    println(wireVals.entries
        .filter { it.key.contains('z') }
        .sortedBy { it.key }
        .reversed()
        .joinToString("") { it.value.toString() }
        .toLong(2))
}

// z0 = x0 XOR y0
//   c1 = x0 AND y0
// z1 = (x1 XOR y1) XOR rem0
//   c2 = ...

// full adder
// xN XOR yN => aN
// aN XOR rN-1 => zN

// xN AND yN => bN
// aN AND rN => cN

// bN OR nC => rN

fun q2 () {
    val (_, gates) = File("src/d24/d24.in")
        .readText().split("\n\n").map {l -> l.split("\n")}
    val gateMap = HashMap<String,String>()
    gates.forEach {
        val (gate,out) = it.split(" -> ")
        gateMap[out] = gate
    }
    val errs = HashSet<String>()
    gateMap
        .filter {it.key.matches(Regex("z[0-9]+"))}.toList()
        .forEach {
            val (out,gate) = it
            if (!gate.contains("XOR") && (!out.contains("45"))) {
                errs.add(out)
            }
        }

    val xorList =  gateMap.filter {it.value.contains("XOR")}.toList()
    gateMap
        .filter {it.value.contains(Regex("[x|y][0-9]+ XOR"))}
        .entries
        .forEach {xorG ->
            if (!xorG.value.contains("00")) {
                val xorG2 = xorList.find {it.second.contains(xorG.key)}
                if (xorG2 == null) {
                    errs.add(xorG.key)
                } else {
                    val (zN, _) = xorG2
                    if (!zN.contains(Regex("z[0-9]+")))
                        errs.add(zN)
                }
            }
        }

    val orList =  gateMap.filter {it.value.contains(" OR ")}.toList()
    gateMap
        .filter {it.value.contains(Regex("[x|y][0-9]+ AND"))}
        .entries
        .forEach {andG ->
            if (!andG.value.contains("00")) {
                val orGate = orList.find {it.second.contains(andG.key)}
                if (orGate == null)
                    errs.add(andG.key)
            }
        }

    println(errs.sorted().joinToString(","))
}

fun main () {
    q2()
}