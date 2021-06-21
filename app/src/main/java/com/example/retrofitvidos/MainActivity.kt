package com.example.retrofitvidos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitvidos.databinding.ActivityMainBinding
import com.example.retrofitvidos.databinding.ItemTodoBinding
import retrofit2.HttpException
import java.io.IOException

const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding

    private lateinit var toDoApapter: ToDoApapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()


        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try {
                RetrofitInstance.api.getTodos()
            }catch (e: IOException){
                Log.e(TAG, "IOException")
                return@launchWhenCreated
            }catch (e: HttpException){
                Log.e(TAG, "HttpException")
                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body() != null){
                toDoApapter.todos = response.body()!!
            }else{
                Log.e(TAG, "Response was not successful")
            }
            binding.progressBar.isVisible = false
        }
    }

    private fun setupRecyclerView() = binding.rvTodos.apply {
        toDoApapter = ToDoApapter()
        adapter = toDoApapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}