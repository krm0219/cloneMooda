package com.krm0219.mooda.diary

import android.app.Activity
import android.os.Bundle
import com.krm0219.mooda.R

class DefaultDialog : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_default)

//        val method = intent.getStringExtra("method")
//
//
//        if (method == "notSaved") {
//
//            text_dialog_title.visibility = View.GONE
//            text_dialog_message.text = resources.getString(R.string.msg_not_saved)
//
//            btn_dialog_yes.visibility = View.VISIBLE
//            btn_dialog_delete.visibility = View.GONE
//
//        } else {
//
//            text_dialog_title.visibility = View.VISIBLE
//            text_dialog_title.text = resources.getString(R.string.text_diary_delete)
//            text_dialog_message.text = resources.getString(R.string.msg_diary_delete)
//
//            btn_dialog_yes.visibility = View.GONE
//            btn_dialog_delete.visibility = View.VISIBLE
//        }
//
//
//
//
//        btn_dialog_no.setOnClickListener {
//
//
//            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
//            finish()
//        }
//
//        btn_dialog_yes.setOnClickListener {
//
//            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
//            finish()
//        }
//
//        btn_dialog_delete.setOnClickListener {
//
//
//        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
        overridePendingTransition(0, 0)
    }


    //
//    override fun onDestroy() {
//        m_wait_dlg?.let {
//            it.dismiss()
//            m_wait_dlg = null
//        }
//
//        super.onDestroy()
//    }
//
//    fun wait_login_api(b_wait: Boolean) {
//        m_wait_dlg?.let {
//            if (b_wait) {
//                it.show()
//            } else {
//                it.hide()
//            }
//        }
//    }
//
//    fun clickOk() {
//        if (m_action_value.isNotEmpty()) {
//            C038.send(
//                m_handler,
//                SharedPreferenceManager.getInstance().userAgent,
//                SharedPreferenceManager.getInstance().qxId,
//                SharedPreferenceManager.getInstance().qxDeviceId,
//                m_action_key,
//                m_action_value
//            )
//        } else {
//            finish()
//        }
//    }
//
//    fun clickConfirm() {
//
//        C025.send(
//            m_handler,
//            SharedPreferenceManager.getInstance().userAgent,
//            m_action_value,
//            SharedPreferenceManager.getInstance().qxDeviceId,
//            SharedPreferenceManager.getInstance().qxDeviceId,
//            "0",
//            Global.getRiderTime(0)
//        )
//    }
//
//    fun dbUpdateConfirmY(invoice_no: String, rider_time: String) {
//        var query: String =
//            String.format(Def.DB.QUERY.INTEGRATION_LIST.UPDATE_CONFIRM_Y, rider_time, invoice_no)
//        Global.getExecuteQueryResult(this, query)
//
//        query = String.format(Def.DB.QUERY.INTEGRATION_LIST.UPDATE_P2, invoice_no)
//        Global.getExecuteQueryResult(this, query)
//
//        val intent = Intent(MainActivity.FCM_BROADCAST_RECEIVER)
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
//    }
//
//    private fun init() {
//        m_wait_dlg = AnimationProgressDialogActivity(this)
//
//        intent.extras?.let {
//            m_action_key = it.getString(FcmService.KEY.ACTION_KEY.desc, "")
//            m_action_value = it.getString(FcmService.KEY.ACTION_VALUE.desc, "")
//
//            subject_text.text = it.getString(FcmService.KEY.TITLE.desc, "")
//            message_text.text =
//                it.getString(FcmService.KEY.MESSAGE.desc, "")
//        }
//
//        when (FcmService.ACTION_KEY.NOT_DEFINED.getActionKey(m_action_key)) {
//            FcmService.ACTION_KEY.QUICK_ASSIGNMENT -> {
//                confirm_layout.visibility = View.VISIBLE
//            }
//            else -> {
//                layout.visibility = View.VISIBLE
//            }
//        }
//
//        ok_btn.setOnClickListener {
//            clickOk()
//        }
//        confirm_btn.setOnClickListener {
//            clickConfirm()
//        }
//    }
}