package com.example.numberwizard

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.*


class MainActivity : AppCompatActivity() {

    private var started = false
    private var guess: Int = 0
    private var tries = 0
    private var iRange = 1
    private var fRange = 1000
    private val phrases = listOf(
        "Dammit! Ok, ok, ok, let me give it another go...",
        "Hmmm.. are you sure? So be it. Lemme feel your vibes again.",
        "FUCK! aight, aight, I'm seeing it now...",
        "OOOoooommmmmmmmmmmmmmmmmmmy god! You're impossible!"
    )

    //@SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        lower.setOnClickListener {
            if (!started) {
                phrase.text = "Press start to begin"
            } else {
            fRange = if (guess - 1 < iRange) guess else guess - 1
            println(fRange)
            guess = Random.nextInt(iRange..fRange)
            guessSlot.text = guess.toString()
            phrase.text = if (iRange == fRange) "C'mon, there's nowhere to go. DEAL WITH MY MAGIC, PEASANT!" else phrases.shuffled().first()
            tries++
            }
        }
        higher.setOnClickListener {
            if (!started) {
                phrase.text = "Press start to begin"
            } else {
                iRange = if (guess + 1 > fRange) guess else guess + 1
                println(iRange)
                guess = Random.nextInt(iRange..fRange)
                guessSlot.text = guess.toString()
                phrase.text =
                    if (iRange == fRange) "C'mon, there's nowhere to go. DEAL WITH MY MAGIC, PEASANT!" else phrases.shuffled().first()
                tries++
            }
        }
        guessed.setOnClickListener {
            if (!started) {
                phrase.text = "Press start to begin"
            } else {
                phrase.text = "A-ha! and it only took me $tries tries... If you want to play again, press 'Start'"
                started = false
            }
        }
        start.setOnClickListener {
            started = true
            iRange = 1
            fRange = 1000
            tries = 0
            guess = Random.nextInt(iRange..fRange)
            guessSlot.text = guess.toString()
            phrase.text = "Well, did I get it right?"
        }


    }
}
