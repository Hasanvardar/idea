import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestAutomation {

    public static void main(String[] args) {
        // WebDriverManager kullanarak ChromeDriver'ı yapılandırma
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Tarayıcıyı görünmez modda çalıştırmak isterseniz
        WebDriver driver = new ChromeDriver(options);

        try {
            // WebDriverWait kullanarak zaman aşımı süresini ayarlama
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Siteyi ziyaret et
            driver.get("https://testcase.myideasoft.com/");

            // Arama kutusunu bul ve "ürün" kelimesini yaz
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
            searchBox.sendKeys("ürün");
            searchBox.submit(); // Aramayı başlat

            // Sonuç listesinden ürünü seç
            WebElement productLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-item a")));
            productLink.click();

            // Ürün detay sayfasına gidin ve 5 adet ürünü sepete ekleyin
            WebElement quantityBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("quantity")));
            quantityBox.clear();
            quantityBox.sendKeys("5");

            WebElement addToCartButton = driver.findElement(By.cssSelector(".add-to-cart-button"));
            addToCartButton.click();

            // Sepete eklendiğini doğrula
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success-message")));
            if (!successMessage.getText().contains("SEPETİNİZE EKLENMİŞTİR")) {
                throw new AssertionError("Başarı mesajı görüntülenmedi.");
            }

            // Sepet sayfasına git
            WebElement cartButton = driver.findElement(By.cssSelector(".cart-button"));
            cartButton.click();

            // Sepet içeriğinde ürünü kontrol et
            WebElement cartItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cart-item")));
            WebElement productName = cartItem.findElement(By.cssSelector(".product-name"));
            WebElement quantity = cartItem.findElement(By.cssSelector(".quantity"));
            if (productName.getText().contains("ürün")) {
                if (!quantity.getText().equals("5")) {
                    throw new AssertionError("Sepetteki ürün miktarı beklenenden farklı.");
                }
            } else {
                throw new AssertionError("Ürün sepette bulunamadı.");
            }

            System.out.println("Test başarılı!");

        } finally {
            driver.quit();
        }
    }
}
