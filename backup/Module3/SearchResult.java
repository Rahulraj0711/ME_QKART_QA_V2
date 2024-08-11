package QKART_SANITY_LOGIN.Module1;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResult {
    WebElement parentElement;
    WebDriverWait wait;

    public SearchResult(WebElement SearchResultElement) {
        this.parentElement = SearchResultElement;
    }

    /*
     * Return title of the parentElement denoting the card content section of a
     * search result
     */
    public String getTitleofResult() {
        // CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
        // Find the element containing the title (product name) of the search result and
        // assign the extract title text to titleOfSearchResult
        String titleOfSearchResult = "";
        titleOfSearchResult = parentElement.getText();
        return titleOfSearchResult;
    }

    /*
     * Return Boolean denoting if the open size chart operation was successful
     */
    public Boolean openSizechart() {
        try {
            // CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 04: MILESTONE 2
            // Find the link of size chart in the parentElement and click on it
            parentElement.findElement(By.tagName("button")).click();
            return true;
        } catch (Exception e) {
            System.out.println("Exception while opening Size chart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the close size chart operation was successful
     */
    public Boolean closeSizeChart(RemoteWebDriver driver) {
        try {
            Actions action = new Actions(driver);

            // Clicking on "ESC" key closes the size chart modal
            action.sendKeys(Keys.ESCAPE);
            action.perform();
            wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("table.MuiTable-root.css-1v2fgo1")));
            return true;
        } catch (Exception e) {
            System.out.println("Exception while closing the size chart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean based on if the size chart exists
     */
    public Boolean verifySizeChartExists() {
        Boolean status = false;
        try {
            // CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 04: MILESTONE 2
            /*
             * Check if the size chart element exists. If it exists, check if the text of
             * the element is "SIZE CHART". If the text "SIZE CHART" matches for the
             * element, set status = true , else set to false
             */
            WebElement sizeChart=parentElement.findElement(By.tagName("button"));
            if(sizeChart.isDisplayed()) {
                if(sizeChart.getText().equalsIgnoreCase("SIZE CHART")) {
                    status=true;
                }
            }
            return status;
        } catch (Exception e) {
            return status;
        }
    }

    /*
     * Return Boolean if the table headers and body of the size chart matches the
     * expected values
     */
    public Boolean validateSizeChartContents(List<String> expectedTableHeaders, List<List<String>> expectedTableBody,
            RemoteWebDriver driver) {
        Boolean status = false;
        try {
            // CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 04: MILESTONE 2
            /*
             * Locate the table element when the size chart modal is open
             * 
             * Validate that the contents of expectedTableHeaders is present as the table
             * header in the same order
             * 
             * Validate that the contents of expectedTableBody are present in the table body
             * in the same order
             */
            WebElement table = driver.findElement(By.cssSelector("table.MuiTable-root.css-1v2fgo1"));
            List<WebElement> actualHeaderElements = table.findElements(By.tagName("th"));
            List<String> actualTableHeaders = new ArrayList<>();
            for(int i=0;i<actualHeaderElements.size();i++) {
                actualTableHeaders.add(actualHeaderElements.get(i).getText());
            }
            status = actualTableHeaders.equals(expectedTableHeaders);
            // for (int i = 0; i < headers.size(); i++) {
            //     System.out.println("Expected: "+expectedTableHeaders.get(i)+"-> Actual: "+headers.get(i).getText());
            //     if (!headers.get(i).getText().equalsIgnoreCase(expectedTableHeaders.get(i))) {
            //         status = false;
            //     }
            // }
            List<WebElement> actualBodyRows = table.findElements(By.cssSelector("tbody tr"));
            List<List<WebElement>> actualBodyElements = new ArrayList<>();
            List<List<String>> actualTableBody = new ArrayList<>();
            for(int i=0;i<actualBodyRows.size();i++) {
                List<WebElement> row=actualBodyRows.get(i).findElements(By.tagName("td"));
                actualBodyElements.add(row);
            }
            for(int i=0;i<actualBodyElements.size();i++) {
                List<String> rowData=new ArrayList<>();
                for(int j=0;j<actualBodyElements.get(0).size();j++) {
                    rowData.add(actualBodyElements.get(i).get(j).getText());
                }
                actualTableBody.add(rowData);
            }
            status = actualTableBody.equals(expectedTableBody);
            // for (int i = 0; i < body.size(); i++) {
            //     List<WebElement> rowData = table.findElements(By.cssSelector("tbody tr:nth-child(" + (i + 1) + ") td"));
            //     for (int j = 0; j < rowData.size(); j++) {
            //         System.out.println("Expected: "+expectedTableBody.get(i).get(j)+"-> Actual: "+rowData.get(j).getText());
            //         if (!rowData.get(j).getText().equalsIgnoreCase(expectedTableBody.get(i).get(j))) {
            //             status= false;
            //         }
            //     }
            // }
            return status;

        } catch (Exception e) {
            System.out.println("Error while validating chart contents");
            return false;
        }
    }

    /*
     * Return Boolean based on if the Size drop down exists
     */
    public Boolean verifyExistenceofSizeDropdown(RemoteWebDriver driver) {
        Boolean status = false;
        try {
            // CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 04: MILESTONE 2
            // If the size dropdown exists and is displayed return true, else return false
            WebElement sizeDropdown=driver.findElement(By.cssSelector("select.css-1gtikml"));
            if(sizeDropdown.isDisplayed()) {
                status=true;
            }
            return status;
        } catch (Exception e) {
            return status;
        }
    }
}