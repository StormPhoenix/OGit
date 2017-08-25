package com.stormphoenix.ogit.adapters.base

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import com.stormphoenix.ogit.utils.ViewUtils
import java.util.*

/**
 * Created by StormPhoenix on 17-8-23.
 * StormPhoenix is a intelligent Android developer.
 */
abstract class BaseRecyclerAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH> {
    protected var context: Context
    var data: MutableList<T>
//        set(value) {
//            data.clear()
//            data.addAll(value)
//        }

    var mLastPosition = -1
    var mDuration = 300
    var mInterpolator: Interpolator = LinearInterpolator()

    private var clickedItem: MutableMap<Int, OnInternalViewClickListener<T>> = mutableMapOf()

    constructor(context: Context, data: MutableList<T>) {
        this.context = context
        this.data = data
    }

    open fun add(model: T) {
        data.add(0, model)
        notifyDataSetChanged()
    }

    open fun swap(srcIndex: Int, targetIndex: Int) {
        Collections.swap(data, srcIndex, targetIndex)
        notifyItemMoved(srcIndex, targetIndex)
    }

    open fun update(model: T, removedIndex: Int, insertIndex: Int) {
        data.removeAt(removedIndex)
        data.add(insertIndex, model)
        if (removedIndex == insertIndex) {
            notifyItemChanged(removedIndex)
        } else {
            notifyItemRemoved(removedIndex)
            notifyItemInserted(insertIndex)
        }
    }

    open fun update(model: T, removedIndex: Int) {
        update(model, removedIndex, 0)
    }

    open fun update(model: T) {
        val removedIndex = data.indexOf(model)
        update(model, removedIndex)
    }

    open fun remove(model: T) {
        val position = data.indexOf(model)
        remove(position)
    }

    open fun remove(index: Int) {
        data.removeAt(index)
        notifyItemRemoved(index)
    }

    open fun addAll(data: MutableList<T>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        addInternalViewClickListener(holder.itemView, position, data[position])
    }

    private fun addInternalViewClickListener(itemView: View, position: Int, valuesMap: T) {
        for (idKey in clickedItem.keys) {
            val foundView = itemView.findViewById(idKey)
            if (foundView != null && clickedItem[idKey] != null) {
                foundView.setOnClickListener { clickedItem[idKey]!!.onClick(itemView, foundView, position, valuesMap) }
                foundView.setOnLongClickListener { clickedItem[idKey]!!.onLongClick(itemView, foundView, position, valuesMap) }
            }
        }
    }

    override fun getItemCount(): Int = data.size

    fun addOnViewClickListener(key: Int, listener: OnInternalViewClickListener<T>) {
        clickedItem.put(key, listener)
    }

    protected fun animate(holder: VH, position: Int) {
        if (position > mLastPosition) {
            for (anim in getAnimators(holder.itemView)) {
                anim?.duration = mDuration.toLong()
                anim?.interpolator = mInterpolator
                anim?.start()
            }
            mLastPosition = position
        } else {
            ViewUtils.clear(holder.itemView)
        }
    }

    open protected fun getAnimators(itemView: View): List<Animator> {
        if (itemView.measuredHeight <= 0) {
            val scaleX = ObjectAnimator.ofFloat(itemView, "scaleX", 1.05f, 1.0f)
            val scaleY = ObjectAnimator.ofFloat(itemView, "scaleY", 1.05f, 1.0f)
            return Arrays.asList<Animator>(scaleX, scaleY)
        }
        return Arrays.asList<Animator>(
                ObjectAnimator.ofFloat(itemView, "scaleX", 1.05f, 1.0f),
                ObjectAnimator.ofFloat(itemView, "scaleY", 1.05f, 1.0f))
    }

    interface OnInternalViewClickListener<T> {
        fun onClick(parentV: View, v: View, position: Int?,
                    values: T)

        fun onLongClick(parentV: View, v: View, position: Int?,
                        values: T): Boolean
    }

}