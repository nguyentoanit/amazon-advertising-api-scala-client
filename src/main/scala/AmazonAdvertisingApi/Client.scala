package AmazonAdvertisingApi

import scalaj.http._
import play.api.libs.json._
import java.net.URL

class Client (clientId: String, clientSecret: String, refreshToken: String, region: Region, version: String, sandbox: Boolean = false) {
  private var accessToken: String = ""
  private var profileId: String = ""
  private val domain: String = if (sandbox) this.region.sandbox.toString else this.region.production.toString

  def setProfileId(profileId: String) = this.profileId = profileId

  private def buildRequest(method: HTTPMethod, url: URL, headers: Seq[(String, String)], body: JsValue): HttpRequest = {
    val request = Http(url.toString).headers(headers)
    method match {
      case GET => request
      case POST => request.postData(Json.stringify(body))
    }
  }

  def doRefreshToken = {
    val headers = Seq(
      "Content-Type" -> "application/json"
    )
    val body: JsValue = Json.obj(
      "grant_type" -> "refresh_token",
      "refresh_token" -> this.refreshToken,
      "client_id" -> this.clientId,
      "client_secret" -> this.clientSecret
    )
    val url: URL = this.region.tokenUrl
    val request = this.buildRequest(POST, url, headers, body).asString
    val response: JsValue = Json.parse(request.body)

    request.code match {
      case 200 => this.accessToken = (response \ "access_token").as[String]
      case _ =>
        val error = (response \ "error").as[String]
        val errorDescription = (response \ "error_description").as[String]
        throw new Exception(s"$error: $errorDescription")
    }
  }

  def _operation(path: String, method: HTTPMethod = GET, body: JsValue): HttpRequest = {
    var headers: Seq[(String, String)] = Seq(
      "Content-Type" -> "application/json",
      "Authorization" -> s"Bearer ${this.accessToken}",
      "Amazon-Advertising-API-ClientId" -> this.clientId
    )
    if (!this.profileId.trim.isEmpty) headers = headers :+ ("Amazon-Advertising-API-Scope" -> this.profileId)

    val url = new URL(this.domain + this.version + path)
    this.buildRequest(method, url, headers, body)
  }
}

object Client {
  def apply(config: Config): Client = new Client(config.clientId, config.clientSecret, config.refreshToken, config.region, config.version, config.sandbox)
}