package com.card.data

import android.content.Context
import android.content.Intent
import com.card.data.mapper.toDomain
import com.card.data.network_model.NetworkApi
import com.card.domain.CardRepository
import com.card.domain.DomainGoodInfo
import com.card.domain.NONE
import com.card.domain.SharedModel
import com.example.networks.Url
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val networkApi: NetworkApi,
    @ApplicationContext private val context: Context,
    @Url
    private val baseUrl: String
) : CardRepository {
    override suspend fun fetchFullItemInfo(slug: String): DomainGoodInfo {
        try {
            val data = networkApi.getFullInfo(slug)
            return data.toDomain(baseUrl)
        }catch (e: Exception){
            if (e is CancellationException)
                throw e
        }
        return NONE
    }

    override fun share(sharedModel: SharedModel) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "${sharedModel.name} ${sharedModel.sku}")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Share via")
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(shareIntent)
    }
}