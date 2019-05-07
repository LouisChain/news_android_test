package com.npc.news.models.response

import java.io.Serializable

open class BaseResponse(var message: String?, var statusCode: Int?): Serializable