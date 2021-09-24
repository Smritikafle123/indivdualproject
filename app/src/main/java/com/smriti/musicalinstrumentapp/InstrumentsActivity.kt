package com.smriti.musicalinstrumentapp

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.smriti.musicalinstrumentapp.api.ServiceBuilder
import com.smriti.musicalinstrumentapp.entity.BookingInstrument
import com.smriti.musicalinstrumentapp.entity.Product
import com.smriti.musicalinstrumentapp.notification.NotificationSender
import com.smriti.musicalinstrumentapp.repository.BookingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class InstrumentsActivity : AppCompatActivity(),View.OnClickListener {
    private var instrument:Product? =null
    private lateinit var tvPrice: TextView
    private lateinit var tvProduct: TextView
    private lateinit var tvDescription: TextView
    private lateinit var btnAdd: Button
    private lateinit var ivInstrument: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instruments)
        binding()
        initialize()
        btnAdd.setOnClickListener(this)
    }

    private fun binding()
    {
        tvPrice = findViewById(R.id.tvPrice)
        tvDescription = findViewById(R.id.tvDescription)
        tvProduct = findViewById(R.id.tvProduct)
        btnAdd = findViewById(R.id.btnAdd)
        ivInstrument = findViewById(R.id.ivInstrument)
        instrument = intent.getParcelableExtra("music")
    }

    private fun initialize()
    {

        tvPrice.text = "Rs "+instrument!!.pprice
        tvDescription.text = instrument!!.pdesc
        tvProduct.text = instrument!!.pname

        var imgPath = ServiceBuilder.loadImgPath()+instrument!!.pimage!!.replace("\\","/")
        Glide.with(this@InstrumentsActivity).load(imgPath).into(ivInstrument)

    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.btnAdd->{

                    var booking = BookingInstrument(product_id = instrument,quantity = 1)
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val bookingRepository = BookingRepository()
                            val response = bookingRepository.bookInstrument(booking)
                            if(response.success == true)
                            {
                                NotificationSender(this@InstrumentsActivity,"Added To Cart!!","").createHighPriority()
                                withContext(Dispatchers.Main)
                                {
                                    val snk = Snackbar.make(tvProduct,"Added to Cart", Snackbar.LENGTH_INDEFINITE)
                                    snk.setAction("Go to Cart",View.OnClickListener {
                                        snk.dismiss()
                                    })
                                    snk.show()
                                }
                            }
                            else
                            {
                                withContext(Dispatchers.Main)
                                {
                                    if(response.message == "Item already exists in cart.")
                                    {
                                        val snk = Snackbar.make(tvProduct,"${response.message}", Snackbar.LENGTH_LONG)
                                        snk.setAction("Go to Cart",View.OnClickListener {
                                            snk.dismiss()
                                        })
                                        snk.show()
                                    }
                                    else
                                    {
                                        val snk = Snackbar.make(tvProduct,"${response.message}", Snackbar.LENGTH_LONG)
                                        snk.setAction("OK",View.OnClickListener {
                                            snk.dismiss()
                                        })
                                        snk.show()
                                    }

                                }
                            }

                        }
                        catch (ex: Exception)
                        {
                            withContext(Dispatchers.Main)
                            {
                                val snk = Snackbar.make(tvProduct,"${ex.toString()}", Snackbar.LENGTH_LONG)
                                snk.setAction("OK",View.OnClickListener {
                                    snk.dismiss()
                                })
                                snk.show()
                                println(ex.printStackTrace())
                            }

                        }
                    }

            }
        }
    }
}