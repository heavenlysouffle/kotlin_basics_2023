package kotlin_basics_course_2023
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.log10
import kotlin.system.exitProcess


/* Note on Fibonacci numbers:
The Fibonacci sequence is a type series in which each number is the sum of the previous two. */

/* Note on Tail Recursion:
In normal recursion, you perform all recursive calls first, and calculate the result from return values at last.
Hence, you don't get result until all recursive calls are made.

In tail recursion, calculations are performed first, then recursive calls are executed (the recursive call passes
the result of your current step to the next recursive call). This makes the recursive call equivalent to looping,
and avoids the risk of stack overflow.

A recursive function is eligible for tail recursion if the function call to itself is the last operation it performs.

Tail recursion is a generic concept rather than the feature of Kotlin language. Some programming languages including
Kotlin use it to optimize recursive calls, whereas other languages (e.g. Python) do not support them.

Source: https://www.programiz.com/kotlin-programming/recursion */

/* Array vs ArrayList:
* An array is a fixed-length data structure. ArrayList is a variable-length data structure. */


// Factorial ordinary recursion
/**
 * Finds factorial of [num] number using ordinary recursion.
 * @param num a non-negative integer.
 * @return factorial of [num].
 * @throws IllegalArgumentException if [num] < 0.
 */
fun factorial(num: Long): Long {
    if (num < 0L) throw IllegalArgumentException("The factorial function is defined only for non-negative integers")
    return if (num == 0L) 1 else if (num == 1L) num else num * factorial(num - 1)
}

// Factorial tail recursion
/**
 * Finds factorial of [num] number using tail recursion.
 * @param num a non-negative integer.
 * @return factorial of [num].
 * @throws IllegalArgumentException if [num] < 0.
 */
tailrec fun factorialTail(num: Long, run: Long = 1): Long {
    if (num < 0L) throw IllegalArgumentException("The factorial function is defined only for non-negative integers")
    return if (num == 0L) 1 else if (num == 1L) run else factorialTail(num - 1, run * num)
}


// Fibonacci number tail recursion
/**
 * Finds one Fibonacci number from [n]th position of the classic Fibonacci sequence.
 * @param n a position of the needed Fibonacci number.
 * @return the value of the Fibonacci number on the [n]th position.
 * @throws IllegalArgumentException if [n] < 0.
 */
tailrec fun fibonacciNumber(n: Int, previous: Long = 1, beforePrevious: Long = 0): Long {
    if (n < 0) throw IllegalArgumentException("The classic Fibonacci numbers sequence is defined only for positions >= 0 (parameter [n] must be >= 0)")
    return when (n) {
        1 -> previous
        0 -> beforePrevious
        else -> fibonacciNumber(n - 1,beforePrevious + previous, previous)
    }
}

// Fibonacci sequence tail recursion
/**
 * Finds the classic Fibonacci numbers sequence starting from the [start]th position to [end]th position.
 * @param start The position F([start]) from where the resulting Fibonacci sequence starts.
 * @param end The position F([end]) where the resulting Fibonacci sequence ends.
 * @return The classic Fibonacci sequence from the [start]th position to [end]th position
 * as ArrayList<Long> of size ([start] - [end] + 1).
 * @throws IllegalArgumentException if [end] < [start].
 * @see fibonacciNumber is used.
 */
tailrec fun fibonacciSequence(start: Int, end: Int,
                              previous: Long = fibonacciNumber(start),
                              beforePrevious: Long = if (previous == 0L) 1 else fibonacciNumber(start - 1),
                              output: ArrayList<Long> = arrayListOf()): ArrayList<Long> {
    if (end < start) throw IllegalArgumentException("The start position of the sequence must be <= the ending position.")
    output.add(previous)
    return if (end - start == 0) output else fibonacciSequence(start + 1, end, beforePrevious + previous, previous, output)
}


/**
 * Counts digits in an integer number [number].
 * @param number an integer number.
 * @return the number of digits in [number].
 */
fun countDigitsLong(number: Long): Int {
    if (number == 0L) return 1
    var count = 0
    var num = number
    while (num != 0L) {
        num /= 10
        count++
    }
    return count
}


/**
 * Counts digits in the integer part of a floating-point number [num] using logarithmic approach.
 * @param num a floating-point number.
 * @return the number of digits in the integer part of [num].
 */
fun countIntegerPartDigits(num: Double): Int {
    return if (num == 0.0) 1
    else (log10(abs(num)) + 1).toInt()
}


/**
 * Counts digits in a natural number [num].
 * @param num a natural number.
 * @return the number of digits in [num].
 * @throws IllegalArgumentException if [num] < 0.
 * @see countDigitsLong is used.
 */
