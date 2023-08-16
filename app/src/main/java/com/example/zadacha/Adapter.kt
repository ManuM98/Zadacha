package com.example.zadacha

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class Adapter (private val context: Context, private var contactList: List<String>) : BaseAdapter() {
        override fun getCount(): Int = contactList.size
        override fun getItem(position: Int): Any = contactList[position]
        override fun getItemId(position: Int): Long = position.toLong()


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val contactInfo = getItem(position) as String
            val inflater = LayoutInflater.from(context)
            val itemView = inflater.inflate(R.layout.item_contact, parent, false)

            val tvName = itemView.findViewById<TextView>(R.id.tvName)
            val tvPhoneNumber = itemView.findViewById<TextView>(R.id.tvPhoneNumber)
            val tvAddress = itemView.findViewById<TextView>(R.id.tvAddress)
            val tvEmail = itemView.findViewById<TextView>(R.id.tvEmail)

            val name = contactInfo.substringBefore("\n")
            val phoneNumber = contactInfo.substringAfter("\n").substringBefore("\n")
            val address = contactInfo.substringAfter("\n").substringAfter("\n").substringBefore("\n")
            val email = contactInfo.substringAfterLast("\n")

            tvName.text = name
            tvPhoneNumber.text = phoneNumber
            tvAddress.text = address
            tvEmail.text = email

            return itemView
        }
    fun updateList(newList: List<String>) {
        contactList = newList
        notifyDataSetChanged()
    }
    }
