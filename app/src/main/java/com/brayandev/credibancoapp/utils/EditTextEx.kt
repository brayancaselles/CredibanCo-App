package com.brayandev.credibancoapp.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText

fun EditText.onTextChanged(listener: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            listener(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}

fun EditText.loseFocusAfterAction(action: Int) {
    this.setOnEditorActionListener { v, actionId, _ ->
        if (actionId == action) {
            this.dismissKeyboard()
            v.clearFocus()
        }
        return@setOnEditorActionListener false
    }
}

fun TextInputEditText.validateCharacters(minLength: Int, isComplete: (Boolean) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s.toString().length < minLength) {
                this@validateCharacters.error = "Debe contener al menos $minLength caracteres."
                isComplete(false)
            } else {
                this@validateCharacters.error = null
                isComplete(true)
            }
        }
    })
}
