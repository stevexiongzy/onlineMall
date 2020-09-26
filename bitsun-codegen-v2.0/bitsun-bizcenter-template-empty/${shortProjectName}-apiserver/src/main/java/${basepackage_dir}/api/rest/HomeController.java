package ${basepackage}.api.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 首页显示swagger接口
 * @author: ${author!"Felix Woo"}
 * @email: ${email!"foruforo@msn.com"}
 */
@Controller
@ApiIgnore
class HomeController {
    @RequestMapping(value = {"/", "/swagger"})
    public String index() {
        return "redirect:swagger-ui.html";
    }
}