package com.insearching.pickstream.news.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import coil3.compose.rememberAsyncImagePainter
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Element

@Composable
fun HtmlContent(
    html: String,
    modifier: Modifier = Modifier,
    onLinkClick: (String) -> Unit
) {
    val document = remember(html) { Ksoup.parse(html) }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        document.body().children().forEach { element ->
            RenderHtmlElement(
                element,
                onLinkClick
            )
        }
    }
}

@Composable
fun RenderHtmlElement(
    element: Element,
    onLinkClick: (String) -> Unit
) {
    val defaultTextSize = 20.sp
    val horizontalPadding = Modifier.padding(horizontal = 16.dp)
    val textStyle = FontFamily.Serif
    val defaultTextStyle = TextStyle(
        fontSize = defaultTextSize,
        fontFamily = textStyle
    )
    val headerTextStyle = TextStyle(
        fontSize = 18.sp,
        fontFamily = textStyle,
        fontWeight = FontWeight.Bold
    )
    when (element.tagName()) {
        "p" -> {
            Text(
                text = element.text(),
                style = defaultTextStyle,
                modifier = Modifier.then(horizontalPadding)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        "h1", "h2", "h3", "h4" -> {
            val style = when (element.tagName()) {
                "h1" -> headerTextStyle.copy(fontSize = 26.sp)
                "h2" -> headerTextStyle.copy(fontSize = 24.sp)
                "h3" -> headerTextStyle.copy(fontSize = 22.sp)
                "h4" -> headerTextStyle.copy(fontSize = 20.sp)
                else -> headerTextStyle
            }
            Text(
                text = element.text(),
                style = style,
                modifier = Modifier.padding(vertical = 8.dp)
                    .then(horizontalPadding)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        "a" -> {
            val linkText = element.text()
            val linkUrl = element.attr("href")

            ClickableText(
                text = AnnotatedString(
                    linkText,
                    spanStyles = listOf(
                        AnnotatedString.Range(
                            SpanStyle(
                                color = Color.Blue,
                                textDecoration = TextDecoration.Underline,
                                fontSize = defaultTextSize,
                                fontFamily = textStyle
                            ),
                            0,
                            linkText.length
                        )
                    )
                ),
                onClick = { onLinkClick(linkUrl) },
                modifier = Modifier.padding(bottom = 8.dp)
                    .then(horizontalPadding)
            )
        }

        "pre" -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 8.dp)
            ) {
                Text(
                    text = element.text(),
                    fontFamily = FontFamily.Monospace,
                    fontSize = defaultTextSize,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = horizontalPadding
                )
                Spacer(modifier = Modifier.height(16.dp))
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
            )
        }

        else -> {
            // Recursive rendering for nested elements
            element.children().forEach { child ->
                RenderHtmlElement(child, onLinkClick)
            }
        }
    }
}