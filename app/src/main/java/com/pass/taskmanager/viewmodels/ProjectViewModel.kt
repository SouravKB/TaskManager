package com.pass.taskmanager.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pass.taskmanager.models.Project
import com.pass.taskmanager.repositories.PersonRepository
import com.pass.taskmanager.repositories.ProjectRepository
import com.pass.taskmanager.utils.Response
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ProjectViewModel : ViewModel() {

    private val auth = Firebase.auth

    val projects = mutableStateOf<Response<List<Project>>>(Response.Loading)

    fun getProjects() {
        viewModelScope.launch {
            PersonRepository.getProjectIds(auth.uid!!).flatMapLatest {
                flow {
                    if (it is Response.Success)
                        emit(Response.Success(ProjectRepository.getProjectList(it.data!!)))
                    else
                        emit(Response.Loading)
                }
            }.collect {
                projects.value = it
            }
        }
    }
}
