package com.dawan.recyclerview.samples

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dawan.recyclerview.ViewEvent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        val adapter = TextListAdapter()
        // 调用EventAdapter的addViewEvent来添加事件，addViewEvent可以一次设置多个事件
        adapter.addViewEvent(
            R.layout.text_item,// 布局id，一个Adapter可能有多种布局，你需要明确指明为哪个布局添加事件
            // 添加第一个事件
            ViewEvent(
                R.id.imageView, // view id，一个布局中一般都会有多个组件（View），你需要明确指明为哪个组件（View）添加事件
                View.OnClickListener {
                    // 可以通过调用EventAdapter.getViewHolder(...)来获取ViewHolder
                    val viewHolder = adapter.getViewHolder(it)
                    if (viewHolder != null) {
                        // 获取到ViewHolder后，你可以方便的获取到当前ViewHolder的位置
                        val adapterPosition = viewHolder.adapterPosition
                        adapter.deleteItem(adapterPosition)
                        // 如果在adapter中设置了tag，你可以在这里通过EventViewHolder.getTag(...)来获取变量
                        // viewHolder.getTag(R.id.xxxx)
                    }
                }),
            // 添加第二个事件
            ViewEvent(R.id.root, View.OnClickListener {
                val viewHolder = adapter.getViewHolder(it)
                if (viewHolder != null) {
                    val adapterPosition = viewHolder.adapterPosition
                    val textView: TextView? = viewHolder.findViewById(R.id.textView)
                    val text = textView?.text
                    Toast.makeText(this, "position=$adapterPosition,text=$text", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        )
        // 设置好事件后将adapter绑定到RecyclerView中
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        val topInsertButton = findViewById<Button>(R.id.button)
        val bottomInsertButton = findViewById<Button>(R.id.button2)
        topInsertButton.setOnClickListener {
            val text = "top insert"
            adapter.insertItem(0, text)
            recyclerView.scrollToPosition(0)
        }
        bottomInsertButton.setOnClickListener {
            val text = "bottom insert"
            adapter.insertItem(adapter.itemCount, text)
            recyclerView.scrollToPosition(adapter.itemCount - 1)
        }
        val textList = generateTextList(30, 3..9)
        adapter.setTextList(textList)
    }

    private fun generateTextList(count: Int, textLength: IntRange): List<String> {
        val resultList = mutableListOf<String>()
        val textRange = '\u4e00'..'\u9fa5' // 基本中文字符Unicode范围
        val stringBuilder = StringBuilder()
        var stringLength: Int
        val forRange = 0 until count
        for (i in forRange) {
            stringLength = textLength.random()
            for (j in 0 until stringLength) {
                stringBuilder.append(textRange.random())
            }
            resultList.add(stringBuilder.toString())
            stringBuilder.clear()
        }
        return resultList
    }
}