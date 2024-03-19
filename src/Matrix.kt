@file:Suppress("UNUSED_PARAMETER")
package mmcs.assignment2

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    if (height <= 0 || width <= 0) {
        throw IllegalArgumentException("Height and width must be greater than zero.")
    }
    return MatrixImpl(height, width, e)
}

/**
 * Реализация интерфейса "матрица"
 */

@Suppress("EqualsOrHashCode")
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {
    private val matrix: MutableList<MutableList<E>> = MutableList(height) {
        MutableList(width) { e }
    }

    override fun get(row: Int, column: Int): E  {
        if (row < 0 || row >= height || column < 0 || column >= width) {
            throw IndexOutOfBoundsException("Cell ($row, $column) is out of bounds")
        }
        return matrix[row][column]
    }

    override fun get(cell: Cell): E {
        return get(cell.row, cell.column)
    }

    override fun set(row: Int, column: Int, value: E) {
        if (row < 0 || row >= height || column < 0 || column >= width) {
            throw IndexOutOfBoundsException("Cell ($row, $column) is out of bounds")
        }
        matrix[row][column] = value
    }

    override fun set(cell: Cell, value: E) {
        set(cell.row, cell.column, value)
    }

    override fun equals(other: Any?): Boolean{
        if (other !is MatrixImpl<*> || height != other.height || width != other.width) return false
        for (row in 0..<height) {
            for (col in 0..<width) {
                if (get(row, col) != other[row, col]) {
                    return false
                }
            }
        }
        return true
    }

    override fun toString(): String {
        val str = StringBuilder()
        for (row in 0..<height) {
            for (col in 0..<width) {
                str.append(get(row, col)).append(" ")
            }
            str.append("\n")
        }
        return str.toString()
    }
}
