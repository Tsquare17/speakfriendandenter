package com.tsquare.speakfriend.api;

import com.tsquare.speakfriend.auth.Auth
import com.tsquare.speakfriend.auth.CurrentUser
import com.tsquare.speakfriend.crypt.Crypt
import com.tsquare.speakfriend.http.Http
import com.tsquare.speakfriend.settings.Options
import org.json.simple.JSONArray
import java.net.URLEncoder

class Api {
    fun register(name: String, email: String, password: String, confirm_password: String): ApiResponse {
        val hash = Crypt.generatePassword(password).toString()
        val key = Crypt.generateKey(hash, password).toString()

        var parameters = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
        parameters += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
        parameters += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")
        parameters += "&" + URLEncoder.encode("password_confirmation", "UTF-8") + "=" +
                URLEncoder.encode(confirm_password, "UTF-8")
        parameters += "&" + URLEncoder.encode("backup_key", "UTF-8") + "=" + URLEncoder.encode(key, "UTF-8")

        val http = Http()

        return http.post("register", parameters)
    }

    fun login(email: String, password: String): ApiResponse {
        var parameters = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
        parameters += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")

        val http = Http()

        return http.post("login", parameters)
    }

    fun logout(): ApiResponse {
        val http = Http()

        return http.get("logout");
    }

    fun createMasterKey(masterKey: String): ApiResponse {
        val hash = Crypt.generatePassword(masterKey).toString()

        Options.put("master_key", hash)
        val auth = Auth()
        auth.setApiPass(masterKey)
        auth.setApiKey(hash, masterKey)

        val parameters = URLEncoder.encode("master_key", "UTF-8") + "=" +
                URLEncoder.encode(hash, "UTF-8")

        val http = Http()

        val url = "create-key"

        return http.post(url, parameters);
    }

    fun getAccounts(): ApiResponse {
        val http = Http()

        val url = getVersion() + "/accounts"

        return http.get(url);
    }

    fun getAccount(id: Int): ApiResponse {
        val http = Http()

        val url = getVersion() + "/accounts/" + id

        return http.sendJson(url, "")
    }

    fun deleteAccount(id: Int): ApiResponse {
        val http = Http()

        val url = getVersion() + "/accounts/" + id + "/delete"

        return http.sendJson(url, "")
    }

    fun sendBackups(list: ArrayList<MutableList<String>>): ApiResponse {
        val http = Http()

        val url = getVersion() + "/backup"

        val key = CurrentUser.apiHash

        var backupKey = ""
        if (key != "") {
            backupKey = ", \"backup_key\": \"$key\""
        }

        val accountsJson = "{\"accounts\": " + JSONArray.toJSONString(list) + backupKey + "}"

        return http.sendJson(url, accountsJson)
    }

    private fun getVersion(): String {
        return "v1"
    }
}
