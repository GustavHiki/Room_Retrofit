package com.example.room_retrofit.network

import com.example.room_retrofit.models.PikabuPostModel
import com.example.room_retrofit.utils.IEntityMapper
import javax.inject.Inject

class NetworkMapper
@Inject
constructor() : IEntityMapper<PostNetworkEntity, PikabuPostModel> {
    override fun mapFromEntity(entity: PostNetworkEntity): PikabuPostModel {
        return PikabuPostModel(
            id = entity.id,
            title = entity.title,
            body = entity.body,
            images = entity.images
        )
    }

    override fun mapToEntity(domainModel: PikabuPostModel): PostNetworkEntity {
        return PostNetworkEntity(
            id = domainModel.id,
            title = domainModel.title,
            body = domainModel.body,
            images = domainModel.images
        )
    }

    fun mapFromEntityList(entities: List<PostNetworkEntity>): List<PikabuPostModel>{
        return entities.map { mapFromEntity(it) }
    }
}