package demo

import demo.util.dialect.GcsHrefProcessor
import demo.util.dialect.GcsResourceDialect
import demo.util.dialect.GcsSrcProcessor
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Configuration
@EnableAutoConfiguration
open class Application {

    @Bean open fun gcsHrefProcessor(): GcsHrefProcessor = GcsHrefProcessor()
    @Bean open fun gcsSrcProcessor(): GcsSrcProcessor = GcsSrcProcessor()
    @Bean open fun gcsResourceDialect(): GcsResourceDialect = GcsResourceDialect()


    @Bean open fun homeController(): HomeController = HomeController()

}

@Controller
@RequestMapping("/")
class HomeController {
    @RequestMapping
    fun home(): String {

        return "index"
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}

