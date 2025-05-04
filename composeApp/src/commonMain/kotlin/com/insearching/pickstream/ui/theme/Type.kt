package com.insearching.pickstream.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import pickstream.composeapp.generated.resources.Res
import pickstream.composeapp.generated.resources.prompt_bold
import pickstream.composeapp.generated.resources.prompt_light
import pickstream.composeapp.generated.resources.prompt_medium
import pickstream.composeapp.generated.resources.prompt_regular
import pickstream.composeapp.generated.resources.prompt_semibold


@OptIn(ExperimentalResourceApi::class)
@Composable
fun PromptFamily() = FontFamily(
    Font(Res.font.prompt_light, weight = FontWeight.Light),
    Font(Res.font.prompt_regular, weight = FontWeight.Normal),
    Font(Res.font.prompt_medium, weight = FontWeight.Medium),
    Font(Res.font.prompt_semibold, weight = FontWeight.SemiBold),
    Font(Res.font.prompt_bold, weight = FontWeight.Bold)
)

@Composable
fun PromptTypography() = Typography().run {

    val fontFamily = PromptFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily =  fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}

