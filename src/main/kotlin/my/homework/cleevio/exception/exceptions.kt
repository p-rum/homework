package my.homework.cleevio.exception

class EntityNotFoundException(message: String) : RuntimeException(message)

class TransactionFailedException(message: String) : RuntimeException(message)

class ValidationException(val attributes: Map<String, String>) : RuntimeException()
