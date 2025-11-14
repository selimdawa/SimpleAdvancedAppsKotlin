package com.flatcode.simpleadvancedapps.calculator

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.Unit.THEME
import com.flatcode.simpleadvancedapps.databinding.ActivityMainCalculatorBinding
import net.objecthunter.exp4j.ExpressionBuilder
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainCalculatorBinding
    var context: Context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        binding = ActivityMainCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            numberZero.appendVal(DATA.Zero, false)
            numberOne.appendVal(DATA.One, false)
            numberTwo.appendVal(DATA.Two, false)
            numberThree.appendVal(DATA.Three, false)
            numberFour.appendVal(DATA.Four, false)
            numberFive.appendVal(DATA.Five, false)
            numberSix.appendVal(DATA.Six, false)
            numberSeven.appendVal(DATA.Seven, false)
            numberEight.appendVal(DATA.Eight, false)
            numberNine.appendVal(DATA.Nine, false)

            dot.appendVal(DATA.DOT, false)
            clear.appendVal(DATA.EMPTY, true)
            divide.appendVal(DATA.divide, false)
            multiply.appendVal(DATA.multiply, false)
            minus.appendVal(DATA.minus, false)
            plus.appendVal(DATA.plus, false)

            delete.setOnClickListener {
                val expression = txtPlaceHolder.text.toString()
                if (expression.isNotEmpty()) {
                    txtPlaceHolder.text = expression.substring(0, expression.length - 1)
                }
            }

            equals.setOnClickListener {
                try {
                    val expression = ExpressionBuilder(txtPlaceHolder.text.toString()).build()
                    val result = expression.evaluate()
                    val longResult = result.toLong()
                    if (result == longResult.toDouble()) {
                        "= $longResult".also { txtResult.text = it }
                    } else {
                        "= $result".also { txtResult.text = it }
                    }
                } catch (e: Exception) {
                    Timber.tag("Exception").d("Message: %s", e.message)
                }
            }
        }
    }

    private fun View.appendVal(string: String, isClear: Boolean) {
        setOnClickListener {
            if (isClear) {
                binding.txtPlaceHolder.text = DATA.EMPTY
                binding.txtResult.text = DATA.EMPTY
            } else {
                binding.txtPlaceHolder.append(string)
            }
        }
    }
}