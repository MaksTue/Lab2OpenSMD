package mmcs.assignment2

fun main() {

    // Тесты
    val matrixToRotate = createMatrix(3, 3, 0)
    for (i in 0 until 3) {
        for (j in 0 until 3) {
            matrixToRotate[i, j] = i * 3 + j + 1
        }
    }
    println("Original matrix:")
    println(matrixToRotate)

    val rotatedMatrix = rotate(matrixToRotate)
    println("Rotated matrix:")
    println(rotatedMatrix)

    val sumMatrix = rotatedMatrix + matrixToRotate
    println("Sum matrix:")
    println(sumMatrix)

    val invertedMatrix = -sumMatrix
    println("Inverted matrix:")
    println(invertedMatrix)

    val productMatrix = matrixToRotate *rotatedMatrix
    println("Product matrix:")
    println(productMatrix)

    // Тесты для функции findHoles
    val matrix1 = createMatrix(5, 4, 0)
    matrix1[0, 1] = 1
    matrix1[1, 2] = 1
    matrix1[2, 0] = 1
    matrix1[3, 2] = 1
    val holes1 = findHoles(matrix1)
    println("Matrix with holes")
    println(matrix1)
    println("Holes for matrix1: $holes1")

    // Тесты для функции canOpenLock
    val key1 = createMatrix(2, 2, 1)
    key1[1,0] = 0

    val lock1 = createMatrix(3, 3, 0)

    lock1[0, 0] = 1
    lock1[1, 1] = 1
    lock1[2, 2] = 1
    println("lock")
    println(lock1)
    println("key")
    println(key1)
    val result1 = canOpenLock(key1, lock1)
    println("Can open lock for key1 and lock1: $result1")

    val key2 = createMatrix(2, 2, 0)
    key2[1, 0] = 1
    key2[0, 1] = 1
    val lock2 = createMatrix(2, 2, 0)
    lock2[0, 0] = 1
    lock2[1, 1] = 1
    println("lock")
    println(lock2)
    println("key")
    println(key2)
    val result2 = canOpenLock(key2, lock2)
    println("Can open lock for key2 and lock2: $result2")
}
