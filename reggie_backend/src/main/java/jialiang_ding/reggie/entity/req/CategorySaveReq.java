package jialiang_ding.reggie.entity.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class CategorySaveReq  implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "分类名称不能为空")
    private String name;
    private Integer sort;
    private  Integer type;

}
