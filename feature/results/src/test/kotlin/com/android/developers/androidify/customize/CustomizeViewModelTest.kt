/*
 * Copyright 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:OptIn(ExperimentalCoroutinesApi::class)

package com.android.developers.androidify.customize

import android.graphics.Bitmap
import android.net.Uri
import androidx.test.core.app.ApplicationProvider
import com.android.developers.testing.repository.FakeImageGenerationRepository
import com.android.developers.testing.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CustomizeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CustomizeExportViewModel

    private val fakeBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    private val originalFakeUri = Uri.parse("content://com.example.app/images/original.jpg")

    @Before
    fun setup() {
        viewModel = CustomizeExportViewModel(
            FakeImageGenerationRepository(),
            application = ApplicationProvider.getApplicationContext(),
        )
    }

    @Test
    fun stateInitialEmpty() = runTest {
        assertEquals(
            CustomizeExportState(),
            viewModel.state.value,
        )
    }

    @Test
    fun setArgumentsWithOriginalImage() = runTest {
        viewModel.setArguments(
            fakeBitmap,
            originalFakeUri,
        )
        assertEquals(
            CustomizeExportState(
                exportImageCanvas = ExportImageCanvas(imageBitmap = fakeBitmap),
                originalImageUrl = originalFakeUri,
            ),
            viewModel.state.value,
        )
    }

    @Test
    fun setArgumentsWithPrompt() = runTest {
        viewModel.setArguments(
            fakeBitmap,
            null,
        )
        assertEquals(
            CustomizeExportState(
                exportImageCanvas = ExportImageCanvas(imageBitmap = fakeBitmap),
                originalImageUrl = null,
            ),
            viewModel.state.value,
        )
    }

  /*  @Test
    fun downloadClicked() = runTest {
        val values = mutableListOf<CustomizeExportState>()
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect {
                values.add(it)
            }
        }

        viewModel.setArguments(
            fakeBitmap,
            originalFakeUri,
        )

        viewModel.downloadClicked()
        assertNotNull(values.last().externalOriginalSavedUri)
        assertEquals(
            originalFakeUri,
            values.last().externalOriginalSavedUri,
        )
    }

    @Test
    fun shareClicked() = runTest {
        val values = mutableListOf<CustomizeExportState>()
        // Launch collector on the backgroundScope directly to use runTest's scheduler
        backgroundScope.launch {
            viewModel.state.collect {
                values.add(it)
            }
        }
        viewModel.setArguments(
            fakeBitmap,
            originalFakeUri,
        )
        advanceUntilIdle()
        viewModel.shareClicked()
        // Ensure all coroutines on the test scheduler complete
        advanceUntilIdle()
        assertNotNull(values.last().savedUri)
    }*/
}
