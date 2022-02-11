@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

// Урок 9: проектирование классов
// Максимальное количество баллов = 40 (без очень трудных задач = 15)

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
 * Простая (2 балла)
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    if (listOf(height, width).any { it <= 0 }) throw IllegalArgumentException("Height or Width need to be more than 0.")
    return MatrixImpl(height, width, e)
}

/**
 * Средняя сложность (считается двумя задачами в 3 балла каждая)
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(
    override val height: Int, override val width: Int, e: E,
    private val matrix: MutableList<E> = MutableList(width * height) { e }
) : Matrix<E> {
    override fun get(row: Int, column: Int): E = matrix[row + column * height]
    override fun get(cell: Cell): E = get(cell.row, cell.column)
    override fun set(cell: Cell, value: E) = set(cell.row, cell.column, value)
    override fun set(row: Int, column: Int, value: E) {
        matrix[row + column * height] = value
    }

    override fun equals(other: Any?): Boolean = other is MatrixImpl<*> && other.matrix == this.matrix
    //https://play.kotlinlang.org/byExample/03_special_classes/01_Data%20classes
    //    override fun equals(other: Any?): Boolean {
    //        if (this === other) return true
    //        if (javaClass != other?.javaClass) return false
    //
    //        other as MatrixImpl<*>
    //
    //        if (height != other.height) return false
    //        if (width != other.width) return false
    //        if (matrix != other.matrix) return false
    //
    //        return true
    //    }

    override fun toString(): String {
        val matrixStr = StringBuilder()
        for (column in 0 until height) {
            matrixStr.append(
                matrix.subList(column * width, (column + 1) * width).joinToString(separator = "\t", postfix = "\n")
            )
        }
        return matrixStr.toString()
    }

    //KOTLIN SUGGESTED THE CODE BELOW
    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + matrix.hashCode()
        return result
    }
}

