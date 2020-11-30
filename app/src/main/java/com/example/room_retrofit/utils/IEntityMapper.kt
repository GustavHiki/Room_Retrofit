package com.example.room_retrofit.utils

interface IEntityMapper<Entity, Model> {
    fun mapFromEntity(entity: Entity): Model

    fun mapToEntity(domainModel: Model): Entity
}