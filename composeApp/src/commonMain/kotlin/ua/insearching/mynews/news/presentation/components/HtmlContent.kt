package ua.insearching.mynews.news.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Element

@Composable
fun HtmlContent(
    html: String,
    modifier: Modifier = Modifier,
) {
    val document = remember(html) { Ksoup.parse(html) }

    val scrollState = rememberScrollState()
    Column(
        modifier = modifier.fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        document.body().children().forEach { element ->
            RenderHtmlElement(element)
        }
    }
}

@Composable
fun RenderHtmlElement(element: Element) {
    when (element.tagName()) {
        "p" -> {
            Text(
                text = element.text(),
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        "h1", "h2", "h3", "h4" -> {
            val style = when (element.tagName()) {
                "h1" -> TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold)
                "h2" -> TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                "h3" -> TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                "h4" -> TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                else -> TextStyle(fontSize = 16.sp)
            }
            Text(
                text = element.text(),
                style = style,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        "a" -> {
            val linkText = element.text()

            ClickableText(
                text = AnnotatedString(
                    linkText,
                    spanStyles = listOf(
                        AnnotatedString.Range(
                            SpanStyle(
                                color = Color.Blue,
                                textDecoration = TextDecoration.Underline
                            ),
                            0,
                            linkText.length
                        )
                    )
                ),
                onClick = { /* Handle click (open browser, etc.) */ },
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        "pre" -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(
                        MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = element.text(),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }

        "img" -> {
            val imageUrl = element.attr("src")
            val painter = rememberAsyncImagePainter(model = imageUrl)
            Image(
                painter = painter,
                contentDescription = "Story Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
                    .aspectRatio(16f / 9f),
            )
        }

        else -> {
            // Recursive rendering for nested elements
            element.children().forEach { child ->
                RenderHtmlElement(child)
            }
        }
    }
}