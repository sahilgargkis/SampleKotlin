package com.samplekotlin.view.custom

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.DigitsKeyListener

class DecimalValueFilter : DigitsKeyListener(false, true) {

    private var digits = 2

    fun setDigits(d: Int) {
        digits = d
    }

    override fun filter(source: CharSequence, start: Int, end: Int,
                        dest: Spanned, dstart: Int, dend: Int): CharSequence {
        var source = source
        var start = start
        var end = end
        val out = super.filter(source, start, end, dest, dstart, dend)

        if (out != null) {
            source = out
            start = 0
            end = out.length
        }

        val len = end - start
        if (len == 0) {
            return source
        }

        val dlen = dest.length
        for (i in 0 until dstart) {
            if (dest[i] == '.') {
                return if (dlen - (i + 1) + len > digits)
                    ""
                else
                    SpannableStringBuilder(source, start, end)
            }
        }
        for (i in start until end) {
            if (source[i] == '.') {
                return if (dlen - dend + (end - (i + 1)) > digits)
                    ""
                else
                    break
            }
        }
        return SpannableStringBuilder(source, start, end)
    }
}