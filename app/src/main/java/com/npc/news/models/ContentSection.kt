package com.npc.news.models

data class Content(val text: String?, val caption: String?, val href: String?)
data class ContentSection(val section_type: Int?, val content: Content?)