package com.recipe.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dosplash.utils.APIClient
import com.recipe.R
import com.recipe.Utils.ApiCall
import com.recipe.Utils.Utils
import com.recipe.adapter.RecyclerViewAdapter
import com.recipe.asyncPkg.DeleteItemAsync
import com.recipe.asyncPkg.GetAllListAsync
import com.recipe.asyncPkg.GetSearchAsync
import com.recipe.asyncPkg.UpdateDbAsync
import com.recipe.interfacePkg.APIInterface
import com.recipe.interfacePkg.ApiCallBack
import com.recipe.model_db.RecipeModel
import com.recipe.model_db.Recipes
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call


class MainActivity : BaseActivity(), ApiCallBack {

    private var searchTxt: String = ""
    private var pageNo: Int = 1
    private var recyclerAdapter: RecyclerViewAdapter? = null
    private var listRecipe: ArrayList<Recipes>? = null
    private var stopPagination: Boolean = false

    private var handler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                0 -> {
                    listRecipe?.addAll(msg.obj as ArrayList<Recipes>)
                    recyclerAdapter?.notifyDataSetChanged()
                }
                2 -> {
                    searchTxt = msg.obj as String
                }
            }
        }
    }

    private val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder,
                target: ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.LEFT) {
                    DeleteItemAsync(application).execute(listRecipe?.get(viewHolder.layoutPosition))
                    listRecipe?.remove(listRecipe?.get(viewHolder.layoutPosition))
                    recyclerAdapter?.notifyItemRemoved(viewHolder.layoutPosition)
                } else {
                    recyclerAdapter?.notifyItemChanged(viewHolder.layoutPosition)
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(listRecipe?.get(viewHolder.layoutPosition)?.source_url)
                    )
                    startActivity(browserIntent)
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listRecipe = ArrayList()
        recyclerAdapter = RecyclerViewAdapter(this, listRecipe!!)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter
        recyclerView.addOnScrollListener(EndlessRecyclerOnScrollListener(recyclerView.layoutManager as LinearLayoutManager))

        val gt = GetAllListAsync(application, handler)
        gt.execute()
        GetSearchAsync(application, handler).execute()

        imageViewSearch.setOnClickListener {
            Utils.hideKeyBoard(this)
            if (!Utils.isNetworkAvailable(this)) {
                Toast.makeText(this, "No internet", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            searchTxt = edtSearch.text.toString().trim()
            if (searchTxt.isEmpty()) {
                Toast.makeText(this, "Enter Search text", Toast.LENGTH_LONG).show()
            } else {
                pageNo = 1
                val endOfList: Int = listRecipe?.size ?: 0
                listRecipe?.clear()
                recyclerAdapter?.notifyItemRangeRemoved(0, endOfList)
                callListApi()
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)
    }

    private fun callListApi() {
        progressBar.visibility = View.VISIBLE
        val apiInterface: APIInterface =
            APIClient.getClient().create(APIInterface::class.java)
        val callList: Call<RecipeModel>? = apiInterface.doGetRecipeList(searchTxt, pageNo)
        ApiCall.apiRandomCall(1, callList, this)
    }


    override fun onRequestSuccess(reqType: Int, result: Any?) {
        progressBar.visibility = View.GONE
        if (result is RecipeModel) {
            if (result.recipes.isEmpty()){
                stopPagination = result.recipes.isEmpty()
                Toast.makeText(this,"No data for searched item",Toast.LENGTH_LONG).show()
            }
            val startOfList: Int = listRecipe?.size ?: 0
            listRecipe?.addAll(result.recipes)
            recyclerAdapter?.notifyItemRangeInserted(startOfList, listRecipe?.size ?: 0)
            UpdateDbAsync(application, true, searchTxt).execute(result)
        } else {
            stopPagination = true;
        }
    }

    override fun onRequestFail(reqType: Int, result: String?) {
        progressBar.visibility = View.GONE
        stopPagination = true;
        Toast.makeText(this, result, Toast.LENGTH_LONG).show()
    }


    inner class EndlessRecyclerOnScrollListener(
        layoutManager: LinearLayoutManager
    ) : RecyclerView.OnScrollListener() {
        private var mLayoutManager: LinearLayoutManager = layoutManager
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val visibleItemCount: Int = mLayoutManager.childCount
            val firstVisibleItem: Int = mLayoutManager.findFirstVisibleItemPosition()
            val totalItemCount: Int = mLayoutManager.itemCount

            val l = visibleItemCount + firstVisibleItem
            if (l >= totalItemCount - 4 && !stopPagination && (listRecipe?.size ?: 0) > 1
                && searchTxt.isNotEmpty()
            ) {
                stopPagination = true
                pageNo++
                callListApi()
            }
        }
    }
}