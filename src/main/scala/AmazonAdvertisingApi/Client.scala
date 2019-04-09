package AmazonAdvertisingApi

import scalaj.http._
import play.api.libs.json._
import java.net.URL

class Client (clientId: String, clientSecret: String, region: String, accessToken: String, refreshToken: String, sandbox: Boolean = false) {
  def buildRequest(method: String, url: URL, headers: Seq[(String, String)], bodies: JsValue = JsValue()): HttpRequest = {
    val request = Http(url.toString).headers(headers)
    method.toUpperCase() match {
      case "GET" => request
      case "POST" => request.postData(Json.stringify(bodies))
    }
  }

  def doRefreshToken: Unit = {

  }
}
object Client {
  def apply(config: Config): Client = new Client(config.clientId, config.clientSecret, config.region, config.accessToken, config.refreshToken)
}