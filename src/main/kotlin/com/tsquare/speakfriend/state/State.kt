package com.tsquare.speakfriend.state

object State {
    @JvmStatic
    var isCloudAuthed = 0

    @JvmStatic
    var isDirtyAccounts = 1

    @JvmStatic
    var loadingMessage = ""

    @JvmStatic
    var selectedAccountId = 0

    @JvmStatic
    var isCloudKeySet = 0
}
