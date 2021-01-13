package com.dawan.recyclerview.samples

import android.widget.TextView
import com.dawan.recyclerview.EventAdapter
import com.dawan.recyclerview.EventViewHolder

// 1. 继承EventAdapter
class TextListAdapter : EventAdapter<EventViewHolder>() {
    private val textList = mutableListOf<String>()

    fun setTextList(textList: List<String>) {
        this.textList.clear()
        this.textList.addAll(textList)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        textList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun insertItem(position: Int, text: String) {
        textList.add(position, text)
        notifyItemInserted(position)
    }

    // 返回列表大小
    override fun getItemCount(): Int {
        return textList.size
    }

    // getItemViewType返回的必须是布局类型
    override fun getItemViewType(position: Int): Int {
        return R.layout.text_item
    }

    // 进行布局绑定
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        // 可以根据R.id从ViewHolder获取View实例来进行布局绑定
        val textView: TextView? = holder.findViewById(R.id.textView)
        textView?.text = textList[position]
        // 你可以通过EventViewHolder.setTag(...)来设置变量传递,通常id需要保持唯一，建议使用ids.xml来设置id，然后在代码中引用
        // holder.setTag(R.id.xxxx,"")
    }
}