fun countDigitsNatural(num: Long): Int {
    if (num < 0L) throw IllegalArgumentException("A natural number is a positive integer from 1 to infinity.")
    else return countDigitsLong(num)
}


/**
 * Reads String entered by the user from the input stream, checks it for not being null or empty and returns in lowercase.
 * @return the entered string in lowercase.
 * @throws NullPointerException if the entered string is null or empty.
 */
fun enter(): String {
    val enter = readLine() /* Note on readLine() returning an empty string instead of null:
    In some environments or configurations, if you press Enter without entering any text,
    readLine() may return an empty string "" instead of null. Let's do a check to handle the situation consistently.

    if (enter.isNullOrEmpty()) enter = null
    else if (enter.lowercase() == "exit") exitProcess(0)
    input = enter?.toInt() ?: throw NullPointerException("Nothing was entered.")
    */
    if (enter.isNullOrEmpty()) throw NullPointerException("Nothing was entered.")
    return enter.lowercase()
}


/**
 * Represents laboratory work #2 realisation, consists of the completed tasks.
 */
class Lab2 {
    /**
     * Decorator for task functions. Continues running the task until it's done correctly, or it's stopped.
     *
     * Implementation of python-like decorators. A high-order function.
     * @param function a function to decorate
     * @throws Exception
     */
    private fun task(function: () -> Unit) {
        while (true) try {
            println("\nEnter \"stop\" at any moment to stop current task.")
            function()
            println()
            break
        } catch (ex: Exception) {
            when (ex) { // multi-catch of exceptions
                is InputMismatchException, is IllegalArgumentException, is NullPointerException -> {
                    print("\nWrong :/ ")
                    if (ex is InputMismatchException) println("[ Please enter valid data. ]")
                    else println("[ The reason: ${ex.message} ]")
                    readLine()
                }
                else -> throw ex
            }
        }
    }

    /* Task 1:
    Be sure to use recursion and recurrence. The program should calculate Fibonacci numbers and display the results on
    the screen. The number of Fibonacci numbers to be output is entered by the user. */
    /* Note on high-order and lambda functions:
    | task  | is a higher-order function.
    | {...} | below is a lambda function (anonymous function, unnamed) passed to task function call as a parameter. */
    fun task1() = task {
        print("Enter the start position of the sequence: ")
        var enter = enter()
        if (enter == "stop") return@task else if (enter == "exit") exitProcess(0)
        val start = enter.toInt()

        print("Enter the end position of the sequence: ")
        enter = enter()
        if (enter == "stop") return@task else if (enter == "exit") exitProcess(0)
        val end = enter.toInt()

        for ((position, number) in fibonacciSequence(start, end).withIndex()) println("F(${position + start}) = $number")
        println()
    }

    /* Task 2:
    Be sure to use recursion and recurrence. The program must count n! . The number n is entered by the user. */
    fun task2() = task {
        print("Enter a number: ")
        val enter = enter()
        if (enter == "stop") return@task else if (enter == "exit") exitProcess(0)
        val number = enter.toLong()

        println("$number! = ${factorialTail(number)}\n")
    }

    /* Task 3:
    Write a program to calculate the number (we don't know the number of digits) of decimal digits a natural number.
    Do not use strings, purely mathematical operations. */
    fun task3() = task {
        print("Enter a natural number: ")
        val enter = enter()
        if (enter == "stop") return@task else if (enter == "exit") exitProcess(0)
        val number = enter.toLong()

        println("There are ${countDigitsNatural(number)} digits in $number.\n")
    }
}


// this example doesn't use all developed functions
fun main() {
    var enter: String?
    var input: Int
    val tasks = Lab2()

    while (true) {
        print("1. Find numbers of the classic Fibonacci sequence from the position F(start) to F(end) (using tail recursion).\n" +
                "2. Find factorial of a number (using tail recursion).\n" +
                "3. Count digits in a natural number.\n" +
                "Enter the number of the needed option (\"exit\" to end the program): ")
        try {
            enter = enter()
            if (enter == "exit") exitProcess(0)
            input = enter.toInt()

            when (input) {
                1 -> tasks.task1()
                2 -> tasks.task2()
                3 -> tasks.task3()
                else -> println("There is no such option available. Please choose some option from the list.\n")
            }

        } catch (ex: Exception) {
            print("\nAn unexpected error! :( ")
            if (!ex.message.isNullOrEmpty()) println("[ The reason: ${ex.message} ]") else println()
            readLine()
        }
    }
}