package com.smriti.musicalinstrumentapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smriti.musicalinstrumentapp.InstrumentsActivity
import com.smriti.musicalinstrumentapp.R
import com.smriti.musicalinstrumentapp.api.ServiceBuilder
import com.smriti.musicalinstrumentapp.entity.Product

class ProductAdapter(val context: Context): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    var productList = emptyList<Product>()
    inner class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ivImage: ImageView
        val tvName: TextView
        val tvPrice : TextView
        val tvRating: TextView
        val constraint:ConstraintLayout
        //val btnAdd:Button

        init {
            ivImage = itemView.findViewById(R.id.ivImage)
            tvName = itemView.findViewById(R.id.tvName)
            tvPrice = itemView.findViewById(R.id.tvPrice)
            tvRating = itemView.findViewById(R.id.tvRating)
            constraint = itemView.findViewById(R.id.constraint)
            //btnAdd= itemView.findViewById(R.id.btnAdd)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.tvName.text = product.pname
        holder.tvPrice.text = "Rs. ${product.pprice}"
        holder.tvRating.text = product.prating

        var imagePath = ServiceBuilder.loadImgPath()
        imagePath += product.pimage
        imagePath = imagePath.replace("\\", "/")

        Glide.with(context).load(imagePath).into(holder.ivImage)

        holder.constraint.setOnClickListener {
            val intent = Intent(context, InstrumentsActivity::class.java)
            intent.putExtra("music", product)
            context.startActivity(intent)
        }

    }

    override fun getItemCount() = productList.size

    fun setList(list: List<Product>) {
        productList = list
        notifyDataSetChanged()
    }
}




