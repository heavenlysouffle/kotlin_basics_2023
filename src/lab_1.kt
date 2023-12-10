package kotlin_basics_course_2023

import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.system.exitProcess


fun main() {
    val scanner = Scanner(System.`in`)

    /**
     * A function to enter a Float value from the keyboard.
     * @return Float value entered by user from the keyboard.
     */
    fun enterFloat(string: String): Float {
        print("Enter $string: ")
        return scanner.nextFloat()
    }

    /**
     * Finds the max Float value.
     * @param numbers 0 or more Float variables
     * @return the max Float value if there is one or NaN if 0 variables have been received.
     */
    fun max(vararg numbers: Float): Float { // with vararg function can accept zero or more variables
        return numbers.maxOrNull() ?: Float.NaN
    }

    /**
     * Finds roots of quadratic equation (including complex roots).
     * @return root or roots as Array<String>.
     */
    fun solveQuadratic(y: Float, a: Float, b: Float, c: Float): Array<String> {
        return if (a == 0.toFloat()) {
            if (b == 0.toFloat()) throw IllegalArgumentException("[Solving a quadratic equation] if a is 0 then b cannot be 0")
            else arrayOf(((y - c) / b).toString())
        }
        else {
            val discriminant = b.pow(2) - 4 * a * c
            if (discriminant >= 0) {
                val sqrt_d = sqrt(discriminant)
                val x1 = (-b + sqrt_d) / 2 * a
                val x2 = (-b - sqrt_d) / 2 * a
                if (x1 == x2) arrayOf(x1.toString())
                else arrayOf(x1.toString(), x2.toString())
            } else { // complex roots
                val realPart = -b / (2 * a)
                val imaginaryPart = sqrt(-discriminant) / (2 * a)
                arrayOf("$realPart + ${imaginaryPart}i", "$realPart - ${imaginaryPart}i")
            }
        }
    }

    var a: Float
    var b: Float
    var c: Float
    var d: Float
    var x: Float
    var leave = ""

    // Task 1 realisation
    while (leave != "no") try {
        println("Task 1")
        a = enterFloat("a")
        b = enterFloat("b")
        c = enterFloat("c")
        d = enterFloat("d")
        x = enterFloat("x")

        println("\nMax of a, b, c, d: ${max(a, b, c, d)}")
        println("x^4 = ${x.pow(4)}")
        println("ax^2 + bx + c = ${a * x.pow(2) + b * x + c}\n")

    } catch (ex: InputMismatchException) {
        println("\nWrong :/ Please enter integer of float number\n")
        scanner.nextLine() // clearing input buffer
    } finally {
        print("Do you wanna continue this task? (Enter \"no\" to finish this task, enter \"exit\" to close the program): ")
        leave = readln().lowercase()
        if (leave == "exit") exitProcess(0) else println()
    }

    var y: Float
    var roots: Array<String>

    // Task 2 realisation
    while (true) try {
        println("Task 2")
        println("Solving equation y = x^4")
        y = enterFloat("y")
        if (y < 0) throw IllegalArgumentException("y must be >= 0")
        x = y.pow(1 / 4.toFloat())
        println("x = ${x}, x = ${-x}\n")

        println("Solving equation y = ax^2 + bx + c")
        y = enterFloat("y")
        a = enterFloat("a")
        b = enterFloat("b")
        c = enterFloat("c")
        roots = solveQuadratic(y, a, b, c)
        for (root in roots) print("x = $root ")
        println("\n")

        println("Solving equation y = ax + c")
        y = enterFloat("y")
        a = enterFloat("a")
        c = enterFloat("c")
        println("x = ${solveQuadratic(y, 0.toFloat(), a ,c).first()}\n")

    } catch (ex: Exception) {
        when (ex) { // multi-catch of exceptions
            is InputMismatchException, is IllegalArgumentException -> {
                println("\nWrong :/ Please enter integer of float number")
                if (!ex.message.isNullOrEmpty()) println("The reason: ${ex.message}\n") else println()
                // empty — an empty string (a string with no characters, whose length is zero)
                // blank — a string that is either empty or consists entirely of whitespace characters
                scanner.nextLine() // clearing input buffer
            }
            else -> throw ex
        }
    } finally {
        print("Do you wanna continue this task? (Enter \"no\" or \"exit\" to close the program): ")
        leave = readln().lowercase()
        if ((leave == "exit") or (leave == "no")) exitProcess(0) else println()
    }
}