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
    implementation 'io.github.nvest-solutions:html-to-pdf-convertor:<latest-version>'
}
```
<a href="https://repo1.maven.org/maven2/io/github/nvest-solutions/html-to-pdf-convertor/" target="_blank">
  <img src="https://img.shields.io/maven-central/v/io.github.nvest-solutions/html-to-pdf-convertor?label=latest-version" />
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
