package com.flatcode.simpleadvancedapps.calculator

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simpleadvancedapps.databinding.ActivityMainCalculatorBinding
import com.flatcode.simpleadvancedapps.utils.DATA
import net.objecthunter.exp4j.ExpressionBuilder
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainCalculatorBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            numberZero.appendVal(DATA.ZERO)
            numberOne.appendVal(DATA.ONE)
            numberTwo.appendVal(DATA.TWO)
            numberThree.appendVal(DATA.THREE)
            numberFour.appendVal(DATA.FOUR)
            numberFive.appendVal(DATA.FIVE)
            numberSix.appendVal(DATA.SIX)
            numberSeven.appendVal(DATA.SEVEN)
            numberEight.appendVal(DATA.EIGHT)
            numberNine.appendVal(DATA.NINE)

            dot.appendVal(DATA.DOT)
            divide.appendVal(DATA.DIVIDE)
            multiply.appendVal(DATA.MULTIPLY)
            minus.appendVal(DATA.MINUS)
            plus.appendVal(DATA.PLUS)

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