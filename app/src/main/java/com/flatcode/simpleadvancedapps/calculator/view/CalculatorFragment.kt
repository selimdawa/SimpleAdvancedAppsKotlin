package com.flatcode.simpleadvancedapps.calculator.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.calculator.viewmodel.CalculatorViewModel
import com.flatcode.simpleadvancedapps.databinding.FragmentCalculatorBinding
import com.flatcode.simpleadvancedapps.utils.DATA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.objecthunter.exp4j.ExpressionBuilder
import timber.log.Timber

@AndroidEntryPoint
class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CalculatorViewModel by hiltNavGraphViewModels(R.id.nav_calculator)
    private val historyAdapter = CalculatorHistoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.recyclerViewHistory.adapter = historyAdapter
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(requireContext())

        setupClickListeners()
        setupObservers()
    }

    private fun setupClickListeners() {
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
                viewModel.clearAll()
            }

            clearHistory.setOnClickListener {
                viewModel.clearHistory()
            }

            delete.setOnClickListener {
                viewModel.deleteLast()
            }

            equals.setOnClickListener {
                val currentExpression = txtPlaceHolder.text.toString()
                if (currentExpression.isNotEmpty()) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        try {
                            val finalResult = withContext(Dispatchers.Default) {
                                val expression = ExpressionBuilder(currentExpression).build()
                                val result = expression.evaluate()
                                val longResult = result.toLong()
                                if (result == longResult.toDouble()) {
                                    "= $longResult"
                                } else {
                                    "= $result"
                                }
                            }
                            viewModel.setResultValue(finalResult)
                            viewModel.saveToHistory(currentExpression, finalResult)
                        } catch (e: Exception) {
                            Timber.tag("Exception").d("Message: %s", e.message)
                        }
                    }
                }
            }
        }
    }

    private fun setupObservers() {
        viewModel.expression.observe(viewLifecycleOwner) { exp ->
            binding.txtPlaceHolder.text = exp
        }

        viewModel.result.observe(viewLifecycleOwner) { res ->
            binding.txtResult.text = res
        }

        viewModel.historyList.observe(viewLifecycleOwner) { history ->
            historyAdapter.submitList(history)
        }
    }

    private fun View.appendVal(string: String) {
        setOnClickListener {
            viewModel.appendValue(string)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}