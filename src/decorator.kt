package kotlin_basics_course_2023

/* Note on typealias:
Type aliases provide alternative names for existing types. If the type name is too long you can introduce a different
shorter name and use the new one instead. */
typealias Decorator<T> = (() -> T) -> T

/**
 * Applies the given decorators from left to right.
 *
 * [Source](https://github.com/stavshamir/kotlin-decorators/tree/master)
 *
 * Usage:
 *
 * fun italic(f: () -> String) = "<i>${f()}</i>"
 *
 * fun hello() = decorateWith(::italic) { "hello" }
 */
fun <T> decorateWith(vararg decorators: Decorator<T>, function: () -> T): T {
    // Type parameters (<T>) are placed before the name of the function.
    return decorators.fold(initial = function()) { result, decorator -> decorator { result } }
}