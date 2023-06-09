package com.example.amatiberkah.view.detailsub

import androidx.lifecycle.ViewModel
import com.example.amatiberkah.model.remote.response.DetailCourseResponse
import com.example.amatiberkah.model.remote.response.DetailSubModuleResponse
import com.example.amatiberkah.model.repository.AuthRepository
import com.example.amatiberkah.model.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class DetailSubModuleViewModel @Inject constructor(
    private val course : CourseRepository,
    private val auth : AuthRepository,
): ViewModel() {

    suspend fun getDetailSubModule(
        id: String,
        accessToken: String
    ): Flow<Result<DetailSubModuleResponse>> {
        return course.getDetailSubModule(id, accessToken)
    }

    fun getToken(): Flow<String?> {
        return auth.getToken()
    }
}