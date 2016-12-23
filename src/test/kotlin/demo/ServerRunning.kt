/*
 * Copyright 2013-2104 the original author or authors.
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

import java.net.HttpURLConnection
import java.util.HashMap

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.junit.Assume
import org.junit.rules.MethodRule
import org.junit.runners.model.FrameworkMethod
import org.junit.runners.model.Statement
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriTemplate

/**
 * @author Dave Syer
 */
class ServerRunning private constructor(path: String) : MethodRule {

    private var port: Int = 0

    private var hostName = DEFAULT_HOST

    private var path: String? = null

    init {
        setPort(DEFAULT_PORT)
        setPath(path)
    }

    /**
     * @param path
     */
    private fun setPath(path: String) {
        this.path = path
    }

    /**
     * @param port the port to set
     */
    fun setPort(port: Int) {
        this.port = port
        if (!serverOnline.containsKey(port)) {
            serverOnline.put(port, true)
        }
    }

    /**
     * @param hostName the hostName to set
     */
    fun setHostName(hostName: String) {
        this.hostName = hostName
    }

    override fun apply(base: Statement, method: FrameworkMethod, target: Any): Statement {

        // Check at the beginning, so this can be used as a static field
        Assume.assumeTrue(serverOnline[port] as Boolean)

        val client = RestTemplate()
        val followRedirects = HttpURLConnection.getFollowRedirects()
        HttpURLConnection.setFollowRedirects(false)
        var online = false
        try {
            client.getForEntity(
                    UriTemplate(getUrl("/")).toString(),
                    String::class.java)
            online = true
            logger.info("Basic connectivity test passed")
        } catch (e: RestClientException) {
            logger.warn(
                    String.format(
                            "Not executing tests because basic connectivity test failed for hostName=%s, port=%d",
                            hostName, port), e)
            Assume.assumeNoException(e)
        } finally {
            HttpURLConnection.setFollowRedirects(followRedirects)
            if (!online) {
                serverOnline.put(port, false)
            }
        }

        return object : Statement() {

            @Throws(Throwable::class)
            override fun evaluate() {
                base.evaluate()
            }
        }

    }

    fun getUrl(path: String): String {
        var path = path
        if (path.startsWith("http")) {
            return path
        }
        if (!path.startsWith("/")) {
            path = "/" + path
        }
        return "http://" + hostName + ":" + port + this.path + path
    }

    companion object {

        private val logger = LogFactory.getLog(ServerRunning::class.java)

        // Static so that we only test once on failure: speeds up test suite
        private val serverOnline = HashMap<Int, Boolean>()

        private val DEFAULT_PORT = 8080

        private val DEFAULT_HOST = "localhost"

        /**
         * @return a new rule that assumes an existing running broker
         */
        fun isRunning(path: String): ServerRunning {
            return ServerRunning(path)
        }

        /**
         * @return a new rule that assumes an existing running broker
         */
        val isRunning: ServerRunning
            get() = ServerRunning("")
    }

}
