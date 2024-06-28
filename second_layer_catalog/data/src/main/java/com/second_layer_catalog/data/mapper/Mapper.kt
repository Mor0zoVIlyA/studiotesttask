package com.second_layer_catalog.data.mapper

import com.second_layer_catalog.data.network.models.Goods
import com.second_layer_catalog.domain.DomainGoods

fun Goods.toDomain() = DomainGoods(title = title, itemSlug = slug)