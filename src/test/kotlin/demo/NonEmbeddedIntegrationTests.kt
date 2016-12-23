/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package demo

import org.junit.Assert.assertTrue

import java.io.IOException

import org.apache.log4j.Logger
import org.junit.Rule
import org.junit.Test
import org.springframework.boot.test.TestRestTemplate

class NonEmbeddedIntegrationTests {

    @Rule
    var serverRunning = ServerRunning.isRunning

    private val port = 8080

    @Test
    @Throws(IOException::class)
    fun testVersion() {
        val body = TestRestTemplate().getForObject("http://localhost:" + port
                + "/info", String::class.java)
        log.info("found info = " + body)
        assertTrue("Wrong body: " + body, body.contains("{\"version"))
    }

    @Test
    @Throws(IOException::class)
    fun testMetrics() {
        val body = TestRestTemplate().getForObject("http://localhost:" + port
                + "/metrics", String::class.java)
        log.info("found metrics = " + body)
        assertTrue("Wrong body: " + body, body.contains("\"classes.loaded"))
    }

    companion object {

        private val log = Logger.getLogger(NonEmbeddedIntegrationTests::class.java)
    }

}
