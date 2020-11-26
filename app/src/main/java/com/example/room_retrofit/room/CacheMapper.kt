package com.example.room_retrofit.room

import com.example.room_retrofit.models.PikabuPostModel
import com.example.room_retrofit.utils.IEntityMapper
import javax.inject.Inject

class CacheMapper
@Inject
constructor() : IEntityMapper<PostCacheEntity, PikabuPostModel> {
    override fun mapFromEntity(entity: PostCacheEntity): PikabuPostModel {
        return PikabuPostModel(
            id = entity.id,
            title = entity.title,
            body = entity.body,
            images = entity.images
        )
    }

    override fun mapToEntity(domainModel: PikabuPostModel): PostCacheEntity {
        return PostCacheEntity(
            id = domainModel.id,
            title = domainModel.title,
            body = domainModel.body,
            images = domainModel.images
        )
    }

    fun mapFromEntityList(entities: List<PostCacheEntity>): List<PikabuPostModel>{
        return entities.map { mapFromEntity(it) }
    }

    fun mapToEntityList(entities: List<PikabuPostModel>): List<PostCacheEntity>{
        return entities.map { mapToEntity(it) }
    }
}