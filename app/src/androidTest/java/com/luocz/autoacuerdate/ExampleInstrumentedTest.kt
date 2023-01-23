/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 22/01/23 12:10
 *   File: ExampleInstrumentedTest.kt
 *   Last modified: 18/01/23 10:37
 *   Last change:
 *     Change Name ->
 *     Change Date -> 22/01/23 12:10
 *     Description ->
 *
 * **************************************************************************
 */

/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 18/01/23 10:36
 *   File: ExampleInstrumentedTest.kt
 *   Last modified: 18/01/23 10:24
 *   Last change:
 *     Change Name ->
 *     Change Date -> 18/01/23 10:36
 *     Description ->
 *
 * **************************************************************************
 */

/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 18/01/23 10:24
 *   File: ExampleInstrumentedTest.kt
 *   Last modified: 18/01/23 10:22
 *   Last change:
 *     Change Name ->
 *      18/01/23 10:24 ->
 *     Description ->
 *
 * **************************************************************************
 */

/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 18/01/23 10:22
 *   File: ExampleInstrumentedTest.kt
 *   Last modified: 18/01/23 10:22
 *   Last change:
 *      18/01/23 10:22:
 *
 *
 * **************************************************************************
 */

/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 18/01/23 10:21
 *   File: ExampleInstrumentedTest.kt
 *   Last modified: 18/01/23 10:21
 *   Last change:
 *  18/01/23 10:21:
 *
 *
 * **************************************************************************
 */

/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 18/01/23 10:20.
 *   File: ExampleInstrumentedTest.kt
 *   Last modified: 18/01/23 10:20
 *   Last change:
 *
 *
 *
 * **************************************************************************
 */

/*
 * **************************************************************************
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 18/01/23 10:20.
 *   File: ExampleInstrumentedTest.kt
 *   Last modified: 18/01/23 10:17
 *   Last change:
 *
 *
 *
 * **************************************************************************
 */

/*
 *
 *   Author: Luis Octavio Goméz de la Cruz
 *   Date created: 18/01/23 10:06
 *   File: $file.name
 *   Last modified: 18/01/23 9:59
 *   Last change:
 *
 *
 *
 * /
 */

package com.luocz.autoacuerdate

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.luocz.autoacuerdate", appContext.packageName)
    }
}