import java.io.File


fun main () {
    val grid = File("input/d4")
        .readLines()
        .map { line -> line.toCharArray().toList() }
    val maxRow = grid.size
    val maxCol = grid[0].size

    fun checkDir(word: String, r: Int, c: Int, dr: Int, dc: Int, step: Int): Int {
        return when {
            step == word.length                  -> 1
            r !in 0..<maxRow || c !in 0..<maxCol -> 0
            grid[r][c] != word[step]             -> 0
            else                                 -> checkDir(word, r+dr, c+dc, dr, dc, step+1)
        }}

    val dirs = (-1..1).map { r -> (-1..1).map { c -> Pair(r,c) } }.flatten()
    fun checkXMAS (r: Int, c: Int): Int {
        return dirs.fold(0) { sum, (dr,dc) -> sum + checkDir("XMAS",r,c,dr,dc,0) }
    }

    fun checkX_MAS (r: Int, c: Int): Int {
        return when {
            checkDir("MAS",r+1,c+1,-1,-1, 0)
                    +  checkDir("SAM",r+1,c+1,-1,-1, 0) == 0    -> 0
            checkDir("MAS",r+1,c-1,-1,1, 0)
                    +  checkDir("SAM",r+1,c-1,-1,1, 0) == 0     -> 0
            else                                                -> 1
        }
    }

    fun checkGrid (checkFn: (r:Int,c:Int)->Int): Int {
        return (0..<maxRow).fold(0) { rSum, r ->
            rSum + (0..<maxCol).fold(0) { cSum, c -> cSum + checkFn(r,c) }
        }
    }

    println(checkGrid(::checkXMAS))     //Q1
    println(checkGrid(::checkX_MAS))    //Q2
}