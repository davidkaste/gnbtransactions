package com.davidcastella.features.productlist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.test.platform.app.InstrumentationRegistry
import com.davidcastella.features.productlist.R
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductDetailsAdapterTest {

    private lateinit var adapter: ProductDetailsAdapter

    private val amounts = listOf("123,45€", "123,45€", "123,45€")
    private val total = "456,78€"

    private val mockView: View = mockk()
    private val context = InstrumentationRegistry.getInstrumentation().context

    @Before
    fun setUp() {
        adapter = ProductDetailsAdapter(amounts, total)
    }

    @Test
    fun given_adapter_when_call_onCreateViewHolder_then_returns_correct_amount_viewholder() {
        val viewGroupMock: ViewGroup = mockk()
        val inflaterMock: LayoutInflater = mockk()
        val mockTextView: TextView = mockk()

        ArrangeBuilder()
            .withAmountViewGroupEnabled(viewGroupMock, inflaterMock)
            .withAmountViewEnabled(mockTextView)

        adapter.onCreateViewHolder(viewGroupMock, AMOUNT_VIEW)

        verify(exactly = 1) { viewGroupMock.context }
        verify(exactly = 1) { LayoutInflater.from(viewGroupMock.context) }
        verify(exactly = 1) {
            inflaterMock.inflate(
                R.layout.amount_item_list,
                viewGroupMock,
                false
            )
        }
        verify(exactly = 1) { mockView.findViewById<View>(any()) }
    }

    @Test
    fun given_adapter_when_call_onCreateViewHolder_then_returns_correct_total_viewholder() {
        val viewGroupMock: ViewGroup = mockk()
        val inflaterMock: LayoutInflater = mockk()
        val mockTextView: TextView = mockk()

        ArrangeBuilder()
            .withTotalViewGroupEnabled(viewGroupMock, inflaterMock)
            .withTotalViewEnabled(mockTextView)

        adapter.onCreateViewHolder(viewGroupMock, TOTAL_VIEW)

        verify(exactly = 1) { viewGroupMock.context }
        verify(exactly = 1) { LayoutInflater.from(viewGroupMock.context) }
        verify(exactly = 1) {
            inflaterMock.inflate(
                R.layout.total_item_list,
                viewGroupMock,
                false
            )
        }
        verify(exactly = 1) { mockView.findViewById<View>(any()) }
    }

    @Test
    fun given_adapter_when_call_getItemCount_then_returns_correct_value() {
        val count = adapter.itemCount

        assertEquals(amounts.size + 1, count)
    }

    @Test
    fun given_adapter_when_call_getItemViewType_then_returns_correct_value() {
        val viewType1 = adapter.getItemViewType(0)

        assertEquals(AMOUNT_VIEW, viewType1)

        val viewType2 = adapter.getItemViewType(amounts.size)

        assertEquals(TOTAL_VIEW, viewType2)
    }

    @Test
    fun given_adapter_when_call_onBindViewHolder_then_sets_values_on_its_amount_view() {
        val mockTextView: TextView = mockk(relaxed = true)

        ArrangeBuilder()
            .withAmountViewEnabled(mockTextView)

        val holder = ProductDetailsAdapter.AmountViewHolder(mockView)

        adapter.onBindViewHolder(holder, 0)

        verify(exactly = 1) { mockView.findViewById<View>(any()) }
        verify { mockTextView.text = any<String>() }
    }

    @Test
    fun given_adapter_when_call_onBindViewHolder_then_sets_values_on_its_total_view() {
        val mockTextView: TextView = mockk(relaxed = true)

        ArrangeBuilder()
            .withTotalViewEnabled(mockTextView)

        val holder = ProductDetailsAdapter.TotalViewHolder(mockView)

        adapter.onBindViewHolder(holder, amounts.size)

        verify(exactly = 1) { mockView.findViewById<View>(any()) }
        verify { mockTextView.text = any<String>() }
    }

    inner class ArrangeBuilder {
        fun withAmountViewEnabled(textViewMock: TextView): ArrangeBuilder {
            every { mockView.findViewById<TextView>(R.id.amountTextView) } returns textViewMock

            return this
        }

        fun withTotalViewEnabled(textViewMock: TextView): ArrangeBuilder {
            every { mockView.findViewById<TextView>(R.id.totalValueTextView) } returns textViewMock

            return this
        }

        fun withAmountViewGroupEnabled(
            viewGroupMock: ViewGroup,
            inflaterMock: LayoutInflater,
        ): ArrangeBuilder {
            prepareViewGroupMocks(viewGroupMock, inflaterMock)
            every {
                inflaterMock.inflate(
                    R.layout.amount_item_list,
                    viewGroupMock,
                    false
                )
            } returns mockView

            return this
        }

        fun withTotalViewGroupEnabled(
            viewGroupMock: ViewGroup,
            inflaterMock: LayoutInflater,
        ): ArrangeBuilder {
            prepareViewGroupMocks(viewGroupMock, inflaterMock)
            every {
                inflaterMock.inflate(
                    R.layout.total_item_list,
                    viewGroupMock,
                    false
                )
            } returns mockView

            return this
        }

        private fun prepareViewGroupMocks(
            viewGroupMock: ViewGroup,
            inflaterMock: LayoutInflater
        ) {
            every { viewGroupMock.context } returns context
            mockkStatic(LayoutInflater::from)
            every { LayoutInflater.from(viewGroupMock.context) } returns inflaterMock
        }
    }

    companion object {
        private const val AMOUNT_VIEW = 0
        private const val TOTAL_VIEW = 1
    }
}