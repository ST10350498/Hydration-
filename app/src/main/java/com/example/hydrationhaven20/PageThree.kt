package com.example.hydrationhaven20

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailedViewActivity : AppCompatActivity() {

    private lateinit var detailedViewTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_two)


        detailedViewTextView = findViewById(R.id.detailedViewTextView)

        val hydrationIntakeData = intent.getIntegerArrayListExtra("hydrationIntakeData") ?: arrayListOf()
        val notesData = intent.getStringArrayListExtra("notesData") ?: arrayListOf()

        if (hydrationIntakeData.isNotEmpty() && notesData.isNotEmpty()) {
            val detailedInfo = StringBuilder()
            val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
            var totalIntake = 0

            for (i in hydrationIntakeData.indices) {
                detailedInfo.append("${daysOfWeek[i % daysOfWeek.size]}: ${hydrationIntakeData[i]} ml\n")
                detailedInfo.append("Notes: ${notesData[i]}\n\n")
                totalIntake += hydrationIntakeData[i]
            }

            val averageIntake = totalIntake / hydrationIntakeData.size
            detailedInfo.append("Average Weekly Hydration: $averageIntake ml")

            detailedViewTextView.text = detailedInfo.toString()
        } else {
            detailedViewTextView.text = "No data available."
        }
    }
}
