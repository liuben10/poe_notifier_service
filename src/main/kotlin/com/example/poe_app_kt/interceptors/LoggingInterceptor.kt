package com.example.poe_app_kt.interceptors

import org.slf4j.LoggerFactory
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import java.io.IOException


class LoggingInterceptor : ClientHttpRequestInterceptor {
    val log = LoggerFactory.getLogger(LoggingInterceptor::class.simpleName)

    override fun intercept(request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution): ClientHttpResponse {
        traceRequest(request, body)
        val response = execution.execute(request, body)
        traceResponse(response)
        return response
    }

    @Throws(IOException::class)
    private fun traceRequest(request: HttpRequest, body: ByteArray) {
        log.info("===========================request begin================================================")
        log.info("URI         : {}", request.uri)
        log.info("Method      : {}", request.method)
        log.info("Headers     : {}", request.headers)
        log.info("==========================request end================================================")
    }

    @Throws(IOException::class)
    private fun traceResponse(response: ClientHttpResponse) {
        //		StringBuilder inputStringBuilder = new StringBuilder();
        //		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
        //		String line = bufferedReader.readLine();
        //		while (line != null) {
        //			inputStringBuilder.append(line);
        //			inputStringBuilder.append('\n');
        //			line = bufferedReader.readLine();
        //		}
        log.info("============================response begin==========================================")
        log.info("Status code  : {}", response.statusCode)
        log.info("Status text  : {}", response.statusText)
        log.info("Headers      : {}", response.headers)
        //		log.debug("Response body: {}", inputStringBuilder.toString());
        log.info("=======================response end=================================================")
    }
}