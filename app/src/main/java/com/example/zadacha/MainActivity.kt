package com.example.zadacha

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etAddress: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnAdd: Button
    private lateinit var listViewContacts: ListView
    private lateinit var btnSearch: Button
    private lateinit var btnDelete: Button
    private lateinit var etSearchName: EditText

    private val contactList = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>
    private var selectedContactPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.etName)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etAddress = findViewById(R.id.etAddress)
        etEmail = findViewById(R.id.etEmail)
        btnAdd = findViewById(R.id.btnAdd)
        listViewContacts = findViewById(R.id.listViewContacts)
        btnSearch = findViewById(R.id.btnSearch)
        btnDelete = findViewById(R.id.btnDelete)
        etSearchName = findViewById(R.id.etSearchName)

        val customAdapter = Adapter(this, contactList)
        listViewContacts.adapter = customAdapter

        btnAdd.setOnClickListener {
            val name = etName.text.toString()
            val phoneNumber = etPhoneNumber.text.toString()
            val address = etAddress.text.toString()
            val email = etEmail.text.toString()

            val contactInfo = "$name\n$phoneNumber\n$address\n$email"
            contactList.add(contactInfo)
            adapter.notifyDataSetChanged()

            etName.text.clear()
            etPhoneNumber.text.clear()
            etAddress.text.clear()
            etEmail.text.clear()
        }

        listViewContacts.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                selectedContactPosition = position
                btnDelete.isEnabled = true
            }

        btnSearch.setOnClickListener {
            val searchName = etSearchName.text.toString()
            val searchResults = mutableListOf<String>()

            for (contactInfo in contactList) {
                val name = contactInfo.substringBefore("\n")
                if (name.contains(searchName, ignoreCase = true)) {
                    searchResults.add(contactInfo)
                }
            }

            val searchResultsAdapter =
                ArrayAdapter(this, android.R.layout.simple_list_item_1, searchResults)
            listViewContacts.adapter = searchResultsAdapter

            btnDelete.isEnabled = false
            selectedContactPosition = -1
            adapter.clear()
            adapter.addAll(contactList)
            adapter.notifyDataSetChanged()

            customAdapter.updateList(searchResults)
        }


        btnDelete.setOnClickListener {
            if (selectedContactPosition != -1) {
                contactList.removeAt(selectedContactPosition)
                adapter.notifyDataSetChanged()
                selectedContactPosition = -1
                btnDelete.isEnabled = false
            }
        }
    }

}