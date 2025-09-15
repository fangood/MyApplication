package com.android.module_compose2.basic


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.android.module_compose2.R

@Composable
fun ImageUsageExample() {
    Column {
        // 1. ImageBitmap示例（从资源加载）
        val bitmap: Bitmap = BitmapFactory.decodeResource(
            LocalContext.current.resources,
            R.drawable.xigua
        )
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Bitmap Image",
            modifier = Modifier.weight(1f)
        )

        // 2. ImageVector示例（矢量图）
        val vectorImage: ImageVector = ImageVector.vectorResource(
            id = R.drawable.cat
        )
        Image(
            painter = rememberVectorPainter(image = vectorImage),
            contentDescription = "Vector Image",
            modifier = Modifier.weight(1f)
        )
        AsyncImage(
            model = "https://avatars.githubusercontent.com/u/33804543?s=200&v=4",
            contentDescription = "Async Image",
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview
@Composable
fun PreviewImageUsageExample() {
    ImageUsageExample()
}
