package revolhope.splanes.com.domain.feature.employees.model

enum class Gender(val value: String) {
    MALE("M"),
    FEMALE("F"),
    UNKNOWN("");

    companion object {
        fun parse(value: String?): Gender {
            val map = values().associateBy { it.value }
            return map[value] ?: UNKNOWN
        }
    }
}