/*
 * Copyright (C) 2018 Mobile Application Lab
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.mobileapplab.aospchips

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.util.Rfc822Tokenizer
import android.util.Log
import android.widget.MultiAutoCompleteTextView
import com.android.ex.chips.BaseRecipientAdapter
import com.android.ex.chips.RecipientEditTextView
import com.android.ex.chips.RecipientEditTextView.*
import com.android.ex.chips.RecipientEntry

class MainActivity : AppCompatActivity(), PermissionsRequestItemClickedListener, RecipientChipDeletedListener, RecipientChipAddedListener {
    private lateinit var mEmailRetv: RecipientEditTextView
    private lateinit var mPhoneRetv: RecipientEditTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mEmailRetv = findViewById(R.id.email_retv)
        mEmailRetv.setTokenizer(Rfc822Tokenizer())
        val emailAdapter = BaseRecipientAdapter(this)
        emailAdapter.setShowRequestPermissionsItem(true)
        mEmailRetv.setAdapter(emailAdapter)
        mEmailRetv.setPermissionsRequestItemClickedListener(this)
        mPhoneRetv = findViewById(R.id.phone_retv)
        mPhoneRetv.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        val phoneAdapter = BaseRecipientAdapter(BaseRecipientAdapter.QUERY_TYPE_PHONE, this)
        phoneAdapter.setShowRequestPermissionsItem(true)
        mPhoneRetv.setAdapter(phoneAdapter)
        mPhoneRetv.setPermissionsRequestItemClickedListener(this)
        mEmailRetv.setRecipientChipAddedListener(this)
        mEmailRetv.setRecipientChipDeletedListener(this)
    }

    override fun onPermissionsRequestItemClicked(view: RecipientEditTextView, permissions: Array<String>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 0 /* requestCode */)
        }
    }

    override fun onPermissionRequestDismissed() {
        mEmailRetv.adapter.setShowRequestPermissionsItem(false)
        mPhoneRetv.adapter.setShowRequestPermissionsItem(false)
    }

    override fun onRecipientChipAdded(entry: RecipientEntry) {
        Log.i("ChipsSample", "${entry.displayName} recipient chip added")
    }

    override fun onRecipientChipDeleted(entry: RecipientEntry) {
        Log.i("ChipsSample", "${entry.displayName} recipient chip removed")
    }
}