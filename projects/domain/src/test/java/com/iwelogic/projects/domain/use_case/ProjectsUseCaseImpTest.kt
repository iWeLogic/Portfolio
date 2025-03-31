package com.iwelogic.projects.domain.use_case

import com.iwelogic.projects.domain.models.ProjectDomain
import com.iwelogic.projects.domain.repository.ProjectsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.UnknownHostException

class ProjectsUseCaseImpTest {

    private lateinit var repository: ProjectsRepository
    private lateinit var useCase: ProjectsUseCaseImp
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = ProjectsUseCaseImp(repository)
    }

    @Test
    fun `getProjects should return sorted projects when repository call is successful`() = runTest(testDispatcher) {
        val projects = listOf(
            ProjectDomain(id = "2"),
            ProjectDomain(id = "1"),
            ProjectDomain(id = "3")
        )
        coEvery { repository.getProjects() } returns Result.success(projects)

        val result = useCase.getProjects()

        assertTrue(result.isSuccess)
        assertEquals(listOf("1", "2", "3"), result.getOrNull()?.map { it.id })
    }

    @Test
    fun `getProjects should return failure when repository call fails`() = runTest(testDispatcher) {
        val exception = UnknownHostException("No internet")
        coEvery { repository.getProjects() } returns Result.failure(exception)

        val result = useCase.getProjects()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `getProject should fetch from repository when force reload is true`() = runTest(testDispatcher) {
        val project = ProjectDomain(id = "1")
        coEvery { repository.getProjects() } returns Result.success(listOf(project))

        val result = useCase.getProject("1", isForceReload = true)

        assertTrue(result.isSuccess)
        assertEquals("1", result.getOrNull()?.id)
    }

    @Test
    fun `getProject should return failure when project not found`() = runTest(testDispatcher) {
        coEvery { repository.getProjects() } returns Result.success(emptyList())

        val result = useCase.getProject("1", isForceReload = true)

        assertTrue(result.isFailure)
        assertEquals("Project didn't found", result.exceptionOrNull()?.message)
    }
}
