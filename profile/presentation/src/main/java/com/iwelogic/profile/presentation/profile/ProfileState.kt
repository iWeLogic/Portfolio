package com.iwelogic.profile.presentation.profile

sealed class ProfileState {
    data object Loading : ProfileState()
    data object Error : ProfileState()
    data class Main(val photo: String = "https://media.licdn.com/dms/image/v2/C4D03AQGb_ud7ivzNgg/profile-displayphoto-shrink_800_800/profile-displayphoto-shrink_800_800/0/1517614699692?e=1743033600&v=beta&t=Tw3qFazog4jK-lUgJ8fFWZh2Tv-YtXL-qehYWJ6luDI") : ProfileState()
}