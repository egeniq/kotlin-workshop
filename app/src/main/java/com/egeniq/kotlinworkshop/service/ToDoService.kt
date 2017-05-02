package com.egeniq.kotlinworkshop.service

import com.egeniq.kotlinworkshop.model.ToDoItem


object ToDoService {
    private val todoItems = mutableListOf<ToDoItem>()

    init {
        todoItems.add(ToDoItem("1", "Start with a coffee", "Lots of sugar!", true))
        todoItems.add(ToDoItem("2", "Feed the cats", null, false))
        todoItems.add(ToDoItem("3", "Learn to know Kotlin", "And understand it can be very awesome", true))
        todoItems.add(ToDoItem("4", "Have a break", "With some nice discussions and questions", false))
        todoItems.add(ToDoItem("5", "Convince the team to use Kotlin", "With the help of Kotlin!", false))
        todoItems.add(ToDoItem("6", "Never think back about the grey days of Plain old Java", null, true))
    }


    /**
     * Just returns the list we created in the constructor
     */
    fun getTodoList(): MutableList<ToDoItem> = todoItems

    /**
     * Add an item to the list
     */
    fun addOrUpdateItem(item: ToDoItem) {
        todoItems.indexOfFirst { todoItem -> todoItem.id == item.id }
            .let { indexOfItem ->
                if (indexOfItem >= 0) {
                    todoItems[indexOfItem] = item
                } else {
                    todoItems.add(item)
                }
            }
    }

    fun deleteTodoItem(position: Int) {
        if (position >= 0 && position < todoItems.size) {
            todoItems.removeAt(position)
        }
    }
}