package com.training.algorithm.stack;

/**
 * @ClassName SampleBrowser
 * @Description 使用链式栈实现浏览器的前进后退
 * @Author caroline
 * @Version 1.0
 * @Date 2023/12/30 16:16
 */
public class SampleBrowser {

    public static void main(String[] args) {
        SampleBrowser browser = new SampleBrowser();
        browser.open("https://www.baidu.com/");
        browser.open("https://www.baidu.com/1");
        browser.open("https://www.baidu.com/2");
        browser.open("https://www.baidu.com/3");
        browser.goForward();
        browser.goBack();
        browser.goBack();
        browser.goBack();
        browser.goForward();
        browser.checkCurrentPage();
    }

    private String currentPage;

    private LinkedListStack backStack;

    private LinkedListStack forwardStack;

    public SampleBrowser() {
        this.backStack = new LinkedListStack();
        this.forwardStack = new LinkedListStack();
    }

    /**
     * 打开一个新的页面，前进栈清空
     */
    public void open (String url) {
        if (this.currentPage != null) {
            this.backStack.push(this.currentPage);
            this.forwardStack.clear();
        }
        showUrl(url, " Open");
    }

    public boolean canGoBack () {
        return this.backStack.size() > 0;
    }

    public boolean canGoForward () {
        return this.forwardStack.size() > 0;
    }

    public String goBack () {
        if (canGoBack()) {
            this.forwardStack.push(this.currentPage);
            String url = this.backStack.pop();
            showUrl(url, "Back");
            return url;
        }
        System.out.println("* Cannot go back, no pages behind.");
        return null;
    }

    public String goForward () {
        if (canGoForward()) {
            this.backStack.push(this.currentPage);
            String url = this.forwardStack.pop();
            showUrl(url, "Forward");
            return url;
        }
        System.out.println("* Cannot go forward, no pages ahead.");
        return null;
    }

    public void showUrl (String url, String prefix) {
        this.currentPage = url;
        System.out.println(prefix + " page: " + url);
    }

    public void checkCurrentPage() {
        System.out.println("Current page is: " + this.currentPage);
    }

}
