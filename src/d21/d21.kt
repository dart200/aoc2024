package d21

import java.io.File
import kotlin.math.abs
import kotlin.time.measureTime

fun numpadMap (num: Char) = when(num) {
    '7' -> (0 to 0); '8' -> (0 to 1); '9' -> (0 to 2)
    '4' -> (1 to 0); '5' -> (1 to 1); '6' -> (1 to 2)
    '1' -> (2 to 0); '2' -> (2 to 1); '3' -> (2 to 2)
                     '0' -> (3 to 1); 'A' -> (3 to 2)
    else -> throw Exception("no")
}

fun arrowpadMap (ch:Char) = when (ch) {
                     '^' -> (0 to 1); 'A' -> (0 to 2)
    '<' -> (1 to 0); 'v' -> (1 to 1); '>' -> (1 to 2)
    else -> throw Exception("no")
}

val DEPTH=26

val moveCache = HashMap<Int,Long>()
fun genMove (startChr:Char, endChr:Char, depth:Int):Long
    = moveCache.getOrPut(depth shl 16 xor endChr.code shl 8 xor startChr.code) {
        val map = if (depth==DEPTH) ::numpadMap else ::arrowpadMap

        var (sr,sc)=map(startChr); var (er,ec)=map(endChr)
        var dr = er-sr;            var dc = ec-sc

        var ir = (if (dr > 0) "v" else "^").repeat(abs(dr))
        var ic = (if (dc > 0) ">" else "<").repeat(abs(dc))
        val rFirst = ir+ic+"A"
        val cFirst = ic+ir+"A"

        if (map==::numpadMap) { // avoid space
            if (sc==0 && er==3) return@getOrPut genMoves(cFirst,depth-1)
            if (sr==3 && ec==0) return@getOrPut genMoves(rFirst,depth-1)
        } else if (map==::arrowpadMap){
            if (sc==0 && er==0) return@getOrPut genMoves(cFirst,depth-1)
            if (sr==0 && ec==0) return@getOrPut genMoves(rFirst,depth-1)
        }

        val rfirstGen = genMoves(rFirst,depth-1)
        val cFirstGen = genMoves(cFirst,depth-1)
        return@getOrPut if (rfirstGen <= cFirstGen) rfirstGen else cFirstGen
    }

fun genMoves (output:String, depth:Int):Long =
        if (depth == 0) output.length.toLong()
        else ("A$output").zipWithNext().fold(0L) { instr, (prevChr, chr) ->
            instr + genMove(prevChr, chr, depth)
        }

fun main () {
    println(measureTime{
        println(File("src/d21/d21.in").readLines().sumOf {
            genMoves(it, DEPTH) * Regex("[0-9]+").find(it)?.value?.toLong()!!
        })
    })
}