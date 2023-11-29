package com.frontleaves.general.utils

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
class BaseResponse constructor(val output: String, val code: Int, val message: String, val data: Any?) :
    Serializable {
    constructor(httpStatus: HttpStatus) : this(
        httpStatus.toString(),
        httpStatus.value(),
        httpStatus.reasonPhrase,
        null
    )

    constructor(httpStatus: HttpStatus, data: Any?) : this(
        httpStatus.toString(),
        httpStatus.value(),
        httpStatus.reasonPhrase,
        data
    )

    constructor(httpStatus: HttpStatus, message: String) : this(
        httpStatus.toString(),
        httpStatus.value(),
        message,
        null
    )

    constructor(httpStatus: HttpStatus, message: String, data: Any?) : this(
        httpStatus.toString(),
        httpStatus.value(),
        message,
        data
    )

    constructor(httpStatus: HttpStatus, code: Int, message: String) : this(
        httpStatus.toString(),
        code,
        message,
        null
    )

    constructor(httpStatus: HttpStatus, code: Int, message: String, data: Any?) : this(
        httpStatus.toString(),
        code,
        message,
        data
    )

    constructor(code: Int, message: String) : this(
        HttpStatus.OK.toString(),
        code, message,
        null
    )

    constructor(code: Int, message: String, data: Any?) : this(
        HttpStatus.OK.toString(),
        code,
        message,
        data
    )

    constructor(message: String, data: Any?) : this(
        HttpStatus.OK.toString(),
        HttpStatus.OK.value(),
        message,
        data
    )

    constructor(data: Any?) : this(
        HttpStatus.OK.toString(),
        HttpStatus.OK.value(),
        HttpStatus.OK.reasonPhrase,
        data
    )

    constructor() : this(
        HttpStatus.OK.toString(),
        HttpStatus.OK.value(),
        HttpStatus.OK.reasonPhrase,
        null
    )
}
