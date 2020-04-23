package model

data class IdToken(var id: Any?) {
    var token: String = ""
        set(value) {
            field = "\"$value\""
        }
}

data class ResponseBodyObjectList(var results: ArrayList<ResponseResult>)

data class ResponseResult(var registration_token: String, var apns_token: String, var status: String)
