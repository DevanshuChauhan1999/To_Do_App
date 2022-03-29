package com.devanshu.todoapp.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.devanshu.todoapp.R
import com.devanshu.todoapp.data.models.Priority
import com.devanshu.todoapp.data.models.ToDoData
import com.devanshu.todoapp.data.viewmodel.ToDoViewModel
import com.devanshu.todoapp.databinding.FragmentAddBinding
import com.devanshu.todoapp.databinding.FragmentListBinding
import com.devanshu.todoapp.fragments.SharedViewModel

class AddFragment : Fragment() {
    
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentAddBinding.inflate(inflater, container, false)

        //Set Menu
        setHasOptionsMenu(true)

        // Spinner Item Selected Listener for Color
        binding.prioritiesSpinner.onItemSelectedListener = mSharedViewModel.listener


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add){
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val mTitle = binding.titleEt.text.toString()
        val mPriorities = binding.prioritiesSpinner.selectedItem.toString()
        val mDescription = binding.descriptionEt.text.toString()
        
        val validation = mSharedViewModel.verifyDataFromUser(mTitle,mDescription)
        if (validation){
            //Insert Data to DataBase
            val newData = ToDoData(
                0,
                mTitle,
                mSharedViewModel.parsePriority(mPriorities),
                mDescription
            )
            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully Added", Toast.LENGTH_SHORT).show()
            
            //Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else{
            Toast.makeText(requireContext(), "Please Fill All Fields", Toast.LENGTH_SHORT).show()
        }
        
    }

}