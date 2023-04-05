package com.base.common.utils;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ThreadUtils;

import java.time.Duration;

/**
 * @Author ..
 * @Date ..
 */
@Slf4j
public class PlaywrightUtil {

    private static String url = "https://www.vfsglobal.cn";

    public static void test () {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(showBrowser());
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate(url);
            page = step1(page);
//            page.pause();
            page = step2(page);
            sleep(30000000);

//            List<Page> pages = context.pages();
//            for (int i = 0; i < pages.size(); i++) {
//                Page page1 = pages.get(0);
//                System.out.println(page1.title());
//            }
//            page = pages.get(1);
//            String html = page.innerHTML("html");
//            System.out.println(html);
//            page.close();
        }
    }


    public static Page step1 (Page page) {
        page.waitForLoadState();
        page.locator(".selected").click();
        page.getByRole(AriaRole.LISTITEM).filter(new Locator.FilterOptions().setHasText("德国")).nth(1).click();
        return page.waitForPopup(() -> page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("立即前往→")).click());
    }

    public static Page step2 (Page page) {
        page.waitForLoadState();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("接受所有 Cookie")).click();
        sleep();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("现在预约")).click();
        return page.waitForPopup(() -> page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("立即预约")).click());
    }



    public static BrowserType.LaunchOptions showBrowser () {
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(false);
        return launchOptions;
    }

    public static Browser.NewPageOptions windowBrowser () {
        Browser.NewPageOptions options = new Browser.NewPageOptions();
        options.setViewportSize(1280, 720);
        return options;
    }

    public static void sleep () {
        sleep(3);
    }

    public static void sleep (long num) {
        try {
            ThreadUtils.sleep(Duration.ofSeconds(num));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test();
    }
}
