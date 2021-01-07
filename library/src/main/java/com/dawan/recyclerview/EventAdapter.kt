package com.dawan.recyclerview

import android.content.res.Resources
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class EventAdapter<VH : EventViewHolder> : RecyclerView.Adapter<VH>() {
    private val mViewEventsMap = SparseArray<MutableList<ViewEvent>>()

    override fun onCreateViewHolder(parent: ViewGroup, @LayoutRes viewType: Int): VH {
        try {
            val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            return onCreateViewHolder(viewType, view)
        } catch (exception: Resources.NotFoundException) {
            throw IllegalArgumentException("viewType must with R.layout.XX resource !")
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun onCreateViewHolder(@LayoutRes viewType: Int, itemView: View): VH {
        return EventViewHolder(itemView, mViewEventsMap[viewType]) as VH
    }

    fun addViewEvent(@LayoutRes layoutId: Int, vararg viewEvent: ViewEvent): EventAdapter<VH> {
        var viewEvents = mViewEventsMap[layoutId]
        if (viewEvents == null) {
            viewEvents = LinkedList<ViewEvent>()
            mViewEventsMap.append(layoutId, viewEvents)
        }
        viewEvents.addAll(viewEvent.toMutableList())
        return this
    }

    fun removeViewEvents(@LayoutRes layoutId: Int) {
        mViewEventsMap.remove(layoutId)
    }

    fun removeViewEvent(@LayoutRes layoutId: Int, viewEvent: ViewEvent) {
        val viewEvents = mViewEventsMap[layoutId]
        viewEvents?.remove(viewEvent)
    }

    @Suppress("UNCHECKED_CAST")
    fun getViewHolder(view: View): VH? {
        val tagContent = view.getTag(R.id.event_view_holder)
        var viewHolder: VH? = null
        if (tagContent != null) {
            viewHolder = tagContent as? VH
        }
        return viewHolder
    }

    abstract override fun getItemViewType(position: Int): Int

}