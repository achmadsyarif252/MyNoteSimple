package com.example.mynotesimple

import android.content.Context

internal object FileHelper {
    fun writeToFile(context: Context, fileModel: FileModel) {
        context.openFileOutput(fileModel.title, Context.MODE_PRIVATE).use {
            it.write(fileModel.description?.toByteArray())
        }
    }

    fun readFromFile(context: Context, title: String): FileModel {
        val fileModel = FileModel()
        fileModel.title = title
        fileModel.description = context.openFileInput(title).bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                "$some$text"
            }
        }
        return fileModel
    }
}