# HTML to PDF Convertor
A simple HTML to PDF convertor for Android

## Download

Add `mavenCentral()` repository in project's `build.gradle`

```gradle
allprojects {
    repositories {
        // Possibly other repositories
        mavenCentral()
    }
}
```

Add dependency in module's `build.gradle`

```gradle
dependencies {
    // Possibly other dependencies
    // Replace the <latest-version> with actual latest version
    implementation 'io.github.mddanishansari:html-to-pdf-convertor:<latest-version>'
}
```
<a href="https://repo1.maven.org/maven2/io/github/mddanishansari/html-to-pdf-convertor/" target="_blank">
  <img src="https://img.shields.io/maven-central/v/io.github.mddanishansari/html-to-pdf-convertor?label=latest-version" />
</a>

## Usage

Create an instance of `HtmlToPdfConvertor` class
```kotlin
val htmlToPdfConvertor = HtmlToPdfConvertor(context)
```

Set optional base URL
```kotlin
htmlToPdfConvertor.setBaseUrl("file:///android_asset/images/") // is null by default
```

To generate dynamic content using JS
```kotlin
htmlToPdfConvertor.setJavaScriptEnabled(true)
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

Checkout [sample code](sample/src/main/java/io/github/mddanishansari/sample/html_to_pdf/MainActivity.kt#L64), also sample [HTML](assets/html_input.html) and [PDF](assets/pdf_output.pdf)
