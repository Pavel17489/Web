package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DebitCardTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSendFormSuccessTest() {
        driver.get("http://localhost:9999");
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("input[name='name']")).sendKeys("Николай Поликарпов");
        formElement.findElement(By.cssSelector("input[name='phone']")).sendKeys("+79514685236");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        WebElement resultSuccess = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertTrue(resultSuccess.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",resultSuccess.getText().trim());
    }

    @Test
    void shouldFailMessageInvalidNameTest() {
        driver.get("http://localhost:9999");
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("input[name='name']")).sendKeys("Nikolay Поликарпов");
        formElement.findElement(By.cssSelector("input[name='phone']")).sendKeys("+79514685236");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        WebElement resultFail = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(resultFail.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",resultFail.getText().trim());
    }

    @Test
    void shouldBeEmptyNameTest() {
        driver.get("http://localhost:9999");
        WebElement formElement = driver.findElement(By.cssSelector("form"));

        formElement.findElement(By.cssSelector("input[name='phone']")).sendKeys("+79514685236");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        WebElement resultFail = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(resultFail.isDisplayed());
        assertEquals("Поле обязательно для заполнения",resultFail.getText().trim());
    }

    @Test
    void shouldFailMessageInvalidPhoneTest() {
        driver.get("http://localhost:9999");
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("input[name='name']")).sendKeys("Николай Поликарпов");
        formElement.findElement(By.cssSelector("input[name='phone']")).sendKeys("+795146852");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        WebElement resultFail = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(resultFail.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",resultFail.getText().trim());
    }

    @Test
    void shouldBeEmptyPhoneTest() {
        driver.get("http://localhost:9999");
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("input[name='name']")).sendKeys("Николай Поликарпов");

        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        WebElement resultFail = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(resultFail.isDisplayed());
        assertEquals("Поле обязательно для заполнения",resultFail.getText().trim());
    }

    @Test
    void shouldCheckBoxInvalidTest() {
        driver.get("http://localhost:9999");
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("input[name='name']")).sendKeys("Николай Поликарпов");
        formElement.findElement(By.cssSelector("input[name='phone']")).sendKeys("+79514685236");

        driver.findElement(By.cssSelector("button.button")).click();
        WebElement resultFail = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text"));
        assertTrue(resultFail.isDisplayed());
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй",resultFail.getText().trim());
    }

}
