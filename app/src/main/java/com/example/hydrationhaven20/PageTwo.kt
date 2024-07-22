package com.example.hydrationhaven20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge

private fun String.isNotEmpty(): Boolean = this.isNotBlank()

class PageTwo : AppCompatActivity() {

    private lateinit var morningText: EditText
    private lateinit var afternoonText: EditText
    private lateinit var notesText: EditText
    private lateinit var enterButton: Button
    private lateinit var clearButton: Button
    private lateinit var intakeButton: Button

    private val hydrationIntakeData = mutableListOf<Int>()
    private val notesData = mutableListOf<String>()
    private val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    private var currentDayIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_page_two)

        val openPageTwoButton: Button = findViewById(R.id.backBtn)
        openPageTwoButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        morningText = findViewById(R.id.MorningText)
        afternoonText = findViewById(R.id.AfternoonText)
        notesText = findViewById(R.id.NotesText)
        enterButton = findViewById(R.id.EnterBtn)
        clearButton = findViewById(R.id.ClearBtn)
        intakeButton = findViewById(R.id.IntakeBtn)

        enterButton.setOnClickListener {
            addHydrationInput()
        }

        clearButton.setOnClickListener {
            clearData()
        }

        intakeButton.setOnClickListener {
            navigateToDetailedView()
        }
    }

    private fun addHydrationInput() {
        val morningTextString = morningText.text.toString()
        val afternoonTextString = afternoonText.text.toString()
        val notesTextString = notesText.text.toString()

        if (morningTextString.isNotEmpty() && afternoonTextString.isNotEmpty()) {
            try {
                val morningHydration = morningTextString.toInt()
                val afternoonHydration = afternoonTextString.toInt()

                if (morningHydration >= 0 && afternoonHydration >= 0) {
                    val totalHydration = morningHydration + afternoonHydration
                    hydrationIntakeData.add(totalHydration)
                    notesData.add(notesTextString)
                    morningText.text.clear()
                    afternoonText.text.clear()
                    notesText.text.clear()

                    currentDayIndex = (currentDayIndex + 1) % daysOfWeek.size
                    Toast.makeText(this, "Hydration Input added successfully for ${daysOfWeek[currentDayIndex]}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Intake values must be non-negative", Toast.LENGTH_SHORT).show()
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Please enter valid integers for hydration intake", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please enter your water intake for both morning and afternoon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearData() {
        hydrationIntakeData.clear()
        notesData.clear()
        currentDayIndex = 0
        Toast.makeText(this, "Data cleared", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToDetailedView() {
        if (hydrationIntakeData.isNotEmpty()) {
            val intent = Intent(this, DetailedViewActivity::class.java).apply {
                putIntegerArrayListExtra("hydrationIntakeData", ArrayList(hydrationIntakeData))
                putStringArrayListExtra("notesData", ArrayList(notesData))
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "Input water intake first", Toast.LENGTH_SHORT).show()
        }
    }
}
