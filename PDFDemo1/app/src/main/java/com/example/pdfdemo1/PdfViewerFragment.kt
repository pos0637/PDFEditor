package com.example.pdfdemo1

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.PathUtils
import com.foxit.sdk.PDFViewCtrl
import com.foxit.sdk.common.Constants
import com.foxit.sdk.common.Library
import com.foxit.sdk.pdf.PDFDoc
import com.foxit.sdk.pdf.interform.Form
import com.foxit.uiextensions.Module
import com.foxit.uiextensions.UIExtensionsManager
import com.foxit.uiextensions.annots.form.FormFillerAnnotHandler
import com.foxit.uiextensions.annots.form.FormFillerModule
import com.foxit.uiextensions.config.Config
import java.io.InputStream


class PdfViewerFragment : Fragment() {

    companion object {

        private const val sn = "SSdtOBZVvlJ1Pi0G6kMpcm82Z4F7wnIVkEVVH6Y7e9puL+0jh0aoiA=="
        private const val key =
            "ezJvjl3GrGpz9JsXIVWofHV+ZehFuFa6bsRoEkOpLqpzBohxF3ZbZ2nKMbbZIJtofMwl4A5j+XALkjpytdB+RYTL5kVy4K1fLq/3/74cynA9ZFmXPXDEaWKcl0iTV9gI2Zj4amUFJHn6k4x42E4tesd8Wj4uX5DsTREiTRukQxpVIZOklPu/7E1fweEjyp/I82j24Y6zKc0JQMjnpElh+ATnQyVsx1MDFgJTlSDHEaxetgqSnF9f53CZEG1QUlh8AQLTkW+VmFcJ3uP7LovKcVQilNoWiV65oWL843T1Q/krZNHMJz4FW21GTtONkM29C+X6UGU+3tMy8vK1ZFFOyxD56vBvS2x39m7wZQM/yLnviArxHkmD2vY5ydfwSTzPhIlClho9eAz+BO3bO1Dsn3qAtxiChHCIiubpmtnORNHbmZwFxFyLK0KQajeT7xKoldESmJ9e69j4RuosS6ycsw3G29iHYo4j5NcnTRAg6DyF0uXi5CIKophIUaEZA5Rnby2xygsx3Sj3hjhUytuSqfTa4fqc0+gx9E3uuPLMv1v4ywZs40fkAeQmm2rq2WRV2lbiZjylgYhnpPiQqndZpLTqqpNrRyYbwAiJPBhAIgfUF8x8spurWWOOpTCTOirtGkBsfL70yxRF8Q82tRK+aM3/40ZcVMZEhan72ZOBjuwZVY2XjKLr1HKc8apkXOUfoXewQCPElCi86YdlmjLTJsJNlqt8HpkE04kGwiqkzZEg1J3aFYSW5dAvdwOg8atUbMNgyO5xfaRbhq7WQvVWO8zYweu5NivfoZ6fR0xmYit7BpClTF7ZFkJkSjmq8o+9dy9u5EiTDnw3oVrCJ01SCO1pTrNTieAifhQfVUVOhr1ruP4HRT7mRvGdqOhYfU+Mv6EE9Hr7E6+Wp0OOrF350vntrlf48ngjaWxMgJOr0YxjfQUMjZ1QiJwSFCQHBikynEr5r0ZruL5DSIlXogeFo6SOvVQiavTmGc8wnH8OVQ9Naaeu5O/hau4M4oaUN4iZpvopYEyOSRezzTK58MRLYBPtmAxuEMwlCf2WOO3zsoYAps1aw/v7kvx1DprtyAuaQPqqlZUS1THY+nn59NcCk4Mg1nTVJ5Ja4gIgwxQbTcZ3TSa/YVf177S/ltu1zMQOWi6pzUJ4LG57939ElFLgrjJuco+MU7nu2CS+ZiRdZsKBx1yHSdvmg9ppG1JfDAj//sdA8Cy5vnI9VW35ZvbK3oOBLEcHeymVID6dwiQ7vJCOGskcDSBWG1gGMbGBtGctGEjir+TZLat5LmuP2NL+AA=="
    }

    private var pdfViewCtrl: PDFViewCtrl? = null
    private var uiExtensionsManager: UIExtensionsManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initialize(activity!!, savedInstanceState)
        return uiExtensionsManager?.contentView
    }

    fun openDocument(): Boolean {
        uiExtensionsManager?.openDocument(
            "${PathUtils.getExternalStoragePath()}/test/testlog.pdf",
            null
        )
        return true
    }

    fun prev() {
        val module = uiExtensionsManager?.getModuleByName(Module.MODULE_NAME_FORMFILLER) as FormFillerModule
        (module.annotHandler as FormFillerAnnotHandler).preNavigation()
    }

    fun next() {
        val module = uiExtensionsManager?.getModuleByName(Module.MODULE_NAME_FORMFILLER) as FormFillerModule
        (module.annotHandler as FormFillerAnnotHandler).nextNavigation()
    }

    private fun initialize(activity: Activity, savedInstanceState: Bundle?) {
        val errorCode = Library.initialize(sn, key)
        if (errorCode != Constants.e_ErrSuccess) {
            Toast.makeText(activity, "SN或KEY错误!", Toast.LENGTH_LONG).show()
            return
        }

        pdfViewCtrl = PDFViewCtrl(activity)
        pdfViewCtrl?.attachedActivity = activity
        pdfViewCtrl?.registerDocEventListener(object : PDFViewCtrl.IDocEventListener {
            override fun onDocWillOpen() {
            }

            override fun onDocOpened(p0: PDFDoc?, p1: Int) {
                try {
                    val form = Form(p0)
                    val count = form.getFieldCount(null)
                    Log.e("", "==================== $count")

                    val field = form.getField(6, null)
                    Log.e(
                        "",
                        "==================== ${field.type}, ${field.controlCount}, ${field.options}"
                    )

                    Log.e("", "==================== ${field.getControl(0).javaClass.name}")
                    Log.e("", "==================== ${field.getControl(0).widget.javaClass.name}")

                    // TODO: get FormFillerAnnoHandler to run navigation thread
                    val module = uiExtensionsManager?.getModuleByName(Module.MODULE_NAME_FORMFILLER) as FormFillerModule
                    Log.e("", "==================== ${module is FormFillerModule}")
                } catch (e: Exception) {
                }
            }

            override fun onDocWillClose(p0: PDFDoc?) {
            }

            override fun onDocClosed(p0: PDFDoc?, p1: Int) {
            }

            override fun onDocWillSave(p0: PDFDoc?) {
            }

            override fun onDocSaved(p0: PDFDoc?, p1: Int) {
            }
        })

        val stream: InputStream = activity.resources.openRawResource(com.example.pdfdemo1.R.raw.uiextensions_config)
        val config = Config(stream)

        uiExtensionsManager = UIExtensionsManager(activity, pdfViewCtrl, config)
        uiExtensionsManager?.attachedActivity = activity
        uiExtensionsManager?.onCreate(activity, pdfViewCtrl, savedInstanceState)
        pdfViewCtrl?.uiExtensionsManager = uiExtensionsManager
    }
}