package AmazonAdvertisingApi

import scalaj.http._
import play.api.libs.json._

class Client (clientId: String, clientSecret: String, region: String, accessToken: String, refreshToken: String, sandbox: Boolean = false) {
  def buildRequest(method: String, profileId: String = "0", bodies: JsValue = JsValue()): HttpRequest = {
    val headers = Seq(
      "Content-Type" -> "application/json",
      "Authorization" -> s"Bearer ${this.accessToken}",
      "Amazon-Advertising-API-Scope" -> profileId,
      "Amazon-Advertising-API-ClientId" -> this.clientId
    )
    val request = Http("https://advertising-api-fe.amazon.com/v2").headers(headers)
    method.toUpperCase() match {
      case "GET" => request
      case "POST" => request.postData(Json.stringify(bodies))
    }
  }
}
object Client {
  def apply(config: Config): Client = new Client(config.clientId, config.clientSecret, config.region, config.accessToken, config.refreshToken)
}