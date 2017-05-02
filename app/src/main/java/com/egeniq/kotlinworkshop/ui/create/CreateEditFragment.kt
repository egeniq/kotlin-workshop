package com.egeniq.kotlinworkshop.ui.create

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.egeniq.kotlinworkshop.R
import com.egeniq.kotlinworkshop.model.ToDoItem
import com.egeniq.kotlinworkshop.service.ToDoService
import com.egeniq.kotlinworkshop.ui.details.DetailsFragment
import com.egeniq.kotlinworkshop.util.circularReveal

class CreateEditFragment : Fragment() {
    companion object {
        val ARG_POSITION: String = "arg_position"
        val ARG_REVEAL_X: String = "arg_reveal_x"
        val ARG_REVEAL_Y: String = "arg_reveal_y"

        fun instantiate(position: Int, revealX: Float, revealY: Float): CreateEditFragment {
            val fragment = CreateEditFragment()

            val args: Bundle = Bundle()
            args.putInt(ARG_POSITION, position)
            args.putFloat(ARG_REVEAL_X, revealX)
            args.putFloat(ARG_REVEAL_Y, revealY)

            fragment.arguments = args
            return fragment
        }
    }

    lateinit var idView: TextView
    lateinit var titleView: EditText
    lateinit var descriptionView: EditText
    lateinit var saveButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_edit, container, false)

        idView = view.findViewById(R.id.id_value) as TextView
        titleView = view.findViewById(R.id.title_value) as EditText
        descriptionView = view.findViewById(R.id.description_value) as EditText
        saveButton = view.findViewById(R.id.save_button) as Button

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        if (args != null) {
            val position = args.getInt(DetailsFragment.ARG_POSITION)
            if (position >= 0 && position < ToDoService.getTodoList().size) {
                val toDoItem = ToDoService.getTodoList()[position]
                idView.text = toDoItem.id
                titleView.setText(toDoItem.title, TextView.BufferType.EDITABLE)
                descriptionView.setText(toDoItem.description, TextView.BufferType.EDITABLE)
            }
            saveButton.setOnClickListener { onSaveClicked() }


            val revealX = args.getFloat(DetailsFragment.ARG_REVEAL_X)
            val revealY = args.getFloat(DetailsFragment.ARG_REVEAL_Y)
            view.circularReveal(revealX, revealY)
        } else {
            Toast.makeText(context, "Extra's are empty!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onSaveClicked() {
        //create a model of the data
        val toDoItem = ToDoItem(
                idView.text.toString(),
                titleView.text.toString(),
                descriptionView.text.toString(),
            false
        )

        //give it to the service
        ToDoService.addOrUpdateItem(toDoItem)

        //go back one fragment
        requireActivity().supportFragmentManager.popBackStack()
    }
}