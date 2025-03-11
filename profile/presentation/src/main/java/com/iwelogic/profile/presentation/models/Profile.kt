package com.iwelogic.profile.presentation.models

data class Profile(
    val rate: String,
    val surname: String,
    val name: String,
    val about: String,
    val avatar: String,
    val years: String,
    val position: String
) {
    companion object {
        val preview = Profile(
            rate = "35",
            surname = "Novak",
            name = "Nazar",
            about = "    Hi, I'm an Android developer with over eight years of experience in the field. During my career, I’ve developed more than 30 apps, with most of them being built from the ground up. I don’t just code — I can lead teams, gather requirements directly from clients, estimate project timelines, and coordinate mobile, backend, and frontend teams to deliver complete solutions.\\\\n     I’ve worked with companies like iAgentur, IntelliBoard, and Mediapulsas, while also establishing myself as a reliable freelancer.\\\\n     I’m a native speaker of Russian and Ukrainian, fluent in English, which helps me collaborate effectively with clients and teams worldwide. I’m passionate about creating apps that are both impactful and user-friendly!",
            avatar = "https://media.licdn.com/dms/image/v2/C4D03AQGb_ud7ivzNgg/profile-displayphoto-shrink_800_800/profile-displayphoto-shrink_800_800/0/1517614699692?e=1743033600&v=beta&t=Tw3qFazog4jK-lUgJ8fFWZh2Tv-YtXL-qehYWJ6luDI",
            years = "8",
            position = "Android Developer"
        )
    }
}
