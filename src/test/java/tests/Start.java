package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Start {

    private WebDriver driver = new ChromeDriver();

    private String calc(double x) {
        return Double.toString(Math.log(Math.abs(12 * Math.sin(x))));
    }

    private void getAlertTextToConsole() {
        System.out.println(driver.switchTo().alert().getText());
    }

    private void CalcXValue(WebElement input_value, WebElement answer, WebElement submit_button) {
        double x_value = Double.valueOf(input_value.getText());
        answer.sendKeys(calc(x_value));
        submit_button.click();
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("return arguments[0].scrollIntoView(true);",
                element);
    }

    @After
    public void close() {
        driver.quit();
    }

    @Test
    public void testFindElement() {
        String link = "http://suninjuly.github.io/find_link_text";
        driver.navigate().to(link);
        driver.findElement(By.linkText(Long.toString(Math.round(Math.pow(Math.PI, Math.E) * 10000)))).click();
        driver.findElement(By.tagName("input")).sendKeys("Ksenia");
        driver.findElement(By.name("last_name")).sendKeys("Kerentseva");
        driver.findElement(By.className("city")).sendKeys("Ulyanovsk");
        driver.findElement(By.id("country")).sendKeys("Russia");
        driver.findElement(By.cssSelector("button.btn")).click();
    }

    @Test
    public void testFindElements() {
        String link = "http://suninjuly.github.io/huge_form.html";
        driver.navigate().to(link);
        List<WebElement> elements = driver.findElements(By.tagName("input"));
        for (WebElement element : elements) {
            element.sendKeys("Мой ответ");
        }
        driver.findElement(By.cssSelector("button.btn")).click();
    }

    @Test
    public void testFindXPath() {
        String link = "http://suninjuly.github.io/find_xpath_form";
        driver.navigate().to(link);
        driver.findElement(By.tagName("input")).sendKeys("Ksenia");
        driver.findElement(By.name("last_name")).sendKeys("Kerentseva");
        driver.findElement(By.className("city")).sendKeys("Ulyanovsk");
        driver.findElement(By.id("country")).sendKeys("Russia");
        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
    }

    @Test
    public void testRegister() throws InterruptedException {
        String link = "http://suninjuly.github.io/registration2.html";
        String expected_text = "Поздравляем! Вы успешно зарегистировались!";
        driver.navigate().to(link);
        driver.findElement(By.cssSelector(".first_block .first")).sendKeys("Test");
        driver.findElement(By.cssSelector(".first_block .second")).sendKeys("Test");
        driver.findElement(By.cssSelector(".first_block .third")).sendKeys("Test");
        driver.findElement(By.cssSelector("button.btn")).click();
        Thread.sleep(1000);

        String actual_text = driver.findElement(By.tagName("h1")).getText();
        Assert.assertEquals(String.format("Заголовок страницы не равен ожидаемому %s", expected_text),
                expected_text, actual_text);
    }

    @Test
    public void testCheckBoxes() {
        String link = "http://suninjuly.github.io/math.html";
        driver.navigate().to(link);
        double x = Double.valueOf(driver.findElement(By.id("input_value")).getText());
        driver.findElement(By.id("answer")).sendKeys(calc(x));
        driver.findElement(By.cssSelector("[for=\"robotCheckbox\"]")).click();
        driver.findElement(By.id("robotsRule")).click();
        driver.findElement(By.cssSelector("button.btn")).click();
    }

    @Test
    public void testGetAttribute() {
        String link = "http://suninjuly.github.io/get_attribute.html";
        driver.navigate().to(link);
        double x = Double.valueOf(driver.findElement(By.id("treasure")).getAttribute("valuex"));
        driver.findElement(By.id("answer")).sendKeys(calc(x));
        driver.findElement(By.id("robotCheckbox")).click();
        driver.findElement(By.id("robotsRule")).click();
        driver.findElement(By.cssSelector("button.btn")).click();
    }

    @Test
    public void testSelect() {
        String link = "http://suninjuly.github.io/selects1.html";
        driver.navigate().to(link);
        int result = Integer.parseInt(driver.findElement(By.id("num1")).getText()) +
                Integer.parseInt(driver.findElement(By.id("num2")).getText());

        WebElement selectElem = driver.findElement(By.id("dropdown"));
        Select select = new Select(selectElem);
        select.selectByValue(Integer.toString(result));

        driver.findElement(By.cssSelector("button.btn")).click();
    }

    @Test
    public void testScript() throws InterruptedException {
        String link = "http://suninjuly.github.io/selects1.html";
        driver.navigate().to(link);
        ((JavascriptExecutor) driver).executeScript("document.title='Script executing';alert('Robots at work');");
        Thread.sleep(7000);
    }

    @Test
    public void testScriptExecuting() throws InterruptedException {
        String link = "https://SunInJuly.github.io/execute_script.html";
        driver.navigate().to(link);
        WebElement button = driver.findElement(By.tagName("button"));
        scrollToElement(button);
        button.click();
        Thread.sleep(7000);
    }

    @Test
    public void testScriptScroll() {
        String link = "http://SunInJuly.github.io/execute_script.html";
        driver.navigate().to(link);
        double x_value = Double.valueOf(driver.findElement(By.id("input_value")).getText());
        driver.findElement(By.id("answer")).sendKeys(calc(x_value));
        driver.findElement(By.id("robotCheckbox")).click();
        scrollToElement(driver.findElement(By.id("peopleRule")));
        driver.findElement(By.id("robotsRule")).click();
        driver.findElement(By.cssSelector("button.btn")).click();
        getAlertTextToConsole();
    }

    @Test
    public void testFile() {
        String file_name = "file.txt";
        File file = new File(file_name);
        String file_path = file.getAbsolutePath();
        System.out.println(file_path);

        String link = "http://suninjuly.github.io/file_input.html";
        driver.navigate().to(link);

        driver.findElement(By.name("firstname")).sendKeys("firstname");
        driver.findElement(By.name("lastname")).sendKeys("lastname");
        driver.findElement(By.name("email")).sendKeys("email@email.ru");
        driver.findElement(By.id("file")).sendKeys(file_path);
        driver.findElement(By.cssSelector("button.btn")).click();
        getAlertTextToConsole();
    }

    @Test
    public void testAlert() {
        String link = "http://suninjuly.github.io/alert_accept.html";
        driver.navigate().to(link);
        driver.findElement(By.cssSelector("button.btn")).click();
        driver.switchTo().alert().accept();
        CalcXValue(driver.findElement(By.id("input_value")),
                driver.findElement(By.id("answer")),
                driver.findElement(By.cssSelector("button.btn")));
        getAlertTextToConsole();
    }

    @Test
    public void testWindowHandles() {
        String link = "http://suninjuly.github.io/redirect_accept.html";
        driver.navigate().to(link);
        driver.findElement(By.cssSelector(".trollface.btn")).click();
        ArrayList<String> handles = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(handles.get(1));
        CalcXValue(driver.findElement(By.id("input_value")),
                driver.findElement(By.id("answer")),
                driver.findElement(By.cssSelector("button.btn")));
        getAlertTextToConsole();
    }

    @Test
    public void testWait() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String link = "http://suninjuly.github.io/wait1.html";
        driver.navigate().to(link);
        driver.findElement(By.id("verify")).click();
        Assert.assertEquals("Текст не совпадает", "Verification was successful!",
                driver.findElement(By.id("verify_message")).getText());
    }

    @Test
    public void testExplicit() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String link = "http://suninjuly.github.io/explicit_wait2.html";
        driver.navigate().to(link);
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("price"), "$100"));
        driver.findElement(By.id("book")).click();
        WebElement input_value = driver.findElement(By.id("input_value"));
        scrollToElement(input_value);
        CalcXValue(input_value, driver.findElement(By.id("answer")),
                driver.findElement(By.id("solve")));
        getAlertTextToConsole();
    }
}
