package com. example. mtapi3

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        obtenerProductos()
    }

        private fun obtenerProductos(){
            var listadoTexto = findViewById<TextView>(R.id.productList)
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.jsonkeeper.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(ProductService::class.java)

            val call = service.getProducts()

            call.enqueue(object : Callback<ProductResponse> {

                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {

                    if (response.isSuccessful) {
                        val products = response.body()?.products
                        products?.forEach { product ->
                            listadoTexto.text = listadoTexto.text.toString() + "/n" + "Product: ${product.name} precio: ${product.price}")
                            Log.d(tag: "MainActivity", msg: "Product: ${product.name} precio: ${product.price}")
                        }
                    } else {
                        Log.e(tag: "MainActivity", msg: "API Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    Log.e(tag: "MainActivity", msg: "Error: ${t.message}")

                }
            })

        }
}
