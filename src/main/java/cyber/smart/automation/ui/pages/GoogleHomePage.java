package cyber.smart.automation.ui.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebElement;

public class GoogleHomePage extends PageObject {


    @FindBy(xpath = "//*[@id=\"tsf\"]/div[2]/div[1]/div[1]/div/div[2]/input")
    private WebElement searchBox;

    @FindBy(xpath = "//*[@id=\"tsf\"]/div[2]/div[1]/div[3]/center/input[1]")
    private WebElement searchButton;

    @FindBy(xpath = "//*[@id=\"rhs_block\"]/div/div[1]/div/div[1]/div[2]/div[2]/div/div[1]/div/div/div[2]/div[1]/span")
    private WebElement resultTitle;


    public void clickButton(){
        searchButton.click();
    }

    public void searchTerm(String term){
        searchBox.sendKeys(term);
        searchBox.submit();
    }

    public String readTitle() {
        return getTitle();

    }



}
