package com.samplekotlin.view.custom

import android.content.Context
import android.content.res.TypedArray
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.View
import android.widget.TextView

import com.samplekotlin.R

class ExpandableTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : TextView(context, attrs) {

    var originalText: CharSequence? = null
        private set
    private var trimmedText: CharSequence? = null
    private var bufferType: TextView.BufferType? = null
    private var trim = true
    private var trimLength: Int = 0

    private val displayableText: CharSequence?
        get() = if (trim) trimmedText else originalText

    init {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView)
        this.trimLength = typedArray.getInt(R.styleable.ExpandableTextView_trimLength, DEFAULT_TRIM_LENGTH)
        typedArray.recycle()

        setOnClickListener {
            trim = !trim
            setText()
            requestFocusFromTouch()
        }
    }

    private fun setText() {
        if (displayableText!!.toString().endsWith(ELLIPSIS)) {
            val text = displayableText!!.toString()
            val wordtoSpan = SpannableString(text)
            wordtoSpan.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorAccent)), text.length - ELLIPSIS.length, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            super.setText(wordtoSpan, bufferType)
        } else {
            super.setText(displayableText, bufferType)
        }
    }

    override fun setText(text: CharSequence, type: TextView.BufferType) {
        originalText = text
        trimmedText = getTrimmedText(text)
        bufferType = type
        setText()
    }

    private fun getTrimmedText(text: CharSequence?): CharSequence? {
        return if (originalText != null && originalText!!.length > trimLength) {
            SpannableStringBuilder(originalText, 0, trimLength + 1).append(ELLIPSIS)
        } else {
            originalText
        }
    }

    fun setTrimLength(trimLength: Int) {
        this.trimLength = trimLength
        trimmedText = getTrimmedText(originalText)
        setText()
    }

    fun getTrimLength(): Int {
        return trimLength
    }

    companion object {
        private val DEFAULT_TRIM_LENGTH = 200
        private val ELLIPSIS = " More"
    }
}