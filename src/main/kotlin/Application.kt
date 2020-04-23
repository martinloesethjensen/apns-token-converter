import io.readJsonStreamToList
import network.networkCall
import java.io.File
import java.util.*

fun main() {
    println("Starting application")

    var result = readJsonStreamToList()

    println(result.size)

    result = readFromFile(result)
    println(result.size)

    networkCall(result)
}

fun readFromFile(result: ArrayList<String>): ArrayList<String> {
    val file = File("<path_to_projcet>/src/main/resources/temp.txt")

    file.readLines().forEach { line ->
        result.removeIf { token ->
            line.contains(token)
        }
    }

    return result
}




