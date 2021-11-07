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

class ProductsAdapterTest {

    private lateinit var adapter: ProductsAdapter

    private val context = InstrumentationRegistry.getInstrumentation().context

    private val callback: (String) -> Unit = mockk()
    val mockView: View = mockk()

    @Before
    fun setUp() {
        adapter = ProductsAdapter(callback)
    }

    @Test
    fun given_adapter_when_call_onCreateViewHolder_then_returns_correct_viewholder() {
        val viewGroupMock: ViewGroup = mockk()
        val inflaterMock: LayoutInflater = mockk()
        val mockTextView: TextView = mockk()
        val mockContainer: View = mockk()

        ArrangeBuilder()
            .withViewGroupEnabled(viewGroupMock, inflaterMock)
            .withViewEnabled(mockTextView, mockContainer)

        adapter.onCreateViewHolder(viewGroupMock, 0)

        verify(exactly = 1) { viewGroupMock.context }
        verify(exactly = 1) { LayoutInflater.from(any()) }
        verify(exactly = 1) { inflaterMock.inflate(any<Int>(), any(), any()) }
        verify(exactly = 2) { mockView.findViewById<View>(any()) }
    }

    @Test
    fun given_adapter_when_call_onBindViewHolder_then_sets_values_on_its_view() {
        val mockTextView: TextView = mockk(relaxed = true)
        val mockContainer: View = mockk(relaxed = true)

        ArrangeBuilder()
            .withViewEnabled(mockTextView, mockContainer)

        val holder = ProductsAdapter.ViewHolder(mockView)

        adapter.updateData(listOf("product1"))
        adapter.onBindViewHolder(holder, 0)

        verify(exactly = 2) { mockView.findViewById<View>(any()) }
        verify { mockTextView.text = any<String>() }
        verify { mockContainer.setOnClickListener(any()) }
    }

    @Test
    fun given_adapter_when_call_getItemCount_then_returns_correct_value() {
        val expected = listOf("1", "2")
        adapter.updateData(expected)
        val count = adapter.itemCount

        assertEquals(expected.size, count)
    }

    inner class ArrangeBuilder {
        fun withViewEnabled(textViewMock: TextView, containerMock: View): ArrangeBuilder {
            every { mockView.findViewById<TextView>(R.id.productTextView) } returns textViewMock
            every { mockView.findViewById<View>(R.id.productItemContainer) } returns containerMock

            return this
        }

        fun withViewGroupEnabled(
            viewGroupMock: ViewGroup,
            inflaterMock: LayoutInflater,
        ): ArrangeBuilder {
            every { viewGroupMock.context } returns context
            mockkStatic(LayoutInflater::class)
            every { LayoutInflater.from(any()) } returns inflaterMock
            every { inflaterMock.inflate(any<Int>(), any(), any()) } returns mockView

            return this
        }
    }
}