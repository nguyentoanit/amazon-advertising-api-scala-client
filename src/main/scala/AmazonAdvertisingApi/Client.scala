package AmazonAdvertisingApi

import scalaj.http._
import play.api.libs.json._
import java.net.URL

class Client (clientId: String, clientSecret: String, region: String, accessToken: String, refreshToken: String, sandbox: Boolean = false) {
  def buildRequest(method: HTTPMethod, url: URL, headers: Seq[(String, String)], body: JsValue): HttpRequest = {
    val request = Http(url.toString).headers(headers)
    method match {
      case GET => request
      case POST => request.postData(Json.stringify(body))
    }
  }

  def doRefreshToken: Unit = {
    val headers = Seq()
    val body: JsValue = Json.obj(
      "grant_type" -> "refresh_token",
      "refresh_token" -> this.refreshToken,
      "client_id" -> this.clientId,
      "client_secret" -> this.clientSecret
    )
    val url: URL = new URL("https://api.amazon.com/auth/o2/token")
    this.buildRequest(POST, url, headers, body)
  }
}

object Client {
  def apply(config: Config): Client = new Client(config.clientId, config.clientSecret, config.region, config.accessToken, config.refreshToken)
}