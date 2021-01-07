package com.dawan.recyclerview

import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

open class EventViewHolder(itemView: View, viewEvents: List<ViewEvent>?) :
    RecyclerView.ViewHolder(itemView) {
    private val mViewList = SparseArray<View>()

    init {
        viewHolderInit(viewEvents)
    }

    private fun viewHolderInit(viewEvents: List<ViewEvent>?) {
        if (viewEvents == null || viewEvents.isEmpty()) return
        var methodName = ""
        try {
            var view: View?
            var method: Method
            viewEvents.forEach {
                // 获取事件要绑定的View
                view = findViewById(it.viewId)
                if (view == null) throw IllegalArgumentException("Please check view id. No such view : id= ${it.viewId}")
                // 将viewHolder设置到View的Tag里面
                view!!.setTag(R.id.event_view_holder, this)
                // 获取绑定监听器的方法名称
                methodName = it.methodName
                method = view!!.javaClass.getMethod(methodName, *it.methodParameterTypes)
                method.invoke(view, *it.methodParameterInstances)
            }
        } catch (e: NoSuchMethodException) {
            throw IllegalArgumentException("no such method:$methodName")
        } catch (e: IllegalAccessException) {
            throw IllegalArgumentException("method:$methodName don't access.")
        } catch (e: InvocationTargetException) {
            throw IllegalArgumentException("no such method:$methodName invoke error.")
        }
    }

    /**
     * @param id View 的id
     * @return id 对应的View，没找到或者类型错误则返回null
     */
    @Suppress("UNCHECKED_CAST")
    open fun <T : View> findViewById(@IdRes id: Int): T? {
        var view = mViewList[id]
        if (view == null) {
            view = itemView.findViewById(id)
            if (view != null) {
                mViewList.append(id, view)
            }
        }
        return view as? T
    }

    fun setTag(@IdRes id: Int, tag: Any?) {
        itemView.setTag(id, tag)
    }

    fun getTag(@IdRes id: Int): Any? {
        return itemView.getTag(id)
    }

}