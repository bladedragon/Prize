package team.redrock.prize.bean;

import lombok.Data;

@Data
public class TemplateData {
    private String value;
    private String color;

public TemplateData(String value, String color){
    this.value = value;
    this.color = color;
}

}
