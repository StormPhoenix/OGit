package com.stormphoenix.ogit.adapters.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stormphoenix.ogit.adapters.base.MultiTypeAdapter.MultiTypeViewHolder
import java.util.*

/**
 * Created by StormPhoenix on 17-3-28.
 * StormPhoenix is a intelligent Android developer.
 */

abstract class MultiTypeAdapter @JvmOverloads constructor(context: Context, private val inflater: LayoutInflater = LayoutInflater.from(context)) : TypeAdapter<MultiTypeAdapter.Item, MultiTypeViewHolder>(context, LinkedList()) {
    // 存放所有的数据
    //    private final List<MultiTypeAdapter.Item> items;
    // 存放所有的ViewType类型
    private val viewTypeMap: MutableMap<Int, MultiTypeAdapter.ViewType>
    private val viewPosMap: Map<Int, View>

    init {
        val viewItemTypes = itemTypes
        viewTypeMap = HashMap()
        viewPosMap = HashMap()
        val empty = IntArray(0)

        for (i in viewItemTypes.indices) {
            val type = viewItemTypes[i]
            val layoutId = getLayoutId(type)
            var layoutChildrenIds: IntArray? = getChildrentIds(type)

            if (layoutChildrenIds == null) {
                layoutChildrenIds = empty
            }

            val viewType = MultiTypeAdapter.ViewType(type, layoutId, layoutChildrenIds)
            viewTypeMap.put(type, viewType)
        }
    }

    fun clear(): MultiTypeAdapter {
        this.data.clear()
        this.notifyDataSetChanged()
        return this
    }

    /**
     * 获取到所有类型对应的 View 的子View的id
     *
     * @param type
     * @return
     */
    protected abstract fun getChildrentIds(type: Int): IntArray

    fun addItem(type: Int, item: Any): MultiTypeAdapter {
        this.data.add(MultiTypeAdapter.Item(type, item))
        this.notifyDataSetChanged()
        return this
    }

    fun addItems(type: Int, items: Array<Any>?): MultiTypeAdapter {
        if (items != null && items.size != 0) {
            val len = items.size

            for (i in 0..len - 1) {
                val item = items[i]
                this.data.add(MultiTypeAdapter.Item(type, item))
            }

            this.notifyDataSetChanged()
            return this
        } else {
            return this
        }
    }

    fun addItems(type: Int, items: Collection<*>?): MultiTypeAdapter {
        if (items != null && !items.isEmpty()) {
            val iterator = items.iterator()

            while (iterator.hasNext()) {
                val item = iterator.next()
                this.data.add(MultiTypeAdapter.Item(type, item!!))
            }

            this.notifyDataSetChanged()
            return this
        } else {
            return this
        }
    }

    fun removeItem(position: Int): MultiTypeAdapter {
        if (position > 0 && position < this.data.size && this.data.removeAt(position) != null) {
            this.notifyDataSetChanged()
        }
        return this
    }

    override fun getItemCount(): Int {
        return this.data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MultiTypeViewHolder {
        val itemView = this.initialize(viewType, this.inflater.inflate(viewTypeMap[viewType]!!.layout, null))
        return MultiTypeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MultiTypeViewHolder, position: Int) {
        val itemView = holder.itemView
        this.update(position, itemView, this.getItem(position), getItemViewType(position))
    }

    fun getItem(position: Int): Any {
        return this.data[position].item
    }

    override fun getItemId(position: Int): Long {
        return this.data[position].hashCode().toLong()
    }

    /**
     * 获取到所有类型对应的 View 的id
     *
     * @param type
     * @return
     */
    protected abstract fun getLayoutId(type: Int): Int

    /**
     * 获取所有的View的类型
     *
     * @return
     */
    protected abstract val itemTypes: IntArray

    /**
     * 获取position位置的类型
     *
     * @param position
     * @return
     */
    override fun getItemViewType(position: Int): Int {
        return this.data[position].type
    }

    protected fun update(position: Int, view: View, item: Any, type: Int) {
        Log.e(TAG, "update: " + Integer.toHexString(view.id) + " " + type)
        this.setCurrentView(view)
        this.update(position, item, type)
    }

    /**
     * 当 update(int, Object, int) 被调用的时候，可以保证这个 position 对应的itemView
     * 就是本函数里面操作的 View。
     * update(int, Object, int) 函数用户当前的 itemView 进行更新。
     *
     * @param position
     * @param item
     * @param type
     */
    protected abstract fun update(position: Int, item: Any, type: Int)

    override fun addAll(models: MutableList<Item>) {
        // 默认覆盖上层方法
    }

    override fun add(model: Item) {
        // 默认覆盖上层方法
    }

    override fun swap(srcIndex: Int, targetIndex: Int) {
        // 默认覆盖上层方法
    }

    override fun update(model: Item) {
        // 默认覆盖上层方法
    }

    override fun remove(model: Item) {
        // 默认覆盖上层方法
    }

    override fun update(model: Item, removedIndex: Int) {
        // 默认覆盖上层方法
    }

    override fun update(model: Item, removedIndex: Int, insertIndex: Int) {
        // 默认覆盖上层方法
    }

    protected fun initialize(type: Int, view: View): View {
        return super.initialize(view, viewTypeMap[type]?.layoutChildrenIds)
    }

    class MultiTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private class ViewType(val type: Int, val layout: Int, val layoutChildrenIds: IntArray)

    class Item public constructor(val type: Int, val item: Any) {

        override fun hashCode(): Int {
            return this.item.hashCode()
        }
    }

    companion object {
        val TAG = MultiTypeAdapter::class.java.simpleName
    }
}
