package util

fun Int.formatTimer(): String {
    val seconds: Int = this % 60
    var minutes: Int = this / 60
    return if (minutes > 60) {
        val hour = minutes / 60
        minutes %= 60
        String.format("%d:%02d:%02d", hour, minutes, seconds)
    } else {
        String.format("%02d:%02d", minutes, seconds)
    }
}