package com.egeniq.kotlinworkshop.ui.details

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.TextView
import android.widget.Toast
import com.egeniq.kotlinworkshop.R
import com.egeniq.kotlinworkshop.service.ToDoService
import com.egeniq.kotlinworkshop.ui.MainActivity
import com.egeniq.kotlinworkshop.ui.create.CreateEditFragment
import com.egeniq.kotlinworkshop.util.circularReveal

class DetailsFragment : Fragment() {
    companion object {
        val ARG_POSITION: String = "arg_position"
        val ARG_REVEAL_X: String = "arg_reveal_x"
        val ARG_REVEAL_Y: String = "arg_reveal_y"

        fun instantiate(position: Int, revealX: Float, revealY: Float): DetailsFragment {
            val fragment = DetailsFragment()

            val args: Bundle = Bundle()
            args.putInt(ARG_POSITION, position)
            args.putFloat(ARG_REVEAL_X, revealX)
            args.putFloat(ARG_REVEAL_Y, revealY)

            fragment.arguments = args
            return fragment
        }
    }

    lateinit var idView: TextView
    lateinit var titleView: TextView
    lateinit var descriptionView: TextView
    var position: Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        idView = view?.findViewById(R.id.id_value) as TextView
        titleView = view.findViewById(R.id.title_value) as TextView
        descriptionView = view.findViewById(R.id.description_value) as TextView


        setHasOptionsMenu(true)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        if (args != null) {
            position = args.getInt(DetailsFragment.ARG_POSITION)
            if (position >= 0 && position < ToDoService.getTodoList().size) {
                val toDoItem = ToDoService.getTodoList()[position]
                idView.text = toDoItem.id
                titleView.setText(toDoItem.title, TextView.BufferType.EDITABLE)
                descriptionView.setText(toDoItem.description, TextView.BufferType.EDITABLE)

            } else {
                Toast.makeText(context, "That position is not inside the ToDo-list!", Toast.LENGTH_SHORT).show()
            }

            val revealX = args.getFloat(DetailsFragment.ARG_REVEAL_X)
            val revealY = args.getFloat(DetailsFragment.ARG_REVEAL_Y)
            view.circularReveal(revealX, revealY)
        } else {
            Toast.makeText(context, "Extra's are empty!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val itemId = item?.itemId
        if(itemId == R.id.action_details_edit){
            if (view != null && activity is MainActivity) {
                (activity as MainActivity).showFragment(CreateEditFragment.instantiate(position, (view as View).width.toFloat(), 0f))
            }
            return true
        }else if(itemId == R.id.action_details_delete){
            ToDoService.deleteTodoItem(position)
            requireActivity().supportFragmentManager.popBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
