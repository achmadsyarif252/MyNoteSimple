package com.example.mynotesimple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mynotesimple.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabSave.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.load -> {
                val items = fileList()
                val alert = AlertDialog.Builder(this)
                alert.setTitle("Pilih Note Anda")
                alert.setItems(items) { _, item -> loadData(items[item]) }
                alert.show()
            }
            R.id.add -> {
                binding.edtTitle.text.clear()
                binding.edtDescription.text.clear()
                Toast.makeText(this, "Clearing Note...", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.readFromFile(this, title)
        binding.edtTitle.setText(fileModel.title)
        binding.edtDescription.setText(fileModel.description)
        Toast.makeText(this, "Membuka note ${fileModel.title}", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(p0: View?) {
        when {
            binding.edtTitle.text.isEmpty() -> binding.edtTitle.error = "Note Title harus diisi"
            binding.edtDescription.text.isEmpty() -> binding.edtDescription.error =
                "Note Description Harus diisi"
            else -> {
                val title = binding.edtTitle.text.toString()
                val desc = binding.edtDescription.text.toString()
                val fileModel = FileModel()
                fileModel.title = title
                fileModel.description = desc
                FileHelper.writeToFile(this, fileModel)
                Snackbar.make(
                    window.decorView.rootView,
                    "Berhasil menyimpan $title",
                    Snackbar.LENGTH_SHORT
                ).show()

            }
        }
    }
}