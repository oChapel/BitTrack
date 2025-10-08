package com.example.bittrack.meta.model

enum class TransactionCategory(val displayName: String) {
    GROCERIES("Groceries"),
    TAXI("Taxi"),
    ELECTRONICS("Electronics"),
    RESTAURANT("Restaurant"),
    OTHER("Other");

    companion object {
        fun fromName(name: String): TransactionCategory =
            entries.firstOrNull { it.name.equals(name, ignoreCase = true) } ?: OTHER
    }
}
