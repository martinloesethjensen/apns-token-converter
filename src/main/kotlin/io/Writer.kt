package io

import java.io.File
import model.ResponseBodyObjectList

fun writeToFile(responseBodyObjectList: ResponseBodyObjectList, oldList: ArrayList<String>) {
    val map = LinkedHashMap<String, String>()
    oldList.forEach {
        map[it] = ""
    }

    responseBodyObjectList.results.forEach {
        if (it.status == "OK") {
            map[it.apns_token] = it.registration_token
        }
    }

    val file = File("<path_to_projcet>/src/main/resources/temp.txt")

    map.filter { it.value.isNotEmpty() }.forEach { file.appendText("${it.key};${it.value}\n") }
}
