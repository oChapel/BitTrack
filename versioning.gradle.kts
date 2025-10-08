// versioning.gradle.kts

/**
 * Builds an Android version code from the version of the project.
 * Handles "-SNAPSHOT" and "-RC" suffixes.
 *
 * Rules:
 * - SNAPSHOT counts as 0
 * - RC* counts as 1..98
 * - final release (no suffix) counts as 99
 */
fun buildVersionCode(version: String): Int {
    var candidate = 99
    var cleanVersion = version.lowercase().replace("-", "")

    // Tokenize into major.minor.patch
    val parts = cleanVersion.split(".")
    if (parts.size < 3) error("Version must have major.minor.patch format")

    var major = parts[0].toInt()
    var minor = parts[1].toInt()
    var patchPart = parts[2]
    var patch = patchPart.filter { it.isDigit() }.ifEmpty { "0" }.toInt()

    if (patchPart.contains("snapshot")) {
        candidate = 0
    } else if (patchPart.contains("rc")) {
        val rcNumber = patchPart.substringAfter("rc").filter { it.isDigit() }
        candidate = rcNumber.toIntOrNull() ?: 99
    }

    return (major * 1_000_000) + (minor * 10_000) + (patch * 100) + candidate
}

// Attach it to the project extras
val versionName = project.version.toString()
val versionCode = buildVersionCode(versionName)

extra["versionName"] = versionName
extra["versionCode"] = versionCode
