package com.dk.englishcards.edit

import android.os.Bundle
import android.view.View
import com.dk.englishcards.R
import com.dk.englishcards.cards.EnglishCard
import com.dk.englishcards.commons.BaseSubPageActivity
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : BaseSubPageActivity() {
    private var englishCardId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val englishCardId = intent.getStringExtra(EnglishCard.ID_FIELD)
        if (englishCardId != null) {
            this.englishCardId = englishCardId
            val englishCard = super.dbHandler.readById(englishCardId)
            englishEditText.setText(englishCard?.english)
            motherLanguageEditText.setText(englishCard?.motherLanguage)
            memoEditText.setText(englishCard?.memo)
            confidenceRatingBar.rating = englishCard?.confidence!!

            this.deleteButton.visibility = View.VISIBLE
        } else {
            englishEditText.setText("")
            motherLanguageEditText.setText("")
            memoEditText.setText("")
            confidenceRatingBar.rating = 0.0F

            this.deleteButton.visibility = View.INVISIBLE
        }

        saveButton.setOnClickListener {
            when(val id = this.englishCardId) {
                null -> {
                    super.dbHandler.insert(
                        englishEditText.text.toString(),
                        motherLanguageEditText.text.toString(),
                        memoEditText.text.toString(),
                        confidenceRatingBar.rating
                    )
                }
                else -> {
                    super.dbHandler.update(
                        id,
                        englishEditText.text.toString(),
                        motherLanguageEditText.text.toString(),
                        memoEditText.text.toString(),
                        confidenceRatingBar.rating
                    )
                }
            }
            super.moveToMain()
        }

        cancelButton.setOnClickListener {
            super.moveToMain()
        }

        deleteButton.setOnClickListener {
            val id = this.englishCardId
            if (id != null) {
                super.dbHandler.delete(id)
            }
            super.moveToMain()
        }
    }
}