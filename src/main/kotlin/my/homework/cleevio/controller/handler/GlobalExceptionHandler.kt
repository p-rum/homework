package my.homework.cleevio.controller.handler

import my.homework.cleevio.controller.ErrorResponse
import my.homework.cleevio.exception.EntityNotFoundException
import my.homework.cleevio.exception.TransactionFailedException
import my.homework.cleevio.exception.ValidationException
import org.springframework.http.HttpStatus

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleEntityNotFound(ex: EntityNotFoundException): ErrorResponse {
        return ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            message = ex.message,
            attributes = null
        )
    }

    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationException(ex: ValidationException): ErrorResponse {
        return ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message,
            attributes = ex.attributes
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationException(ex: IllegalArgumentException): ErrorResponse {
        return ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message,
            attributes = null
        )
    }


    @ExceptionHandler(TransactionFailedException::class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    fun handleTxException(ex: TransactionFailedException): ErrorResponse {
        return ErrorResponse(
            status = HttpStatus.BAD_GATEWAY.value(),
            message = ex.message,
            attributes = null
        )
    }
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGeneralException(ex: Exception): ErrorResponse {
        return ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Internal Server Error: ${ex.message}",
            attributes = null
        )
    }
}