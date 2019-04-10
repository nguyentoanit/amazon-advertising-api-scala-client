## Amazon Advertising API Library
Amazon Advertising API Scala Client Library

Base on : https://github.com/amzn/amazon-advertising-api-php-sdk

## Getting Started
#### Instantiate the client
```
import AmazonAdvertisingApi._

val config: Config = Config(
    "CLIENT_ID",
    "CLIENT_SECRET",
    "REFRESH_TOKEN",
    REGION,
    "VERSION",
    SANDBOX
)
val client: Client = Client(config)
```

#### Refresh access token
```
client.doRefreshToken
```

#### Set profile Id
```
client.profileId = "123"
```

#### Request a report
```
import play.api.libs.json.{JsValue, Json}
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val reportDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
val data: JsValue = Json.obj(
    "reportDate" -> reportDate,
    "metrics" -> "campaignName,campaignId"
)
```

#### Get a report
```
import play.api.libs.json.{JsValue, Json}
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val reportDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
val data: JsValue = Json.obj(
    "reportDate" -> reportDate,
    "metrics" -> "campaignName,campaignId"
)

val response = client.requestReport("sp/campaigns", data).asString.body
val reportId: String = (Json.parse(response) \ "reportId").as[String]
val reportLink: URL = client.getReport(reportId)
```

## Documentation
- [API Reference](https://advertising.amazon.com/API/docs/v2/guides/get_started)
- [Apply for API Access](https://advertising.amazon.com/about-api)
