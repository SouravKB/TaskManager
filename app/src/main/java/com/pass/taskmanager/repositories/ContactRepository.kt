package com.pass.taskmanager.repositories

import android.content.Context
import android.provider.ContactsContract
import com.pass.taskmanager.models.Contact
import com.pass.taskmanager.utils.DefaultMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ContactRepository {

    suspend fun getContacts(context: Context): List<Contact> {

        // uri for the table
        val uri = ContactsContract.Data.CONTENT_URI

        // projection selects column
        val projection = arrayOf(
            ContactsContract.Data.DISPLAY_NAME_PRIMARY,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Email.ADDRESS,
        )
        val displayNameIndex = 0
        val phoneIndex = 1
        val emailIndex = 2

        // selection & selection args selects row
        val selection = "${ContactsContract.Data.MIMETYPE}=? OR ${ContactsContract.Data.MIMETYPE}=?"
        val selectionArgs = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
        )

        val phones = DefaultMap(HashMap<String, MutableList<String>>()) { mutableListOf() }
        val emails = DefaultMap(HashMap<String, MutableList<String>>()) { mutableListOf() }

        withContext(Dispatchers.IO) {
            context.contentResolver?.query(uri, projection, selection, selectionArgs, null)?.use {
                while (it.moveToNext()) {
                    phones[it.getString(displayNameIndex)] += it.getString(phoneIndex)
                    emails[it.getString(displayNameIndex)] += it.getString(emailIndex)
                }
            }
        }

        val contacts = mutableListOf<Contact>()
        for (name in phones.keys + emails.keys) {
            contacts += Contact(name, phones[name], emails[name])
        }
        return contacts
    }
}
