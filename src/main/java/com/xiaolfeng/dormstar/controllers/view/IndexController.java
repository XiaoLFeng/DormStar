package com.xiaolfeng.dormstar.controllers.view;

import com.xiaolfeng.dormstar.cache.RamDataCache;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 筱锋xiao_lfeng
 */
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {
    private final RamDataCache ramDataCache;

    @GetMapping("/")
    public String getIndex(@NotNull Model model) {
        model.addAttribute("title", ramDataCache.name);
        return "index";
    }

    @GetMapping("/center")
    public String getCenter(@NotNull Model model) {
        model.addAttribute("title", ramDataCache.name);
        model.addAttribute("version", ramDataCache.version);
        return "center";
    }

    @GetMapping("/help")
    public String getHelp() {
        return "help";
    }
}
