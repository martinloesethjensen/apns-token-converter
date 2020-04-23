package io

import com.beust.klaxon.JsonReader
import com.beust.klaxon.Klaxon
import model.IdToken
import model.ResponseBodyObjectList
import java.io.FileReader

fun parseJsonToResponseBodyObject(json: String) = Klaxon().parse<ResponseBodyObjectList>(json)

fun readJsonStreamToList(): ArrayList<String> {
    val klaxon = Klaxon()
    val result = arrayListOf<String>()
    JsonReader(FileReader("src/main/resources/tokens.json")).use { reader ->
        reader.beginArray {
            while (reader.hasNext()) {
                val idToken = klaxon.parse<IdToken>(reader)
                idToken?.let { result.add(it.token) }
            }
        }
    }

    Klaxon()
    return result
}