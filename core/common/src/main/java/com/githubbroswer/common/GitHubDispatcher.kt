package com.githubbroswer.common

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: GitHubDispatcher)

enum class GitHubDispatcher {
    IO,
    Default
}