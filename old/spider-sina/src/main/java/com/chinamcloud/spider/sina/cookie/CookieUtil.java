package com.chinamcloud.spider.sina.cookie;

import com.alibaba.fastjson.JSON;
import com.chinamcloud.spider.orm.redis.RedisUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

public class CookieUtil {

    private static Vector<Map<String, String>> users;

    {
        users = new Vector<>();
    }


    public synchronized static Map<String, String> getCookie() {
        String cookie = RedisUtil.lpop("SinaCookie");
        RedisUtil.pushList("SinaCookie", cookie);
        List<Cookie> cookies = JSON.parseArray(cookie, Cookie.class);
        Map<String, String> cookiesMap = new HashMap<>();
        cookies.stream().forEach(cookie1 -> {
            cookiesMap.put(cookie1.getName(), cookie1.getValue());
        });
        return cookiesMap;
    }

    public static void getCookie(String username, String password) {
        WebDriver webDriver = new ChromeDriver();
        try {
            webDriver.get("https://weibo.cn");
            WebElement element = webDriver.findElement(By.linkText("登录"));
            (new Actions(webDriver)).moveToElement(element).click().perform();
            Thread.sleep(1000 * 2);
            WebElement loginName = new WebDriverWait(webDriver, 10)
                    .until((webDriver1) -> webDriver1.findElement(By.id("loginName")));
            WebElement loginPassword = webDriver.findElement(By.id("loginPassword"));
            loginName.sendKeys(username);
            loginPassword.sendKeys(password);

            WebElement loginButton = webDriver.findElement(By.id("loginAction"));
            loginButton.click();
            Thread.sleep(1000 * 2);
            Set<Cookie> cookies = webDriver.manage().getCookies();

            cookies.stream().forEach(cookie -> System.out.println(cookie.toString()));
            RedisUtil.pushList("SinaCookie", JSON.toJSONString(cookies));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            webDriver.quit();
        }

    }

    public static void main(String[] args) {
        CookieUtil.getCookie("18048889970", "201011.4wydtx");
    }


}
