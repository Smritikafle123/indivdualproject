package com.smriti.musicalinstrumentapp.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.smriti.musicalinstrumentapp.R
import com.smriti.musicalinstrumentapp.adapter.ProductAdapter
import com.smriti.musicalinstrumentapp.db.ProductDB
import com.smriti.musicalinstrumentapp.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class HomeFragment : Fragment() {
    private val RQ_SPEECH_REC = 100
    private lateinit var tvText: TextView
    private lateinit var imageVoice: ImageView
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var rvProducts: RecyclerView
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.home_fragment, container, false)
        binding(root)
        rvProducts = root.findViewById(R.id.rvProducts)
        adapter = ProductAdapter(requireContext())
        rvProducts.adapter = adapter
        imageVoice.setOnClickListener(){
            openVoiceDialog()
        }
        getProducts()
        return root
    }
    private fun openVoiceDialog() {
        val i= Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak now")
        startActivityForResult(i,RQ_SPEECH_REC)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK){
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            tvText.text= result?.get(0).toString();

        }
    }
    private fun binding(v: View) {
        imageVoice = v.findViewById(R.id.imageVoice)
        tvText= v.findViewById(R.id.tvText)

    }

    private fun getProducts() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val repository = ProductRepository()
                val response = repository.getProducts()
                if (response.success == true) {
                    var products = response.products!!

                    val productDao = ProductDB.getInstance(requireContext()).getProductDAO()
                    productDao.deleteAllProduct()
                    for(product in products) {
                        productDao.insertProduct(product)
                    }

                    products = productDao.getAllProduct()
                    adapter.setList(products)

                }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {

                    Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}