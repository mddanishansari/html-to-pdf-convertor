# Html to Pdf Convertor
Does what it says

### Usage

Create an instance of `HtmlToPdfConvertor` class
```kotlin
val htmlToPdfConvertor = HtmlToPdfConvertor(context)
```

Start the conversion by calling `convert` function

```kotlin
htmlToPdfConvertor.convert(
  pdfLocation = pdfLocation, // the file location where pdf should be saved
  htmlString = htmlString, // the HTML string to be converted
  onPdfGenerationFailed = { exception -> // something went wrong, handle the exception (this param is optional) 
      exception.printStackTrace()
  },
  onPdfGenerated = { pdfFile -> // pdf was generated, do whatever you want with it
      openPdf(pdfFile)
  })
```

> Storage permission is required to save pdf file on storage

Checkout [sample code](https://github.com/nvest-solutions/html-to-pdf-convertor/blob/4f4cf6a7793ba989a8d78b27eea241a5867d2327/sample/src/main/java/org/nvest/sample/html_to_pdf/MainActivity.kt#L62)
