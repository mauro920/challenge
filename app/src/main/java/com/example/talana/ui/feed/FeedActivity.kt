package com.example.talana.ui.feed

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talana.R
import com.example.talana.data.local.datasource.LocalDataSource
import com.example.talana.data.local.models.Favourite
import com.example.talana.data.local.models.Post
import com.example.talana.data.local.models.User
import com.example.talana.data.local.room.FavouriteDB
import com.example.talana.data.local.room.PostDB
import com.example.talana.data.local.room.UserDB
import com.example.talana.data.remote.api.RetrofitClient
import com.example.talana.data.remote.datasource.FeedRemoteDataSource
import com.example.talana.databinding.ActivityFeedBinding
import com.example.talana.presentation.FeedViewModel
import com.example.talana.presentation.FeedViewModelFactory
import com.example.talana.repository.RepositoryPreferences
import com.example.talana.repository.implementations.FeedRepositoryImpl
import com.example.talana.ui.details.DetailsActivity
import com.example.talana.utils.Resource

class FeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBinding
    private val viewModel: FeedViewModel by viewModels<FeedViewModel> {
        FeedViewModelFactory(
            FeedRepositoryImpl(
                FeedRemoteDataSource(RetrofitClient.apiservice),
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
    private var showOnlyFav = false
    private lateinit var listPost: List<Post>
    private lateinit var listFav: List<Favourite>
    private lateinit var listUser: List<User>
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: FeedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Feed"
        viewModel.fetchFavs()
        setFavObserver()
        fetchPosts()
    }


    private fun setupRecyclerView(posts: List<Post>, users: List<User>,favs: List<Favourite>) {
        layoutManager = LinearLayoutManager(this)
        binding.postList.layoutManager = layoutManager
        adapter = FeedListAdapter(
            this, posts, users, favs,
            ::onSelect,
            ::onSetFav
        )
        binding.postList.adapter = adapter
    }


    private fun onSetFav(idPost: Long) {
        if (listFav.contains(Favourite(idPost))) {
            deleteFav(idPost)
        } else {
            addFav(idPost)
        }
    }

    private fun deleteFav(idPost: Long) {
        viewModel.deleteFav(idPost).observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    Toast.makeText(this, "Se Elimino de favoritos!", Toast.LENGTH_LONG).show()
                    viewModel.fetchFavs()
                }
                is Resource.Failure -> {
                }
            }
        })
    }

    private fun addFav(idPost: Long) =
        viewModel.addFav(RepositoryPreferences(applicationContext).getApiKey(), idPost)
            .observe(this, androidx.lifecycle.Observer {
                Log.d("LIVEDATA",RepositoryPreferences(applicationContext).getApiKey())
                when (it) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        Toast.makeText(
                            this,
                            "Se agrego correctamente a favoritos!",
                            Toast.LENGTH_LONG
                        ).show()
                        viewModel.fetchFavs()
                    }
                    is Resource.Failure -> {
                        Log.d("LIVEDATA",it.exception.message!!)
                    }
                }
            })


    private fun fetchPosts() =
        viewModel.fetchPosts().observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.shimmerItems.visibility = View.VISIBLE
                    binding.postList.visibility = View.GONE
                }
                is Resource.Success -> {
                    listPost = it.data
                    fetchUsers()
                }
                is Resource.Failure -> {
                    Log.d("LIVEDATA", it.exception.message.toString())
                    Toast.makeText(this, it.exception.message, Toast.LENGTH_LONG).show()
                }
            }
        })

    private fun fetchUsers() =
        viewModel.fetchUsers().observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.shimmerItems.visibility = View.VISIBLE
                    binding.postList.visibility = View.GONE
                }
                is Resource.Success -> {
                    listUser = it.data
                    setupRecyclerView(listPost,listUser,listFav)
                    binding.shimmerItems.visibility = View.GONE
                    binding.postList.visibility = View.VISIBLE
                }
                is Resource.Failure -> {
                    Log.d("LIVEDATA",it.exception.message!!)
                }
            }
        })

    private fun onSelect(item: Post) {
        startActivity(Intent(this, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.ID_POST, item.id)
        })
    }

    private fun setFavObserver(){
        viewModel.favsPost.observe(this, Observer { favs ->
            listFav = favs
            if (this::adapter.isInitialized){
                adapter.updateFavourite(favs)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.feed_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.folder_fav -> {
                if (!listPost.isNullOrEmpty()) {
                    showOnlyFav = !showOnlyFav
                    val listPostFav = listPost.filter { post ->
                        listFav.find { favourite -> favourite.id_post == post.id } != null
                    }
                    if (showOnlyFav) {
                        supportActionBar?.title = "Favoritos"
                        adapter.updatePost(listPostFav)
                    } else {
                        supportActionBar?.title = "Feed"
                        adapter.updatePost(listPost)
                    }
                } else {
                    showOnlyFav = !showOnlyFav

                    if (showOnlyFav) {
                        supportActionBar?.title = "Favoritos"
                        binding.emptyViewFavorites.visibility  =View.VISIBLE
                        binding.postList.visibility = View.GONE
                        binding.shimmerItems.visibility = View.GONE
                    } else {
                        supportActionBar?.title = "Feed"
                        binding.emptyViewFavorites.visibility  =View.GONE
                        binding.postList.visibility = View.VISIBLE
                        binding.shimmerItems.visibility = View.GONE
                        adapter.updatePost(listPost)
                    }
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}