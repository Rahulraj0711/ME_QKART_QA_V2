package QKART_SANITY_LOGIN.Module1;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class Home {
    RemoteWebDriver driver;
    WebDriverWait wait;
    String url = "https://crio-qkart-frontend-qa.vercel.app";

    public Home(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHome() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public Boolean PerformLogout() throws InterruptedException {
        try {
            // Find and click on the Logout Button
            WebElement logout_button = driver.findElement(By.className("MuiButton-text"));
            logout_button.click();

            // Wait for Logout to Complete
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.css-vb6e92 button.css-1urhf6j")));

            return true;
        } catch (Exception e) {
            // Error while logout
            return false;
        }
    }

    /*
     * Returns Boolean if searching for the given product name occurs without any
     * errors
     */
    public Boolean searchForProduct(String product) {
        try {
            // CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Clear the contents of the search box and Enter the product name in the search box
            WebElement searchBox=driver.findElement(By.name("search"));
            searchBox.clear();
            searchBox.sendKeys(product);
            return true;
        } catch (Exception e) {
            System.out.println("Error while searching for a product: " + e.getMessage());
            return false;
        }
    }

    /*
     * Returns Array of Web Elements that are search results and return the same
     */
    public List<WebElement> getSearchResults() {
        List<WebElement> searchResults = new ArrayList<WebElement>() {
        };
        try {
            // CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Find all webelements corresponding to the card content section of each of search results
            searchResults=driver.findElements(By.cssSelector("div.css-1qw96cp"));
            return searchResults;
        } catch (Exception e) {
            System.out.println("There were no search results: " + e.getMessage());
            return searchResults;

        }
    }

    /*
     * Returns Boolean based on if the "No products found" text is displayed
     */
    public Boolean isNoResultFound() {
        Boolean status = false;
        try {
            // CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Check the presence of "No products found" text in the web page. Assign status
            // = true if the element is *displayed* else set status = false
            String notFoundText=driver.findElement(By.cssSelector("div.loading h4")).getText();
            if(notFoundText.trim().toUpperCase().equalsIgnoreCase("No products found")) {
                status=true;
            }
            return status;
        } catch (Exception e) {
            return status;
        }
    }

    /*
     * Return Boolean if add product to cart is successful
     */
    public Boolean addProductToCart(String productName) {
        try {
            // CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            /*
             * Iterate through each product on the page to find the WebElement corresponding
             * to the matching productName
             * 
             * Click on the "ADD TO CART" button for that element
             * 
             * Return true if these operations succeeds
             */
            List<WebElement> items=driver.findElements(By.cssSelector("div.css-sycj1h div div p.css-yg30e6"));
            for(WebElement item:items) {
                if (item.getText().equalsIgnoreCase(productName)) {
                    driver.findElement(with(By.cssSelector("div.css-3zukih button")).below(item)).click();
                    FluentWait<RemoteWebDriver> fWait = new FluentWait<>(driver).pollingEvery(Duration.ofMillis(250)).withTimeout(Duration.ofSeconds(30)).ignoring(Exception.class);
                    boolean status = fWait.until(new Function<WebDriver, Boolean>() {
                        boolean flag = false;

                        @Override
                        public Boolean apply(WebDriver t) {
                            List<WebElement> cartItems = driver
                                    .findElements(By.cssSelector("div.cart div.css-1gjj37g"));
                            for (WebElement e : cartItems) {
                                String itemText = e.getText().split("\n")[0];
                                if (itemText.equalsIgnoreCase(productName)) {
                                    return true;
                                }
                            }
                            return flag;
                        }
                    });
                    return status;
                }
            }
            System.out.println("Unable to find the given product");
            return false;
        } catch (Exception e) {
            System.out.println("Exception while performing add to cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting the status of clicking on the checkout button
     */
    public Boolean clickCheckout() {
        Boolean status = false;
        try {
            // CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            // Find and click on the Checkout button
            WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button.checkout-btn")));
            driver.findElement(By.cssSelector("button.checkout-btn")).click();
            status=true;
            return status;
        } catch (Exception e) {
            System.out.println("Exception while clicking on Checkout: " + e.getMessage());
            return status;
        }
    }

    /*
     * Return Boolean denoting the status of change quantity of product in cart
     * operation
     */
    public Boolean changeProductQuantityinCart(String productName, int quantity) {
        try {
            // CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 06: MILESTONE 5

            // Find the item on the cart with the matching productName

            // Increment or decrement the quantity of the matching product until the current
            // quantity is reached (Note: Keep a look out when then input quantity is 0,
            // here we need to remove the item completely from the cart)
            List<WebElement> cartItems=driver.findElements(By.cssSelector("div.cart div.css-1gjj37g"));
            for(WebElement cartItem:cartItems) {
                if(cartItem.getText().contains(productName)) {
                    int qty=Integer.parseInt(cartItem.findElement(By.cssSelector("div.css-69i1ev div.css-olyig7")).getText());
                    if(qty<quantity) {
                        //button svg[data-testid='AddOutlinedIcon']
                        //button svg[data-testid='RemoveOutlinedIcon']
                        //div[@class='MuiBox-root css-69i1ev']/div[@class='css-u4p24i']/button/*[@data-testid='AddOutlinedIcon']
                        while(qty<quantity) {
                            cartItem.findElement(By.cssSelector("button svg[data-testid='AddOutlinedIcon']")).click();
                            Thread.sleep(3000);
                            qty=Integer.parseInt(cartItem.findElement(By.cssSelector("div.css-69i1ev div.css-olyig7")).getText());
                        }
                    }
                    else if(qty>quantity) {
                        while(qty>quantity) {
                            if(qty==1) {
                                cartItem.findElement(By.cssSelector("button svg[data-testid='RemoveOutlinedIcon']")).click();
                                Thread.sleep(3000);
                                return true;
                            }
                            else {
                                cartItem.findElement(By.cssSelector("button svg[data-testid='RemoveOutlinedIcon']")).click();
                                Thread.sleep(3000);
                                qty=Integer.parseInt(cartItem.findElement(By.cssSelector("div.css-69i1ev div.css-olyig7")).getText());
                            }
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            if (quantity == 0)
                return true;
            System.out.println("exception occurred when updating cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the cart contains items as expected
     */
    public Boolean verifyCartContents(List<String> expectedCartContents) {
        try {
            WebElement cartParent = driver.findElement(By.className("cart"));
            List<WebElement> cartContents = cartParent.findElements(By.className("css-zgtx0t"));

            ArrayList<String> actualCartContents = new ArrayList<String>() {
            };
            for (WebElement cartItem : cartContents) {
                actualCartContents.add(cartItem.findElement(By.className("css-1gjj37g")).getText().split("\n")[0]);
            }

            for (String expected : expectedCartContents) {
                if (!actualCartContents.contains(expected)) {
                    return false;
                }
            }

            return true;

        } catch (Exception e) {
            System.out.println("Exception while verifying cart contents: " + e.getMessage());
            return false;
        }
    }
}
