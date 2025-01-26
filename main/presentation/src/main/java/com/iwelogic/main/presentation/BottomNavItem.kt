package com.iwelogic.main.presentation

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavItem(
    screen: Screen,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        contentAlignment = Alignment.Center,
    ) {
        val animSpec: AnimationSpec<Float> = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioMediumBouncy
        )
        val animatedAlpha by animateFloatAsState(
            targetValue = if (isSelected) 1f else .6f,
            animationSpec = animSpec,
            label = "animatedAlpha"
        )
        val animatedIconSize by animateDpAsState(
            targetValue = if (isSelected) 32.dp else 24.dp,
            animationSpec = spring(
                stiffness = Spring.StiffnessLow,
                dampingRatio = Spring.DampingRatioMediumBouncy
            ),
            label = "animatedIconSize"
        )
        val animationRotation by animateFloatAsState(
            targetValue = if (isSelected) 180f else 0f,
            animationSpec = animSpec,
            label = "animationRotation"
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .alpha(animatedAlpha)
                .size(animatedIconSize)
                .graphicsLayer { rotationY = animationRotation },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                rememberVectorPainter(
                    image = ImageVector.vectorResource(id = if (animationRotation > 90f) screen.activeIcon else screen.inactiveIcon)
                ),
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .fillMaxHeight()
                    .alpha(animatedAlpha)
                    .size(animatedIconSize),
                contentDescription = "Bottom Navigation Icon"
            )
        }
    }
}