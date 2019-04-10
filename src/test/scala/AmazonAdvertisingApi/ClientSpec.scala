package AmazonAdvertisingApi

import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.specs2.mutable._
import play.api.libs.json.{JsValue, Json}

class ClientSpec extends Specification {
  val config: Config = Config(
    "amzn1.application-oa2-client.xxxx",
    "xxxx",
    "Atzr|xxx",
    NA,
    "v2",
    true
  )
  val client: Client = Client(config)
  client.profileId = "123"
  val reportDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
  val data: JsValue = Json.obj(
    "reportDate" -> reportDate,
    "metrics" -> "campaignName,campaignId"
  )

  "doRefreshToken" should  {
    "return new access token when Authentication is true" in {
      client.accessToken must beEmpty
      client.doRefreshToken
      client.accessToken.length must beGreaterThan(0)
    }
  }

  "requestReport" should {
    "return HTTPRequest contain a reportID" in {
      client.doRefreshToken
      val response = client.requestReport("sp/campaigns", data).asString.body
      val reportId: String = (Json.parse(response) \ "reportId").as[String]
      println(reportId)
      reportId must startWith("amzn1.clicksAPI")
    }
  }

  "getReport" should {
    "return Download report link" in {
      client.doRefreshToken
      val response = client.requestReport("sp/campaigns", data).asString.body
      val reportId: String = (Json.parse(response) \ "reportId").as[String]
      val reportLink: URL = client.getReport(reportId)
      println(reportLink)
      reportLink.toString must contain("s3.amazonaws.com")
    }
  }
}
