package com.smriti.musicalinstrumentapp.ui.dashboard

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.smriti.musicalinstrumentapp.MainActivity
import com.smriti.musicalinstrumentapp.R
import com.smriti.musicalinstrumentapp.adapter.BookingAdapter
import com.smriti.musicalinstrumentapp.api.StaticCartModel
import com.smriti.musicalinstrumentapp.interfaces.CartRefreshment
import com.smriti.musicalinstrumentapp.repository.BookingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class DashboardFragment : Fragment(),View.OnClickListener,CartRefreshment {

    private lateinit var tvCartDesc : TextView
    private lateinit var btnContinue : Button
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: BookingAdapter
    private lateinit var btnDelete:Button
    private lateinit var checkout: TextView
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.dashboard_fragment, container, false)
        binding(root)
        initialize()

        btnDelete.setOnClickListener(this)
        btnContinue.setOnClickListener(this)
        return root
    }



    private fun binding(v: View) {
        tvCartDesc = v.findViewById(R.id.tvCartDesc)
        btnContinue = v.findViewById(R.id.btnContinue)
        recycler = v.findViewById(R.id.recycler)
        btnDelete = v.findViewById(R.id.btnDelete)
        checkout = v.findViewById(R.id.checkout)


    }

    private fun initialize()
    {


            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val bookingRepository = BookingRepository()
                    val response = bookingRepository.retrieveBooking()
                    if(response.success == true)
                    {
                        StaticCartModel.lstCart = response.data!!

                        withContext(Dispatchers.Main)
                        {
                            loadData()
                        }
                    }
                    else
                    {
                        withContext(Dispatchers.Main)
                        {

                            loadData()
                        }
                    }
                }
                catch (ex: Exception)
                {
                    withContext(Dispatchers.Main)
                    {
                        val snk = Snackbar.make(recycler, "${ex.toString()}", Snackbar.LENGTH_LONG)
                        snk.setAction("Cancel", View.OnClickListener {
                            snk.dismiss()
                        })
                        snk.show()
                        println(ex.printStackTrace())
                    }
                }
            }




    }

    private fun loadData()
    {
        if(StaticCartModel.lstCart.size > 0)
        {
            tvCartDesc.text = "${StaticCartModel.lstCart.size} items in cart."
            adapter = BookingAdapter(requireContext(), StaticCartModel.lstCart, this)
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(requireContext())
            btnContinue.visibility = View.GONE
        }
        else
        {
            tvCartDesc.text = "No items in cart."
            btnContinue.visibility = View.VISIBLE
        }
    }

    override fun refreshCartActivity() {
        if(StaticCartModel.myCart.size > 0)
        {

            var prices = StaticCartModel.myCart.map {
                it.price
            }
            var totalPrice = prices.reduce { acc, i ->
                acc+i
            }

            btnDelete.visibility = View.VISIBLE
            checkout.visibility = View.VISIBLE
            checkout.text = "Total Checkout: Rs ${totalPrice}"
        }
        else
        {
            btnDelete.visibility = View.GONE
            checkout.visibility = View.GONE
            checkout.text = "Total Checkout: Rs ${0}"
        }
    }

    private fun loadContent()
    {
        adapter = BookingAdapter(requireContext(), StaticCartModel.lstCart, this)
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()
        StaticCartModel.myCart = mutableListOf()
        btnDelete.visibility=View.GONE
        checkout.visibility = View.GONE
        if(StaticCartModel.lstCart.size > 0)
        {
            tvCartDesc.text = "${StaticCartModel.lstCart.size} items in cart."
            btnContinue.visibility = View.GONE
        }
        else
        {
            tvCartDesc.text = "No Items in cart."
            btnContinue.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.btnDelete -> {

                if (StaticCartModel.myCart.size > 0) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val repo = BookingRepository()
                            val response = repo.deleteBooking(StaticCartModel.myCart[0]._id)
                            if (response.success == true) {
                                StaticCartModel.lstCart.remove(StaticCartModel.lstCart[0])

                                withContext(Dispatchers.Main)
                                {
                                    loadContent()
                                }
                            } else {
                                withContext(Dispatchers.Main)
                                {
                                    val snk = Snackbar.make(recycler, "${response.message}", Snackbar.LENGTH_LONG)
                                    snk.setAction("Ok", View.OnClickListener {
                                        snk.dismiss()
                                    })
                                    snk.show()
                                }
                            }
                        } catch (ex: Exception) {
                            withContext(Dispatchers.Main)
                            {
                                val snk = Snackbar.make(recycler, "${ex.printStackTrace()}", Snackbar.LENGTH_LONG)
                                snk.setAction("Ok", View.OnClickListener {
                                    snk.dismiss()
                                })
                                println(ex.printStackTrace())
                                snk.show()
                            }
                        }
                    }
                }


            }

            R.id.btnContinue -> {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }
    }








}