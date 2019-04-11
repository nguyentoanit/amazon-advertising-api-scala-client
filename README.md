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
val profileId: String = "123"

val response = client.requestReport("sp/campaigns", profileId, data).asString.body
```

#### Get report status
```
client.getReportStatus(reportId, profileId)
```

#### Get report URL
```
client.getReportURL(s"reports/$reportId/download", profileId)
```


## Documentation
- [API Reference](https://advertising.amazon.com/API/docs/v2/guides/get_started)
- [Apply for API Access](https://advertising.amazon.com/about-api)
