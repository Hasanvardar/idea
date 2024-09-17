package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class VisitSiteTest {
    public static void main(String[] args) throws InterruptedException {


        // WebDriver'ı yapılandır ve başlat
        WebDriverManager.chromedriver().setup(); // ChromeDriver'ı ayarla
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options); // ChromeDriver'ı başlat
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080)); // Pencere boyutlarını ayarla
        // Web sitesine git
        driver.get("https://testcase.myideasoft.com/");
        System.out.println("site açıldı.");



        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
        searchBox.sendKeys("ürün");
        searchBox.sendKeys(Keys.RETURN);
        System.out.println("ürün yazıp aratıldı");
        Thread.sleep(2000);
        WebElement ilkUrun = driver.findElement(By.cssSelector("img[alt='Ürün']"));
        ilkUrun.click();
        System.out.println("ilk ürüne tıklandı");
        Thread.sleep(2000);
        WebElement adetSayisi = driver.findElement(By.cssSelector("#qty-input"));
        adetSayisi.click();
        adetSayisi.sendKeys("5");
        adetSayisi.sendKeys(Keys.RETURN);
        System.out.println("adet sayısı ayarlandı");
        Thread.sleep(2000);


        WebElement sepeteEkle = driver.findElement(By.cssSelector("a[class='add-to-cart-button'] span"));
        sepeteEkle.click();
        System.out.println("5 adet ürün sepete eklendi");
        Thread.sleep(2000);
        //WebElement basariliMesaji = driver.findElement(By.cssSelector(".alert.alert-success"));
        //System.out.println("Sepete eklenme mesajı: " + basariliMesaji.getText());

        WebElement sepeteGit = driver.findElement(By.cssSelector("a[title='Sepet'] span"));
        sepeteGit.click();
        System.out.println("sepet ekranı açıldı");
        Thread.sleep(2000);
        By elementLocator = By.cssSelector("img[alt='Ürün']");
        List<WebElement> elements = driver.findElements(elementLocator);

        if (elements.size() > 0) {
            System.out.println("ürün bulundu.");
        } else {
            System.out.println("ürün bulunamadı.");
        }
        Thread.sleep(2000);

        By elementLocator2 = By.xpath("//input[@value='5']");
        List<WebElement> elements2 = driver.findElements(elementLocator2);

        if (elements.size() > 0) {
            System.out.println("5tane var.");
        } else {
            System.out.println("5 tane yok");
        }
        System.out.println("ürün sayısı  kontrol edildi ");

        // Tarayıcıyı kapat

        driver.quit();
    }
}