package com.wex.launcher.versions

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

data class McVersion(val id: String, val type: String, val url: String)

object VersionManifest {
    const val MANIFEST_URL = "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json"

    fun fetchReleases(): List<McVersion> {
        val conn = URL(MANIFEST_URL).openConnection() as HttpURLConnection
        conn.connectTimeout = 10_000
        conn.readTimeout = 10_000
        try {
            val body = conn.inputStream.bufferedReader().use { it.readText() }
            val versions = JSONObject(body).getJSONArray("versions")
            return (0 until versions.length())
                .map { versions.getJSONObject(it) }
                .map { McVersion(it.getString("id"), it.getString("type"), it.getString("url")) }
                .filter { it.type == "release" }
        } finally {
            conn.disconnect()
        }
    }
}
