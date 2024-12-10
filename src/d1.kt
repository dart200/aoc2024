import java.io.File
import kotlin.math.abs

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val lines: List<String> = File("input/d1").readLines()
    val arr0 = ArrayList<Int>()
    val arr1 = ArrayList<Int>()

    lines.forEach { line ->
        val (num1, num2) = line
            .split("   ")
            .map { str -> str.toInt() }
        arr0.add(num1)
        arr1.add(num2)
    }

    arr0.sort()
    arr1.sort()

    var totalDiff = 0
    for ((num0, num1) in arr0.zip(arr1)) {
        totalDiff += abs(num0-num1)

        println("$num0-$num1")
    }
    println("Q1: $totalDiff")

    var score = 0; var i0 = 0; var i1 = 0; var reps1 = 0;
    while (i0 < arr0.size && i1 < arr1.size) {
        val num0 = arr0[i0]
        val num1 = arr1[i1]

        if (num0 == num1) {
            reps1 += 1
            i1 += 1
        } else if (num0 < num1) {
            var reps0 = 0
            do {
                reps0 += 1
                i0 += 1
            } while (i0 < arr0.size && arr0[i0] == num0)

            score += (num0 * reps0 * reps1)
            reps1 = 0
        } else { // num0 > num1
            i1 += 1
        }

        println("$num0-$num1, score: $score")
    }
    // cover if i1 iterates off the end while counting reps
    if (i0 < arr0.size)
        score += arr0[i0] * reps1

    println ("Q2: $score")
}