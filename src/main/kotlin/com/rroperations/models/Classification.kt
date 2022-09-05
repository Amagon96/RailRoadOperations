package com.rroperations.models

import java.util.*

interface Classification {
    var id: UUID?
    var type: String
    var name: String
    var classification: Int
}