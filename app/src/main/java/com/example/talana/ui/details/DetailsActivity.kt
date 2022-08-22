package com.example.talana.ui.details

import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.talana.R
import com.example.talana.data.local.datasource.LocalDataSource
import com.example.talana.data.local.models.User
import com.example.talana.data.local.room.FavouriteDB
import com.example.talana.data.local.room.PostDB
import com.example.talana.data.local.room.UserDB
import com.example.talana.databinding.ActivityDetailsBinding
import com.example.talana.presentation.DetailsViewModel
import com.example.talana.presentation.DetailsViewModelFactory
import com.example.talana.repository.implementations.DetailsRepositoryImpl
import com.example.talana.utils.Resource
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DetailsActivity : AppCompatActivity() {
    companion object {
        const val ID_POST = "id_post"
    }

    private lateinit var binding: ActivityDetailsBinding

    private val viewModel: DetailsViewModel by viewModels<DetailsViewModel> {
        DetailsViewModelFactory(
            DetailsRepositoryImpl(
                LocalDataSource(
                    PostDB.getDatabase(applicationContext).postDao(), FavouriteDB.getDatabase(
                        applicationContext
                    ).favouriteDao(), UserDB.getDatabase(
                        applicationContext
                    ).userDao()
                )
            )
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        val idPost = intent.getLongExtra(ID_POST, -1)

        if (idPost != (-1).toLong()) {
            setFavoriteInfo(idPost)
        }
    }

    private fun setFavoriteInfo(idPost: Long) {
        viewModel.fetchIsFavourite(idPost).observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.pbDetails.visibility = View.VISIBLE
                    binding.detailsLayout.visibility = View.GONE
                }
                is Resource.Success -> {
                    if (it.data == null){
                        binding.favourite.setImageResource(R.drawable.ic_baseline_star_outline_24)
                    } else {
                        binding.favourite.setImageResource(R.drawable.ic_star)
                    }
                    setPostInfo(idPost)
                }
                is Resource.Failure -> {
                    binding.pbDetails.visibility = View.GONE
                    binding.detailsLayout.visibility = View.GONE
                    Toast.makeText(this,"No se ha podido recuperar la informacion.",Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setupActionBar(){
        if (supportActionBar != null) {
            supportActionBar?.title = "Detalles"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setPostInfo(id: Long){
        viewModel.fetchPostInfo(id).observe(this, Observer {
            when(it){
                is Resource.Loading -> {
                    binding.pbDetails.visibility = View.VISIBLE
                    binding.detailsLayout.visibility = View.GONE
                }
                is Resource.Success -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        try {
                            val date = LocalDate.parse(it.data.date, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                            binding.date.text = date.toString()
                        }
                        catch (e : Exception){  binding.date.text = it.data.date }
                    }
                    else
                        binding.date.text = it.data.date

                    setImg(it.data.image)
                    binding.title.text = it.data.title
                    binding.description.movementMethod = ScrollingMovementMethod()
                    binding.description.text = it.data.description
                    setUser(it.data.authorID.toLong())
                }
                is Resource.Failure -> {
                    binding.pbDetails.visibility = View.GONE
                    binding.detailsLayout.visibility = View.GONE
                    Toast.makeText(this,"No se ha podido recuperar la informacion.",Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setUser(userId: Long){
        viewModel.fetchUserInfo(userId).observe(this, Observer {
            when(it){
                is Resource.Loading -> {
                    binding.pbDetails.visibility = View.VISIBLE
                    binding.detailsLayout.visibility = View.GONE
                }
                is Resource.Success -> {
                    setAttributesUser(it.data)
                    binding.pbDetails.visibility = View.GONE
                    binding.detailsLayout.visibility = View.VISIBLE
                }
                is Resource.Failure -> {
                    binding.pbDetails.visibility = View.GONE
                    binding.detailsLayout.visibility = View.GONE
                    Toast.makeText(this,"No se ha podido recuperar la informacion.",Toast.LENGTH_LONG).show()
                }
            }
        })

    }

    private fun setAttributesUser(user : User){
        try {
            val strAvatar = "${user.firstName[0]}${user.lastName[0]}"
            val strName = "${user.firstName} ${user.lastName}"
            binding.strAvatar.text = strAvatar
            binding.name.text = strName
            binding.avatar.setCardBackgroundColor(
                if (user.gender == "F")  resources.getColor(R.color.fuchsia)
                else resources.getColor(R.color.orange)
            )
        }catch (e : Exception){
            binding.strAvatar.text = R.string.ee.toString()
            binding.name.text = R.string.error.toString()
            binding.avatar.setCardBackgroundColor(resources.getColor(R.color.red_dark))
        }
    }
    private fun setImg(url : String){
        Glide.with(this)
            .load(url)
            .transform(CenterCrop())
            .placeholder(R.color.grey_40)
            .error(R.drawable.ic_not_image)
            .into(binding.img)
    }
}
