package demo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Configuration
@EnableAutoConfiguration
open class Application {


    @Bean open fun homeController(): HomeController = HomeController()

}

@RestController
class HomeController {
    @RequestMapping("/")
    fun home(): String {
        return "Hello World"
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}

