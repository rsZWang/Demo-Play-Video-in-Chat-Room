package com.yhw.playvideoinchatdemo

abstract class MessageType

class MessageText(
    val text: String
): MessageType()

class MessagePicture(
    val pictureUri: String
): MessageType()

class MessageVideo(
    val videoUri: String
): MessageType()

class MessageYT(
    val ytId: String
): MessageType()