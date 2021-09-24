package com.smriti.musicalinstrumentapp.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.smriti.musicalinstrumentapp.R
import com.smriti.musicalinstrumentapp.api.ServiceBuilder
import com.smriti.musicalinstrumentapp.api.StaticCartModel
import com.smriti.musicalinstrumentapp.entity.BookingInstrument
import com.smriti.musicalinstrumentapp.interfaces.CartRefreshment
import com.smriti.musicalinstrumentapp.repository.BookingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class BookingAdapter(val context: Context, var lstCart:MutableList<BookingInstrument>, var rfr: CartRefreshment):
    RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {
    class BookingViewHolder(val view: View): RecyclerView.ViewHolder(view)
    {
        val tvProduct: TextView
        val tvBrand: TextView
        val tvPrice: TextView
        val ivMinus: ImageView
        val ivAdd: ImageView
        val tvQuantity: TextView
        val ivProduct: ImageView
        val cbCheck: CheckBox
        val progress : ContentLoadingProgressBar
        init {
            tvProduct = view.findViewById(R.id.tvProduct)
            tvBrand = view.findViewById(R.id.tvBrand)
            tvPrice = view.findViewById(R.id.tvPrice)
            ivMinus = view.findViewById(R.id.ivMinus)
            ivAdd = view.findViewById(R.id.ivAdd)
            tvQuantity = view.findViewById(R.id.tvQuantity)
            ivProduct = view.findViewById(R.id.ivProduct)
            cbCheck = view.findViewById(R.id.cbCheck)
            progress = view.findViewById(R.id.progressBar)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_cart,parent,false)
        return BookingViewHolder(view)
    }
    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        var cart = lstCart[position]
      
            holder.tvProduct.text = cart.product_id!!.pname

            holder.tvPrice.text = "Rs "+cart.price.toString()
            holder.tvQuantity.text = cart.quantity.toString()



            var imagePath = ServiceBuilder.loadImgPath()+cart.product_id!!.pimage!!.replace("\\","/")
            Glide.with(context).load(imagePath).into(holder.ivProduct)
            //comes from db

       

        var newPrice = cart.price/cart.quantity
        var currentQuantity = cart.quantity

        //plus minus listener
        holder.ivAdd.setOnClickListener {


                    CoroutineScope(Dispatchers.Main).launch {
                        holder.progress.visibility = View.VISIBLE


                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val repo = BookingRepository()
                            var quantity = cart.quantity+1
                            val response = repo.updateBooking(cart._id,quantity)
                            if(response.success == true)
                            {

                                cart.quantity+=1
                                cart.price = newPrice * cart.quantity
                                withContext(Dispatchers.Main)
                                {
                                    notifyDataSetChanged()
                                    rfr.refreshCartActivity()
                                    holder.progress.visibility = View.GONE
                                }


                            }
                            else
                            {
                                withContext(Dispatchers.Main)
                                {
                                    val snk = Snackbar.make(holder.tvBrand,"${response.message}", Snackbar.LENGTH_LONG)
                                    snk.setAction("Ok", View.OnClickListener {
                                        snk.dismiss()
                                    })
                                    snk.show()
                                    holder.progress.visibility = View.GONE

                                }
                            }
                        }
                        catch (ex: Exception)
                        {
                            withContext(Dispatchers.Main)
                            {
                                val snk = Snackbar.make(holder.tvBrand,"${ex.toString()}", Snackbar.LENGTH_LONG)
                                snk.setAction("Ok", View.OnClickListener {
                                    snk.dismiss()
                                })
                                snk.show()
                                println(ex.printStackTrace())
                            }
                        }
                    }




        }
        holder.ivMinus.setOnClickListener {

                if(cart.quantity > 1)
                {
                    CoroutineScope(Dispatchers.Main).launch {
                        holder.progress.visibility = View.VISIBLE


                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val repo = BookingRepository()
                            var quantity = cart.quantity-1
                            val response = repo.updateBooking(cart._id,quantity)
                            if(response.success == true)
                            {

                                cart.quantity-=1
                                cart.price = newPrice*cart.quantity
                                withContext(Dispatchers.Main)
                                {
                                    notifyDataSetChanged()
                                    rfr.refreshCartActivity()
                                    holder.progress.visibility = View.GONE
                                }


                            }
                            else
                            {
                                withContext(Dispatchers.Main)
                                {
                                    val snk = Snackbar.make(holder.tvBrand,"${response.message}", Snackbar.LENGTH_LONG)
                                    snk.setAction("Ok", View.OnClickListener {
                                        snk.dismiss()
                                    })
                                    snk.show()
                                    holder.progress.visibility = View.GONE

                                }
                            }
                        }
                        catch (ex: Exception)
                        {
                            withContext(Dispatchers.Main)
                            {
                                val snk = Snackbar.make(holder.tvBrand,"${ex.toString()}", Snackbar.LENGTH_LONG)
                                snk.setAction("Ok", View.OnClickListener {
                                    snk.dismiss()
                                })
                                snk.show()
                                println(ex.printStackTrace())
                            }
                        }
                    }
                }







        }
        holder.cbCheck.setOnClickListener {
            if(holder.cbCheck.isChecked == true)
            {
                if(!StaticCartModel.myCart.contains(cart))
                {
                    StaticCartModel.myCart.add(cart)
                    rfr.refreshCartActivity()
                }
            }
            else
            {
                StaticCartModel.myCart.remove(cart)
                rfr.refreshCartActivity()
            }
        }


    }
    override fun getItemCount(): Int {
        return lstCart.size
    }

}