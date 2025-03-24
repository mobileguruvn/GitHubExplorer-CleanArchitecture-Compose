package com.githubbrowser.designsystem

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration


@Composable
fun UnderlineTextClickable(linkText: String) {
    val context = LocalContext.current
    val annotatedText = buildAnnotatedString {
        pushStyle(SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline))
        append(linkText)
        addStringAnnotation(
            tag = "URL",
            annotation = linkText, // Actual URL
            start = 0,
            end = linkText.length
        )
        pop()
    }


    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    // Open the link in a browser
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                    context.startActivity(intent)
                }
        }
    )
}