package network

import com.beust.klaxon.json
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.core.requests.tryCancel
import com.github.kittinunf.result.Result
import io.parseJsonToResponseBodyObject
import io.writeToFile
import kotlin.system.exitProcess

fun networkCall(tokenList: ArrayList<String>) {
    tokenList.chunked(100).forEach {

        val jsonObject = getJsonObject(it)

        val (request, response, result) = Fuel.post("https://iid.googleapis.com/iid/v1:batchImport")
            .jsonBody(jsonObject.toJsonString())
            .header(Headers.AUTHORIZATION, "key=<token_key>")
            .header(Headers.CONTENT_TYPE, "application/json; charset=utf-8")
            .response()

        val responseJson = response.body().asString("application/json")

        println(responseJson)


        when (result) {
            is Result.Failure -> {
                println("Trying to interrupt and cancel request")

                exitProcess(1)
            }
            is Result.Success -> {
                println("Success")


                val responseBodyObjectList = parseJsonToResponseBodyObject(responseJson)

                if (responseBodyObjectList != null) {
                    writeToFile(responseBodyObjectList, tokenList)
                } else {
                    request.tryCancel()
                    println("Failed: Nothing in the object")
                }
            }
        }
    }
}

fun getJsonObject(it: List<String>) = json {
    obj(
        "application" to "<package_for_application>", // example: com.example.ex
        "sandbox" to false,
        "apns_tokens" to array(it)
    )
}
