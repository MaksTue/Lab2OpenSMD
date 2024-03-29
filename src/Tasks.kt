@file:Suppress("UNUSED_PARAMETER")

package mmcs.assignment2

import mmcs.assignment2.Matrix
import mmcs.assignment2.createMatrix

/**
 * Пример
 *
 * Транспонировать заданную матрицу matrix.
 */
fun <E> transpose(matrix: Matrix<E>): Matrix<E> {
    if (matrix.width < 1 || matrix.height < 1) return matrix
    val result = createMatrix(height = matrix.width, width = matrix.height, e = matrix[0, 0])
    for (i in 0 until matrix.width) {
        for (j in 0 until matrix.height) {
            result[i, j] = matrix[j, i]
        }
    }
    return result
}

fun <E> rotate(matrix: Matrix<E>): Matrix<E> {
    if (matrix.height != matrix.width) {
        throw IllegalArgumentException("Matrix must be square to rotate.")
    }
    val resultMatrix = createMatrix(matrix.width, matrix.height, matrix[0, 0])


    for (row in 0 until matrix.height) {
        for (col in 0 until matrix.width) {
            val rotatedRow = col // Индекс строки в новой матрице
            val rotatedColumn = matrix.height - row - 1 // Индекс столбца в новой матрице
            resultMatrix[rotatedRow, rotatedColumn] = matrix[row, col] // Заполняем новую матрицу
        }
    }

    return resultMatrix
}

/**
 * Сложить две заданные матрицы друг с другом.
 * Складывать можно только матрицы совпадающего размера -- в противном случае бросить IllegalArgumentException.
 * При сложении попарно складываются соответствующие элементы матриц
 */
operator fun Matrix<Int>.plus(other: Matrix<Int>): Matrix<Int> {
    // Проверяем, что размеры матриц совпадают
    if (this.height != other.height || this.width != other.width) {
        throw IllegalArgumentException("Matrices must have the same dimensions for addition.")
    }

    // Создаем новую матрицу для хранения результата
    val resultMatrix = createMatrix(this.height, this.width, 0)

    // Выполняем поэлементное сложение
    for (row in 0 until this.height) {
        for (col in 0 until this.width) {
            val sum = this[row, col] + other[row, col]
            resultMatrix[row, col] = sum
        }
    }

    return resultMatrix
}

/**
 * Инвертировать заданную матрицу.
 * При инвертировании знак каждого элемента матрицы следует заменить на обратный
 */
operator fun Matrix<Int>.unaryMinus(): Matrix<Int> {
    val resultMatrix = createMatrix(height, width, 0) // Создаем новую матрицу с нулевыми элементами

    for (i in 0 until height) {
        for (j in 0 until width) {
            resultMatrix[i, j] = -this[i, j] // Инвертируем знак элемента исходной матрицы и записываем в новую матрицу
        }
    }

    return resultMatrix
}

/**
 * Перемножить две заданные матрицы друг с другом.
 * Матрицы можно умножать, только если ширина первой матрицы совпадает с высотой второй матрицы.
 * В противном случае бросить IllegalArgumentException.
 * Подробно про порядок умножения см. статью Википедии "Умножение матриц".
 */
operator fun Matrix<Int>.times(other: Matrix<Int>): Matrix<Int> {
    // Проверяем, совпадают ли размеры матриц для умножения
    if (width != other.height) {
        throw IllegalArgumentException("Width of the first matrix must match height of the second matrix for multiplication.")
    }

    val resultMatrix = createMatrix(height, other.width, 0)

    // Выполняем умножение матриц
    for (row in 0 until height) {
        for (col in 0 until other.width) {
            var sum = 0
            for (k in 0 until width) {
                sum += this[row, k] * other[k, col]
            }
            resultMatrix[row, col] = sum
        }
    }

    return resultMatrix
}


/**
 * Целочисленная матрица matrix состоит из "дырок" (на их месте стоит 0) и "кирпичей" (на их месте стоит 1).
 * Найти в этой матрице все ряды и колонки, целиком состоящие из "дырок".
 * Результат вернуть в виде Holes(rows = список дырчатых рядов, columns = список дырчатых колонок).
 * Ряды и колонки нумеруются с нуля. Любой из спискоов rows / columns может оказаться пустым.
 *
 * Пример для матрицы 5 х 4:
 * 1 0 1 0
 * 0 0 1 0
 * 1 0 0 0 ==> результат: Holes(rows = listOf(4), columns = listOf(1, 3)): 4-й ряд, 1-я и 3-я колонки
 * 0 0 1 0
 * 0 0 0 0
 */
fun findHoles(matrix: Matrix<Int>): Holes {
    val rowsWithHoles = mutableListOf<Int>()
    val columnsWithHoles = mutableListOf<Int>()

    for (row in 0 until matrix.height) {
        var hasHole = true
        for (column in 0 until matrix.width) {
            if (matrix[row, column] != 0) {
                hasHole = false
                break
            }
        }
        if (hasHole) {
            rowsWithHoles.add(row)
        }
    }

    for (column in 0 until matrix.width) {
        var hasHole = true
        for (row in 0 until matrix.height) {
            if (matrix[row, column] != 0) {
                hasHole = false
                break
            }
        }
        if (hasHole) {
            columnsWithHoles.add(column)
        }
    }

    return Holes(rowsWithHoles, columnsWithHoles)
}

/**
 * Класс для описания местонахождения "дырок" в матрице
 */
data class Holes(val rows: List<Int>, val columns: List<Int>)

/**
 * Даны мозаичные изображения замочной скважины и ключа. Пройдет ли ключ в скважину?
 * То есть даны две матрицы key и lock, key.height <= lock.height, key.width <= lock.width, состоящие из нулей и единиц.
 *
 * Проверить, можно ли наложить матрицу key на матрицу lock (без поворота, разрешается только сдвиг) так,
 * чтобы каждой единице в матрице lock (штырь) соответствовал ноль в матрице key (прорезь),
 * а каждому нулю в матрице lock (дырка) соответствовала, наоборот, единица в матрице key (штырь).
 * Ключ при сдвиге не может выходить за пределы замка.
 *
 * Пример: ключ подойдёт, если его сдвинуть на 1 по ширине
 * lock    key
 * 1 0 1   1 0
 * 0 1 0   0 1
 * 1 1 1
 *
 * Вернуть тройку (Triple) -- (да/нет, требуемый сдвиг по высоте, требуемый сдвиг по ширине).
 * Если наложение невозможно, то первый элемент тройки "нет" и сдвиги могут быть любыми.
 */
fun canOpenLock(key: Matrix<Int>, lock: Matrix<Int>): Triple<Boolean, Int, Int> {
    val maxShiftHeight = lock.height - key.height + 1
    val maxShiftWidth = lock.width - key.width + 1

    for (shiftHeight in 0 until maxShiftHeight) {
        for (shiftWidth in 0 until maxShiftWidth) {
            if (isLockOpened(key, lock, shiftHeight, shiftWidth)) {
                return Triple(true, shiftHeight, shiftWidth)
            }
        }
    }

    return Triple(false, 0, 0)
}

fun isLockOpened(key: Matrix<Int>, lock: Matrix<Int>, shiftHeight: Int, shiftWidth: Int): Boolean {
    for (i in 0 until key.height) {
        for (j in 0 until key.width) {
            val lockRow = i + shiftHeight
            val lockColumn = j + shiftWidth

            if (lock[lockRow, lockColumn] == 1 && key[i, j] == 1) {
                return false
            }

            if (lock[lockRow, lockColumn] == 0 && key[i, j] == 0) {
                return false
            }
        }
    }
    return true
}