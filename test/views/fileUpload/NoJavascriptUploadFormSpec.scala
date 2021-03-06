/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package views.fileUpload

import common.BaseSpec
import org.jsoup.Jsoup
import play.api.i18n.Messages
import views.html.fileUpload.NoJavascriptUploadForm
import play.api.i18n.Messages.Implicits._

class NoJavascriptUploadFormSpec extends BaseSpec {

  val envelopeID = "00000000-0000-0000-0000-000000000000"

  "The NoJavascriptUploadForm" should {

    "contain the correct elements when loaded with no files" in {

      lazy val page = NoJavascriptUploadForm(Seq(), envelopeID)(fakeRequest, applicationMessages)
      lazy val document = Jsoup.parse(page.body)

      //Dynamic button
      document.body.getElementsByAttributeValue("style", "display: none;").size() shouldBe 0
      document.body.getElementById("upload-button-nojs").text() shouldBe Messages("page.fileUpload.upload")
    }

    "contain the correct elements when loaded with 1 or more files" in {

      lazy val page = NoJavascriptUploadForm(files, envelopeID)(fakeRequest, applicationMessages)
      lazy val document = Jsoup.parse(page.body)

      //File upload should be invisible
      document.body.getElementsByAttributeValue("style", "display: none;").size() shouldBe 0
      document.body.getElementById("upload-button-nojs").text() shouldBe Messages("page.fileUpload.upload.another")
    }

  }


}
