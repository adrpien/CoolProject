package com.example.coolproject.feature_market.presentation.company_details.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coolproject.feature_market.domain.model.IntradayInfo
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun CompanyChart(
    intradayInfoList: List<IntradayInfo> = emptyList(),
    modifier: Modifier = Modifier,
    graphColor: Color = Color.Green,

) {

    val spacing = 100f
    val transparentGraphColor = remember {
         graphColor.copy(alpha = 0.5f, )
    }

    // value in brackets is recalculated when the value changes
    val upperValue = remember(intradayInfoList) {
        (intradayInfoList.maxOfOrNull { it.close }?.plus(1))?.roundToInt() ?: 0
    }

    val lowerValue = remember(intradayInfoList) {
        (intradayInfoList.minOfOrNull { it.close })?.roundToInt() ?: 0
    }

    val density = LocalDensity.current

    val textPaint = remember {
        Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Canvas(
        modifier = modifier)  {

        // X Axis...
        val horizontalSpace = (size.width - spacing) / intradayInfoList.size
        (0 until intradayInfoList.size - 1  step 2). forEach{ i ->
            val hour = intradayInfoList[i].time.hour

            // This is how to draw taxt in compose canvas - u need to use native android canvas
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                     hour.toString(),
                    spacing + i * horizontalSpace,
                    size.height - 4,
                    textPaint
                )
            }

        }

        // Y Axis...
        val verticalStep = (upperValue - lowerValue) / 5f
        (0..4).forEach{ i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    round(lowerValue + verticalStep * i ).toString(),
                    50f,
                    size.height - spacing - i * size.height / 5f,
                    textPaint
                )
            }
        }


        // Graph...
        var lastX = 0f
        val strokePath = Path().apply {
            val height = size.height
            for (i in intradayInfoList.indices) {
                val intradayInfo = intradayInfoList[i]
                val nextIntradayInfo = intradayInfoList.getOrNull(i + 1 ) ?: intradayInfoList.last()

                // Mapping close values into Y coordinates in percentage of graph height
                val intradayInfoYCoordinatePercentage = (intradayInfo.close - lowerValue) / (upperValue - lowerValue)
                val nextIntradayInfoYCoordinatePercentage = (nextIntradayInfo.close - lowerValue) / (upperValue - lowerValue)

                // Point coordinates...
                val intradayInfoXCoordinate = spacing + i * horizontalSpace
                val intradayInfoYCoordinate = height - spacing - (intradayInfoYCoordinatePercentage * height).toFloat()

                // Next point coordinate...
                val nextIntradayInfoXCoordinate = spacing + (i + 1) * horizontalSpace
                val nextIntradayInfoYCoordinate = height - spacing - (nextIntradayInfoYCoordinatePercentage * height).toFloat()
                if (i == 0 ) {
                    moveTo(
                        intradayInfoXCoordinate,
                        intradayInfoYCoordinate
                    )
                }
                lastX = (intradayInfoXCoordinate + nextIntradayInfoXCoordinate) / 2f
                quadraticBezierTo(
                    intradayInfoXCoordinate,
                    intradayInfoYCoordinate,
                    (intradayInfoXCoordinate + nextIntradayInfoXCoordinate) / 2f,
                    (intradayInfoYCoordinate + nextIntradayInfoYCoordinate) / 2f
                )

            }
            }

        // Adding gradient...
        // Need to cenvert this into native android path and then to compose path in order to copy path
        val fillPath = android.graphics.Path(strokePath.asAndroidPath()).asComposePath().apply {
            lineTo(
                lastX,
                size.height - spacing
            )
            lineTo(
                spacing,
                size.height - spacing
            )
            close()
            }
        drawPath(
            fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    transparentGraphColor,
                    Color.Transparent
                ),
                endY = size.height - spacing
            )
        )
        drawPath(
            strokePath,
            color = graphColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        )

    }
}

