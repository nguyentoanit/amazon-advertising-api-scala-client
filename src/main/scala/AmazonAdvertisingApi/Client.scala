package AmazonAdvertisingApi

import scalaj.http._
import play.api.libs.json._
import java.net.URL

class Client (clientId: String, clientSecret: String, region: String, refreshToken: String, sandbox: Boolean = false) {
  private var accessToken: String = ""
  def buildRequest(method: HTTPMethod, url: URL, headers: Seq[(String, String)], body: JsValue): HttpRequest = {
    val request = Http(url.toString).headers(headers)
    method match {
      case GET => request
      case POST => request.postData(Json.stringify(body))
    }
  }

  def doRefreshToken = {
    val headers = Seq()
    val body: JsValue = Json.obj(
      "grant_type" -> "refresh_token",
      "refresh_token" -> this.refreshToken,
      "client_id" -> this.clientId,
      "client_secret" -> this.clientSecret
    )
    val url: URL = new URL("https://api.amazon.com/auth/o2/token")
    val request = this.buildRequest(POST, url, headers, body).asString
    val response: JsValue = Json.parse(request.body)

    request.code match {
      case 200 => this.accessToken = (response \ "access_token").as[String]
      case _ => {
        val error = (response \ "error").as[String]
        val errorDescription = (response \ "error_description").as[String]
        throw new Exception(s"$error: $errorDescription")
      }
    }
  }
}

object Client {
  def apply(config: Config): Client = new Client(config.clientId, config.clientSecret, config.region, config.refreshToken)
}