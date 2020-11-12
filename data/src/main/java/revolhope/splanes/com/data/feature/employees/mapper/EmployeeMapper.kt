package revolhope.splanes.com.data.feature.employees.mapper

import revolhope.splanes.com.data.feature.employees.response.FavoriteResponse
import revolhope.splanes.com.data.feature.employees.response.OompaLoompaDetailResponse
import revolhope.splanes.com.data.feature.employees.response.OompaLoompaResponse
import revolhope.splanes.com.domain.feature.employees.model.FavoriteModel
import revolhope.splanes.com.domain.feature.employees.model.Gender
import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaDetailsModel
import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaModel

object EmployeeMapper {

    fun fromEmployeeResponseToModel(response: OompaLoompaResponse): OompaLoompaModel =
        OompaLoompaModel(
            firstName = response.firstName ?: "",
            lastName = response.lastName ?: "",
            favorite = response.favorite?.let(::fromFavoriteResponseToModel) ?: FavoriteModel.empty,
            gender = Gender.parse(response.gender),
            image = response.image ?: "",
            profession = response.profession ?: "",
            email = response.email ?: "",
            age = response.age ?: Int.MIN_VALUE,
            country = response.country ?: "",
            height = response.height ?: Int.MIN_VALUE,
            id = response.id ?: Int.MIN_VALUE
        )

    fun fromEmployeeDetailsResponseToModel(response: OompaLoompaDetailResponse): OompaLoompaDetailsModel =
        OompaLoompaDetailsModel(
            firstName = response.firstName ?: "",
            lastName = response.lastName ?: "",
            favorite = response.favorite?.let(::fromFavoriteResponseToModel) ?: FavoriteModel.empty,
            gender = Gender.parse(response.gender),
            image = response.image ?: "",
            profession = response.profession ?: "",
            email = response.email ?: "",
            age = response.age ?: Int.MIN_VALUE,
            country = response.country ?: "",
            height = response.height ?: Int.MIN_VALUE,
            quota = response.quota ?: "",
            description = response.description ?: ""
        )

    private fun fromFavoriteResponseToModel(response: FavoriteResponse): FavoriteModel =
        FavoriteModel(
            color = response.color ?: "",
            food = response.food ?: "",
            song = response.song ?: ""
        )

}