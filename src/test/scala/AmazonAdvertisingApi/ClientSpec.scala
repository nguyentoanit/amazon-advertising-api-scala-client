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
  val profileId: String = "123"
  val client: Client = Client(config)
  val reportDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
  val data: JsValue = Json.obj(
    "reportDate" -> reportDate,
    "metrics" -> "campaignName,campaignId"
  )

  "doRefreshToken" should  {
    "return new access token when Authentication is true" in {
      client.accessToken.trim must beEmpty
      client.doRefreshToken
      client.accessToken.trim.length must beGreaterThan(0)
    }
  }

  "requestReport" should {
    "return HTTPRequest contain a reportID" in {
      client.doRefreshToken
      val response = client.requestReport("sp/campaigns", profileId, data).asString.body
      val reportId: String = (Json.parse(response) \ "reportId").as[String]
      println(reportId)
      reportId must startWith("amzn1.clicksAPI")
    }
  }

  "getReportStatus" should {
    "return instance of ReportStatus" in {
      client.doRefreshToken
      val response = client.requestReport("sp/campaigns", profileId, data).asString.body
      val reportId: String = (Json.parse(response) \ "reportId").as[String]
      val reportStatus = client.getReportStatus(reportId, profileId)
      reportStatus must beAnInstanceOf[ReportStatus]
    }
  }

  "getReportURL" should {
    "return URL when request valid reportID" in {
      def getReport(reportId: String, profileId: String): URL = {
        val reportStatus = client.getReportStatus(reportId, profileId)
        Thread.sleep(5000)
        reportStatus match {
          case ReportSuccess(_,_,_) => client.getReportURL(s"reports/$reportId/download", profileId)
          case ReportFailure(_,status,detail) => throw new Exception(s"$status: $detail")
          case ReportInProgress(_,_,_) => getReport(reportId, profileId)
        }
      }
      client.doRefreshToken
      val response = client.requestReport("sp/campaigns", profileId, data).asString.body
      val reportId: String = (Json.parse(response) \ "reportId").as[String]
      val url = getReport(reportId, profileId)
      println(url)
      url must beAnInstanceOf[URL]
    }
  }
}
