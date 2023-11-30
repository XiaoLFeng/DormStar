package com.xlf.dromstarkotlin.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class ViewController {
    @RequestMapping("/{path:[^.]*}")
    fun forward(@PathVariable path: String): String? {
        return "forward:/index.html"
    }
}