package com.egeniq.kotlinworkshop.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.egeniq.kotlinworkshop.R
import com.egeniq.kotlinworkshop.model.ToDoItem
import com.egeniq.kotlinworkshop.service.ToDoService
import com.egeniq.kotlinworkshop.ui.details.DetailsFragment

/**
 * A placeholder fragment containing a simple view.
 */

class MainFragment : Fragment() {
    private val GRID_COL_COUNT = 2
    private var mainRecycler: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        mainRecycler = view.findViewById(R.id.main_recycler) as RecyclerView?
        return view
    }

    override fun onStart() {
        super.onStart()

        if (mainRecycler != null) {
            val recycler = mainRecycler as RecyclerView
            recycler.layoutManager = GridLayoutManager(context, GRID_COL_COUNT)
            recycler.adapter = ToDoRecyclerAdapter(items = ToDoService.getTodoList(),
                    clickListener = object : ToDoRecyclerAdapter.OnTodoItemClickListener {
                        override fun onClick(position: Int, item: ToDoItem, revealX: Float, revealY: Float) {
                             (activity as MainActivity).showFragment(DetailsFragment.instantiate(position, revealX, revealY))
                        }
                    })
        }
    }

    private class ToDoRecyclerAdapter(val items: MutableList<ToDoItem>, val clickListener: OnTodoItemClickListener) : RecyclerView.Adapter<ToDoRecyclerAdapter.TodoViewHolder>() {

        override fun getItemCount(): Int = items.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
            return TodoViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
            val toDoItem = items[position]

            if (toDoItem.description == null) {
                toDoItem.description = "No description"
            }

            if (holder.itemView != null) {
                (holder.itemView.findViewById(R.id.title_textview) as? TextView)?.text = toDoItem.title
                (holder.itemView.findViewById(R.id.description_textview) as? TextView)?.text = toDoItem.description
                holder.itemView.setOnClickListener({ view -> clickListener.onClick(position, toDoItem, view.x + view.width / 2, view.y + view.height / 2) })
            }
        }

        private class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        interface OnTodoItemClickListener {
            fun onClick(position: Int, item: ToDoItem, revealX: Float, revealY: Float)
        }
    }
}
