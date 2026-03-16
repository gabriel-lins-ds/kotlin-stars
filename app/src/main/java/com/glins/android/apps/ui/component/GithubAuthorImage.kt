package com.glins.android.apps.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.glins.android.apps.ui.constants.UiConstants.AVATAR_SIZE
import com.glins.android.apps.ui.util.IdenticonUtils.getIdenticonRandomId

@Composable
fun GithubAuthorImage(
    modifier: Modifier = Modifier,
    url: String? = null,
    size: Int = AVATAR_SIZE
) {
    val placeholder = getIdenticonRandomId()
    AsyncImage(
        model = url,
        contentDescription = "Owner avatar",
        placeholder = painterResource(placeholder),
        error = painterResource(placeholder),
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
    )
}

@Preview
@Composable
private fun GithubAuthorImagePreview() {
    GithubAuthorImage()
}