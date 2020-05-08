package com.shakirfattani.diceroller

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val random = Random()
    private var currentSelected = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_roll_dice.setOnClickListener {
            while (true) {
                val i = random.nextInt(6) + 1
                if (i != currentSelected) {
                    currentSelected = i
                    break
                }
            }
            rollDice()
        }

        savedInstanceState?.run {
            currentSelected = getInt("dice_val")
            rollDice()
        }

        try {
            val pInfo = packageManager.getPackageInfo(packageName, 0)
            version_label.text =
                "version: ${pInfo.versionName}.${(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) pInfo.longVersionCode.toString() else pInfo.versionCode.toString())}"
        } catch (ignore: PackageManager.NameNotFoundException) {
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState.apply {
            putInt("dice_val", currentSelected)
        })
    }

    private fun rollDice() = image_current_img.setImageResource(
        when (currentSelected) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.empty_dice
        }
    )
}
