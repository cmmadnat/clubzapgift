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
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.TestRestTemplate
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration

@RunWith(SpringJUnit4ClassRunner::class)
@SpringApplicationConfiguration(classes = arrayOf(Application::class))
@WebAppConfiguration
@IntegrationTest("server.port=0")
class EmbeddedIntegrationTests {

    @Value("\${local.server.port}")
    private val port: Int = 0

    @Test
    @Throws(IOException::class)
    fun testVersion() {
        val body = TestRestTemplate().getForObject("http://127.0.0.1:" + port
                + "/info", String::class.java)
        log.info("found info = " + body)
        assertTrue("Wrong body: " + body, body.contains("{\"version"))
    }

    companion object {

        private val log = Logger.getLogger(EmbeddedIntegrationTests::class.java)
    }

}
