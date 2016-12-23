package com.lpa.abtc.util

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.springframework.format.Formatter
import java.util.*

/**
 * Created by cmmad_000 on 7/20/2016.
 */
class DateFormatter : Formatter<Date> {
    val formatter = DateTimeFormat.forPattern("dd/MM/yyyy")

    override fun print(p0: Date, p1: Locale): String {
        return DateTime(p0).toString(formatter)
    }


    override fun parse(p0: String, p1: Locale): Date {
        return DateTime.parse(p0, formatter).toDate()
    }


}
