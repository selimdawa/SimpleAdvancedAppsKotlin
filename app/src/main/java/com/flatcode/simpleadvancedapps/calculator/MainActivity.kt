package com.flatcode.simpleadvancedapps.calculator

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simpleadvancedapps.utils.DATA
import com.flatcode.simpleadvancedapps.utils.THEME
import com.flatcode.simpleadvancedapps.databinding.ActivityMainCalculatorBinding
import net.objecthunter.exp4j.ExpressionBuilder
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainCalculatorBinding? = null
    private val binding get() = _binding!!
    private val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            numberZero.appendVal(DATA.Zero)
            numberOne.appendVal(DATA.One)
            numberTwo.appendVal(DATA.Two)
            numberThree.appendVal(DATA.Three)
            numberFour.appendVal(DATA.Four)
            numberFive.appendVal(DATA.Five)
            numberSix.appendVal(DATA.Six)
            numberSeven.appendVal(DATA.Seven)
            numberEight.appendVal(DATA.Eight)
            numberNine.appendVal(DATA.Nine)

            dot.appendVal(DATA.DOT)
            divide.appendVal(DATA.divide)
            multiply.appendVal(DATA.multiply)
            minus.appendVal(DATA.minus)
            plus.appendVal(DATA.plus)

            clear.setOnClickListener {
                txtPlaceHolder.text = DATA.EMPTY
                txtResult.text = DATA.EMPTY
            }

            delete.setOnClickListener {
                val expression = txtPlaceHolder.text.toString()
                if (expression.isNotEmpty()) {
                    txtPlaceHolder.text = expression.dropLast(1)
                }
            }

            equals.setOnClickListener {
                try {
                    val expression = ExpressionBuilder(txtPlaceHolder.text.toString()).build()
                    val result = expression.evaluate()
                    val longResult = result.toLong()
                    txtResult.text = if (result == longResult.toDouble()) {
                        "= $longResult"
                    } else {
                        "= $result"
                    }
                } catch (e: Exception) {
                    Timber.tag("Exception").d("Message: %s", e.message)
                }
            }
        }
    }

    private fun View.appendVal(string: String) {
        setOnClickListener {
            binding.txtPlaceHolder.append(string)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}